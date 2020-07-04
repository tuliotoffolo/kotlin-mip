package mip.solvers

import jnr.ffi.*
import jnr.ffi.byref.PointerByReference
import mip.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty

class Gurobi(model: Model, name: String, sense: String) : Solver(model, name, sense) {

    private var env: Pointer
    private var gurobi: Pointer
    private val lib = GurobiLibrary.lib
    private val runtime: Runtime = Runtime.getRuntime(lib)

    override val solverName = "Gurobi"

    // region properties override

    // override val gap by Param<Double>()
    override val hasSolution get() = numSolutions > 0
    override val numCols get() = getIntAttr("NumVars")
    override val numRows get() = getIntAttr("NumRows")
    override val numSolutions get() = getIntAttr("SolCount")
    override val objectiveBound get() = getDblAttr("ObjBound")
    override val objectiveValue get() = getDblAttr("ObjVal")

    override val status
        get(): OptimizationStatus = when (getIntAttr("Status")) {
            // LOADED
            1 -> OptimizationStatus.Loaded
            // OPTIMAL
            2 -> OptimizationStatus.Optimal
            // INFEASIBLE
            3 -> OptimizationStatus.Infeasible
            // INF_OR_UNDB, INFEASIBLE
            4, 5 -> OptimizationStatus.Unbounded
            // CUTOFF
            6 -> OptimizationStatus.Cutoff
            // ITERATION_LIMIT, NODE_LIMIT, TIME_LIMIT, SOLUTION_LIMIT, INTERRUPTED, NUMERIC
            // ≈, INPROGRESS
            7, 8, 9, 10, 11, 12, 13, 14 ->
                if (numSolutions >= 1) OptimizationStatus.Feasible
                else OptimizationStatus.NoSolutionFound
            // USER_OBJ_LIMIT
            15 -> OptimizationStatus.Feasible
            else -> OptimizationStatus.Other
        }

    // override var clique: Int
    override var cutoff: Double
        get() = getDblParam("Cutoff")
        set(value) = setDblParam("Cutoff", value)

    // override var cutPasses: Int
    // override var cuts: Int
    // override var cutsGenerator: Int

    override var infeasTol: Double
        get() = getDblParam("FeasibilityTol")
        set(value) = setDblParam("FeasibilityTol", value)

    // override var integerTol: Int
    // override var lazyConstrsGenerator: Int
    // override var lpMethod: LPMethod

    override var maxMipGap: Double
        get() = getDblParam("MIPGap")
        set(value) = setDblParam("MIPGap", value)

    override var maxMipGapAbs: Double
        get() = getDblParam("MIPGapAbs")
        set(value) = setDblParam("MIPGapAbs", value)

    override var maxNodes: Int
        get() = getIntParam("NodeLimit")
        set(value) = setIntParam("NodeLimit", value)

    override var maxSeconds: Double
        get() = getDblParam("TimeLimit")
        set(value) = setDblParam("TimeLimit", value)

    override var maxSolutions: Int
        get() = getIntAttr("SolutionLimit")
        set(value) = setIntAttr("SolutionLimit", value)

    override var objective: LinExpr
        get() {
            val obj = LinExpr(objectiveConst)
            obj.sense = sense
            for (v in model.vars)
                obj += v.obj * v
            return obj
        }
        set(linExpr: LinExpr) {

        }

    override var objectiveConst: Double
        get() = getDblParam("ObjCon")
        set(value) = setDblParam("ObjCon", value)

    override var optTol: Double
        get() = getDblParam("OptimalityTol")
        set(value) = setDblParam("OptimalityTol", value)

    // override var plog: Boolean
    // override var preprocess: Int
    // override var roundIntVars: Boolean

    override var seed: Int
        get() = getIntParam("Seed")
        set(value) = setIntParam("Seed", value)

    override var sense: String
        get() = if (getIntAttr("ModelSense") >= 0) MINIMIZE else MAXIMIZE
        set(value) = when (value) {
            MINIMIZE -> setIntAttr("ModelSense", 1)
            MAXIMIZE -> setIntAttr("ModelSense", -1)
            else -> throw IllegalArgumentException("Model sense '$value' is invalid.")
        }

