package mip.solvers

import jnr.ffi.*
import jnr.ffi.byref.*
import mip.*
import kotlin.math.roundToInt

class Gurobi(model: Model, name: String, sense: String) : Solver(model, name, sense) {

    override val solverName = "Gurobi"

    private var env: Pointer
    private var gurobi: Pointer
    private val lib = GurobiJnrLib.loadLibrary()
    private val runtime: Runtime = Runtime.getRuntime(lib)

    // region properties override

    override val gap
        get() = if (hasSolution) (objectiveValue - objectiveBound) / objectiveBound else 1.0

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

    override var integerTol: Double
        get() = getDblParam("IntFeasTol")
        set(value) = setDblParam("IntFeasTol", value)

    // override var lazyConstrsGenerator: Int
    // override var lpMethod: LPMethod

    override var maxMipGap: Double
        get() = getDblParam("MIPGap")
        set(value) = setDblParam("MIPGap", value)

    override var maxMipGapAbs: Double
        get() = getDblParam("MIPGapAbs")
        set(value) = setDblParam("MIPGapAbs", value)

    override var maxNodes: Int
        get() = getDblParam("NodeLimit").roundToInt()
        set(value) = setDblParam("NodeLimit", value.toDouble())

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
        set(expr: LinExpr) {
            assert(expr.isAffine)

            val nz = model.vars.size
            val vars = IntArray(nz) { it }
            val objs = DoubleArray(nz) { 0.0 }
            for ((v, coeff) in expr.terms)
                objs[v.idx] = coeff

            // setting constant and objective sense
            objectiveConst = expr.const
            if (sense == MINIMIZE || sense == MAXIMIZE) sense = expr.sense

            // setting variable coefficients
            lib.GRBsetdblattrlist(gurobi, "Obj", nz, vars, objs)
            // error = GRBsetdblattrlist(self._model, attr, nz, cind, cval)
        }

    override var objectiveConst: Double
        get() = getDblAttr("ObjCon")
        set(value) = setDblAttr("ObjCon", value)

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

    private var pi: Pointer? = null
        get() {
            if (field == null && hasSolution) {
                field = Memory.allocateDirect(runtime, 8 * numRows)
                val res = lib.GRBgetdblattrarray(gurobi, "Pi", 0, numRows, field)
                assert(res == 0)
            }
            return field
        }

    private var rc: Pointer? = null
        get() {
            if (field == null && hasSolution) {
                field = Memory.allocateDirect(runtime, 8 * numCols)
                val res = lib.GRBgetdblattrarray(gurobi, "RC", 0, numCols, field)
                assert(res == 0)
            }
            return field
        }