    // override var solPoolSize: Boolean
    // override var start: Int
    // override var storeSearchProgressLog: Double

    override var threads: Int
        get() = getIntParam("Threads")
        set(value) = setIntParam("Threads", value)

    // endregion properties override

    // region buffers

    private var bufferLength = 8192
    private var dblBuffer = Memory.allocateDirect(runtime, bufferLength * 8)
    private var intBuffer = Memory.allocateDirect(runtime, bufferLength * 4)
    private var strBuffer = Memory.allocateDirect(runtime, bufferLength * 1)

    private var pi: Pointer? = null
        get() {
            if (field == null && hasSolution) {
                field = Memory.allocateDirect(runtime, 8 * numRows)
                val res = lib.GRBgetdblattrarray(gurobi, "Pi", 0, numRows, field)
            }
            return field
        }

    private var rc: Pointer? = null
        get() {
            if (field == null && hasSolution) {
                field = Memory.allocateDirect(runtime, 8 * numCols)
                val res = lib.GRBgetdblattrarray(gurobi, "RC", 0, numCols, field)
            }
            return field
        }

    private var solution: Pointer? = null
        get() {
            if (field == null && hasSolution) {
                field = Memory.allocateDirect(runtime, 8 * numCols)
                val res = lib.GRBgetdblattrarray(gurobi, "X", 0, numCols, field)
            }
            return field
        }

    private var solutions = HashMap<Int, Pointer>()

    // endregion buffers

    init {
        // initializing the environment
        val envRef = PointerByReference()
        lib.GRBloadenv(envRef, "")
        env = envRef.value

        // initializing the solver/model
        val grbRef = PointerByReference()
        lib.GRBnewmodel(env, grbRef, name, 0, null, null, null, null, null)
        gurobi = grbRef.value

        // setting sense (if needed)
        if (sense == MAXIMIZE)
            lib.GRBsetintattr(gurobi, "ModelSense", -1)

        lib.fflush(null)
    }


    override fun addConstr(linExpr: LinExpr, name: String) {
        val nz = linExpr.size
        val rhs = -linExpr.const
        val sense = linExpr.sense[0].toByte()

        checkBuffer(nz)
        var i = 0L
        for ((v, coeff) in linExpr.terms) {
            intBuffer.putInt(4L * i, v.idx)
            dblBuffer.putDouble(8L * i, coeff)
            i++
        }

        lib.GRBaddconstr(gurobi, nz, intBuffer, dblBuffer, sense, rhs, name)
    }

    override fun addVar(name: String, obj: Double, lb: Double, ub: Double, varType: VarType,
                        column: Column) {
        val nz = column.size
        val lowerBound = if (lb == -INF) -GurobiLibrary.GRB_INFINITY else lb
        val upperBound = if (ub == INF) GurobiLibrary.GRB_INFINITY else ub

        val vtype = when (varType) {
            VarType.Binary -> 'B'.toByte()
            VarType.Continuous -> 'C'.toByte()
            VarType.Integer -> 'I'.toByte()
        }

        if (nz > 0) {
            checkBuffer(nz)
            var i = 0L
            for ((constr, coeff) in column.terms) {
                intBuffer.putInt(4 * i, constr.idx)
                dblBuffer.putDouble(8 * i, coeff)
                i++
            }
            lib.GRBaddvar(gurobi, nz, intBuffer, dblBuffer, obj, lowerBound, upperBound, vtype, name)
        }
        else {
            lib.GRBaddvar(gurobi, nz, null, null, obj, lowerBound, upperBound, vtype, name)
        }
    }

    override fun get(property: KProperty<*>): Any = when (property.name) {
        "cutoff" -> getDblParam("Cutoff")
        "maxMipGap" -> getDblParam("MIPGap")
        "maxMipGapAbs" -> getDblParam("MIPGapAbs")
        // "numSolutions" -> lib.Cbc_numberSavedSolutions(gurobi)
        "nodeLimit" -> getDblParam("NodeLimit")
        "objective" -> objective
        "objectiveBound" -> getDblAttr("ObjBound")
        "objectiveValue" -> getDblAttr("ObjVal")
        "sense" -> if (getIntAttr("ModelSense") >= 0) MINIMIZE else MAXIMIZE
        "status" -> status
        "threads" -> getIntParam("Threads")
        "timeLimit" -> getDblParam("TimeLimit")

        else -> throw NotImplementedError("Parameter '${property.name}' currently unavailable in Gurobi interface")
    }

    override fun optimize(): OptimizationStatus {
        // resetting buffers
        removeSolution()

        // optimizing... and flushing stdout
        lib.GRBoptimize(gurobi)
        lib.fflush(null)

        return status
    }

    override fun removeConstrs(constrs: Iterable<Constr>) {
        val size = if (constrs is Collection<*>) constrs.size else constrs.count()
        if (size > 0) {
            checkBuffer(size)
            for ((i, constr) in constrs.withIndex())
                intBuffer.putInt(4 * i.toLong(), constr.idx)
            lib.GRBdelconstrs(gurobi, size, intBuffer)
        }
    }

    override fun removeVars(vars: Iterable<Var>) {
        val size = if (vars is Collection<*>) vars.size else vars.count()
        if (size > 0) {
            checkBuffer(size)
            for ((i, variable) in vars.withIndex())
                intBuffer.putInt(4 * i.toLong(), variable.idx)
            lib.GRBdelvars(gurobi, size, intBuffer)
        }
    }

    override fun <T> set(property: KMutableProperty<*>, value: T) {
        when (property.name) {
            "cutoff" -> lib.GRBsetdblparam(gurobi, "Cutoff", value as Double)
            "iterationLimit" -> lib.GRBsetdblparam(gurobi, "IterationLimit", value as Double)
            "maxMipGap" -> lib.GRBsetdblparam(gurobi, "MIPGap", value as Double)
            "maxMipGapAbs" -> lib.GRBsetdblparam(gurobi, "MIPGapAbs", value as Double)
            "seed" -> lib.GRBsetintparam(gurobi, "Seed", value as Int)
            "sense" -> lib.GRBsetintparam(gurobi, "ModelSense", if (sense == MIN) 1 else -1)
            "threads" -> lib.GRBsetintparam(gurobi, "Threads", value as Int)
            "nodeLimit" -> lib.GRBsetdblparam(gurobi, "NodeLimit", value as Double)
            "timeLimit" -> lib.GRBsetdblparam(gurobi, "TimeLimit", value as Double)

            else -> throw NotImplementedError("Parameter currently unavailable in Gurobi interface")
        }
    }

    override fun write(path: String) {
        lib.GRBwrite(gurobi, path)
    }


    // region constraints getters and setters

    override fun getConstrExpr(idx: Int): LinExpr = throw NotImplementedError()
    override fun setConstrExpr(idx: Int, value: LinExpr): Unit = throw NotImplementedError()

    override fun getConstrName(idx: Int): String {
        val pointer = PointerByReference()
        lib.GRBgetstrattrelement(gurobi, "VarName", idx, pointer)
        return pointer.value.getString(0)
    }

    override fun getConstrPi(idx: Int): Double =
        pi?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available")

    override fun getConstrRHS(idx: Int): Double = throw NotImplementedError()
    override fun setConstrRHS(idx: Int, value: Double): Unit = throw NotImplementedError()

    override fun getConstrSlack(idx: Int): Double = throw NotImplementedError()

    // endregion constraints getters and setters

    // region variable getters and setters

    override fun getVarColumn(idx: Int): Column {
        // val nz = lib.Cbc_getColNz(gurobi, idx)
        // if (nz == 0) return Column()
        //
        // val cidx = lib.Cbc_getColIndices(gurobi, idx)
        // val ccoeff = lib.Cbc_getColCoeffs(gurobi, idx)
        //
        // if (cidx.getPointer(0) == null || ccoeff.getPointer(0) == null)
        //     throw Error("Error getting column indices and/or column coefficients")
        //
        // val constrs = List<Constr>(nz) { model.constrs[cidx.getInt(4 * it.toLong())] }
        // val coeffs = List<Double>(nz) { ccoeff.getDouble(8 * it.toLong()) }

        // TODO Implement this function
        return Column.EMPTY
    }