    private var solution: Pointer? = null
        get() {
            if (field == null && hasSolution) {
                field = Memory.allocateDirect(runtime, 8 * numCols)
                val res = lib.GRBgetdblattrarray(gurobi, "X", 0, numCols, field)
                assert(res == 0)
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

    protected fun finalize() {
        // freeing Gurobi model and environment
        lib.GRBfreemodel(gurobi)
        lib.GRBfreeenv(env)
    }


    override fun addConstr(linExpr: LinExpr, name: String) {
        val nz = linExpr.size
        val rhs = -linExpr.const
        val sense = linExpr.sense[0].toByte()

        val intBuffer = IntArray(nz) { 0 }
        val dblBuffer = DoubleArray(nz) { 0.0 }
        var i = 0
        for ((v, coeff) in linExpr.terms) {
            intBuffer[i] = v.idx
            dblBuffer[i] = coeff
            i++
        }

        lib.GRBaddconstr(gurobi, nz, intBuffer, dblBuffer, sense, rhs, name)
    }

    override fun addVar(name: String, obj: Double, lb: Double, ub: Double, varType: VarType,
                        column: Column) {
        val nz = column.size
        val lowerBound = if (lb == -INF) -GurobiJnrLib.GRB_INFINITY else lb
        val upperBound = if (ub == INF) GurobiJnrLib.GRB_INFINITY else ub

        val vtype = when (varType) {
            VarType.Binary -> 'B'.toByte()
            VarType.Continuous -> 'C'.toByte()
            VarType.Integer -> 'I'.toByte()
        }

        if (nz > 0) {
            val intBuffer = IntArray(nz)
            val dblBuffer = DoubleArray(nz)
            var i = 0
            for ((constr, coeff) in column.terms) {
                intBuffer[i] = constr.idx
                dblBuffer[i] = coeff
                i++
            }
            lib.GRBaddvar(gurobi, nz, intBuffer, dblBuffer, obj, lowerBound, upperBound, vtype, name)
        }
        else {
            lib.GRBaddvar(gurobi, nz, null, null, obj, lowerBound, upperBound, vtype, name)
        }
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
            val intBuffer = IntArray(size)
            for ((i, constr) in constrs.withIndex())
                intBuffer[i] = constr.idx
            lib.GRBdelconstrs(gurobi, size, intBuffer)
        }
    }

    override fun removeVars(vars: Iterable<Var>) {
        val size = if (vars is Collection<*>) vars.size else vars.count()
        if (size > 0) {
            val intBuffer = IntArray(size)
            for ((i, variable) in vars.withIndex())
                intBuffer[i] = variable.idx
            lib.GRBdelvars(gurobi, size, intBuffer)
        }
    }

    override fun write(path: String) {
        lib.GRBwrite(gurobi, path)
    }


    // region constraints getters and setters

    override fun getConstrExpr(idx: Int): LinExpr = throw NotImplementedError()
    override fun setConstrExpr(idx: Int, value: LinExpr) = throw NotImplementedError()

    override fun getConstrName(idx: Int): String {
        val pointer = PointerByReference()
        lib.GRBgetstrattrelement(gurobi, "VarName", idx, pointer)
        return pointer.value.getString(0)
    }

    override fun getConstrPi(idx: Int): Double =
        pi?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available")

    override fun getConstrRHS(idx: Int): Double = throw NotImplementedError()
    override fun setConstrRHS(idx: Int, value: Double) = throw NotImplementedError()

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
        val dblRef = DoubleByReference()
        lib.GRBgetdblattrelement(gurobi, "LB", idx, dblRef)
        return dblRef.value
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
        val dblRef = DoubleByReference()
        lib.GRBgetdblattrelement(gurobi, "Obj", idx, dblRef)
        return dblRef.value
    }

    override fun setVarObj(idx: Int, value: Double) {
        lib.GRBsetdblattrelement(gurobi, "Obj", idx, value)
    }

    override fun getVarRC(idx: Int): Double =
        rc?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available.")

    override fun getVarType(idx: Int): VarType {
        val strBuffer = Memory.allocateDirect(runtime, 256)
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
        val dblRef = DoubleByReference()
        lib.GRBgetdblattrelement(gurobi, "UB", idx, dblRef)
        return dblRef.value
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

    private fun getDblAttr(attr: String): Double {
        val dblRef = DoubleByReference()
        lib.GRBgetdblattr(gurobi, attr, dblRef)
        return dblRef.value
    }

    private fun setDblAttr(attr: String, value: Double) {
        lib.GRBsetdblattr(gurobi, attr, value)
    }

    private fun getDblParam(param: String): Double {
        val dblRef = DoubleByReference()
        lib.GRBgetdblparam(env, param, dblRef)
        return dblRef.value
    }

    private fun setDblParam(param: String, value: Double) {
        lib.GRBsetdblparam(env, param, value)
    }

    private fun getIntAttr(attr: String): Int {
        val intRef = IntByReference()
        lib.GRBgetintattr(gurobi, attr, intRef)
        return intRef.value
    }

    private fun setIntAttr(param: String, value: Int) {
        lib.GRBsetintattr(gurobi, param, value)
    }

    private fun getIntParam(param: String): Int {
        val intRef = IntByReference()
        lib.GRBgetintparam(env, param, intRef)
        return intRef.value
    }

    private fun setIntParam(param: String, value: Int) {
        lib.GRBsetintparam(env, param, value)
    }

    private fun removeSolution() {
        pi = null
        rc = null
        solution = null
        solutions.clear()
    }

    // endregion private useful functions
}