    override fun setVarColumn(idx: Int, value: Column) =
        throw UnsupportedOperationException("Gurobi interface does not permit setting column")

    override fun getVarLB(idx: Int): Double {
        lib.GRBgetdblattrelement(gurobi, "LB", idx, dblBuffer)
        return dblBuffer.getDouble(0)
    }

    override fun setVarLB(idx: Int, value: Double) {
        lib.GRBsetdblattrelement(gurobi, "LB", idx, value)
    }

    override fun getVarName(idx: Int): String {
        val pointer = PointerByReference()
        lib.GRBgetstrattrelement(gurobi, "VarName", idx, pointer)
        return pointer.value.getString(0)
    }

    override fun getVarObj(idx: Int): Double {
        lib.GRBgetdblattrelement(gurobi, "Obj", idx, dblBuffer)
        return dblBuffer.getDouble(0)
    }

    override fun setVarObj(idx: Int, value: Double) {
        lib.GRBsetdblattrelement(gurobi, "Obj", idx, value)
    }

    override fun getVarRC(idx: Int): Double =
        rc?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available.")

    override fun getVarType(idx: Int): VarType {
        lib.GRBgetcharattrelement(gurobi, "VType", idx, strBuffer)
        val vtype = strBuffer.getByte(0).toChar()

        return when (vtype) {
            'B' -> VarType.Binary
            'I' -> VarType.Integer
            else -> VarType.Continuous
        }
    }

    override fun setVarType(idx: Int, value: VarType) {
        val vtype = when (value) {
            VarType.Binary -> 'B'.toByte()
            VarType.Continuous -> 'C'.toByte()
            VarType.Integer -> 'I'.toByte()
        }
        lib.GRBsetcharattrelement(gurobi, "VType", idx, vtype)
    }

    override fun getVarUB(idx: Int): Double {
        lib.GRBgetdblattrelement(gurobi, "UB", idx, dblBuffer)
        return dblBuffer.getDouble(0)
    }

    override fun setVarUB(idx: Int, value: Double) {
        lib.GRBsetdblattrelement(gurobi, "UB", idx, value)
    }

    override fun getVarX(idx: Int): Double =
        solution?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available")

    // override fun getVarXi(idx: Int, i: Int): Double {
    //     if (idx == 0) return getVarX(idx)
    //
    //     return getSolutionIdx(i)?.getDouble(8 * idx.toLong())
    //         ?: throw Error("Solution $i not available")
    // }

    // endregion variable getters and setters

    // region private useful functions

    private fun checkBuffer(nz: Int) {
        if (nz > bufferLength) {
            bufferLength = nz
            dblBuffer = Memory.allocateDirect(runtime, bufferLength * 8)
            intBuffer = Memory.allocateDirect(runtime, bufferLength * 4)
        }
    }

    private fun getDblAttr(attr: String): Double {
        lib.GRBgetdblattr(gurobi, attr, dblBuffer)
        return dblBuffer.getDouble(0)
    }

    private fun setDblAttr(attr: String, value: Double): Unit {
        lib.GRBsetdblattr(gurobi, attr, value)
    }

    private fun getDblParam(param: String): Double {
        lib.GRBgetdblparam(gurobi, param, dblBuffer)
        return dblBuffer.getDouble(0)
    }

    private fun setDblParam(param: String, value: Double): Unit {
        lib.GRBsetdblparam(gurobi, param, value)
    }

    private fun getIntAttr(attr: String): Int {
        lib.GRBgetintattr(gurobi, attr, intBuffer)
        return intBuffer.getInt(0)
    }

    private fun setIntAttr(param: String, value: Int): Unit {
        lib.GRBsetintattr(gurobi, param, value)
    }

    private fun getIntParam(param: String): Int {
        lib.GRBgetintparam(gurobi, param, intBuffer)
        return intBuffer.getInt(0)
    }

    private fun setIntParam(param: String, value: Int): Unit {
        lib.GRBsetintparam(gurobi, param, value)
    }

    private fun removeSolution() {
        pi = null
        rc = null
        solution = null
        solutions.clear()
    }

    // endregion private useful functions
}
