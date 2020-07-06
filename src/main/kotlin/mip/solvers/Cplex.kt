package mip.solvers

import jnr.ffi.*
import jnr.ffi.byref.*
import mip.*
import java.nio.charset.Charset

class Cplex(model: Model, name: String, sense: String) : Solver(model, name, sense) {

    override val solverName = "Cplex"

    private var env: Pointer
    private var lp: Pointer
    private val lib = CplexLibrary.lib
    private val runtime: Runtime = Runtime.getRuntime(lib)

    // region properties override

    // override val gap by Param<Double>()
    override val hasSolution get() = numSolutions > 0
    override val numCols get() = lib.CPXgetnumcols(env, lp)
    override val numRows get() = lib.CPXgetnumrows(env, lp)
    override val numSolutions get() = lib.CPXgetsolnpoolnumsolns(env, lp)

    override val objectiveBound
        get(): Double {
            lib.CPXgetbestobjval(env, lp, dblBuffer)
            return dblBuffer.getDouble(0)
        }

    override val objectiveValue
        get(): Double {
            lib.CPXgetobjval(env, lp, dblBuffer)
            return dblBuffer.getDouble(0)
        }

    override val status
        // http://www-eio.upc.es/lceio/manuals/cplex75/doc/refmanccpp/html/appendixB.html#151095
        get(): OptimizationStatus = when (lib.CPXgetstat(env, lp)) {
            1, 101, 102 -> OptimizationStatus.Loaded
            2, 19, 32, 34, 103 -> OptimizationStatus.Infeasible
            3, 33, 37 -> OptimizationStatus.Unbounded
            4, 35 -> OptimizationStatus.Cutoff

            in 5..11, in 37..42, in 104..114 ->
                if (numSolutions >= 1) OptimizationStatus.Feasible
                else OptimizationStatus.NoSolutionFound

            else -> OptimizationStatus.Other
        }

    // override var clique: Int
    override var cutoff: Double
        get() = getDblParam(if (sense == MAXIMIZE) CplexLibrary.CPX_PARAM_CUTUP else CplexLibrary.CPX_PARAM_CUTLO)
        set(value) = setDblParam(if (sense == MAXIMIZE) CplexLibrary.CPX_PARAM_CUTUP else CplexLibrary.CPX_PARAM_CUTLO, value)

    // override var cutPasses: Int
    // override var cuts: Int
    // override var cutsGenerator: Int

    override var infeasTol: Double
        get() = getDblParam(CplexLibrary.CPXPARAM_Feasopt_Tolerance)
        set(value) = setDblParam(CplexLibrary.CPXPARAM_Feasopt_Tolerance, value)

    // override var integerTol: Int
    // override var lazyConstrsGenerator: Int
    // override var lpMethod: LPMethod

    override var maxMipGap: Double
        get() = getDblParam(CplexLibrary.CPXPARAM_MIP_Tolerances_MIPGap)
        set(value) = setDblParam(CplexLibrary.CPXPARAM_MIP_Tolerances_MIPGap, value)

    override var maxMipGapAbs: Double
        get() = getDblParam(CplexLibrary.CPXPARAM_MIP_Tolerances_AbsMIPGap)
        set(value) = setDblParam(CplexLibrary.CPXPARAM_MIP_Tolerances_AbsMIPGap, value)

    override var maxNodes: Int
        get() = getIntParam(CplexLibrary.CPXPARAM_MIP_Limits_Nodes)
        set(value) = setIntParam(CplexLibrary.CPXPARAM_MIP_Limits_Nodes, value)

    override var maxSeconds: Double
        get() = getDblParam(CplexLibrary.CPXPARAM_TimeLimit)
        set(value) = setDblParam(CplexLibrary.CPXPARAM_TimeLimit, value)

    override var maxSolutions: Int
        get() = getIntParam(CplexLibrary.CPXPARAM_MIP_Limits_Solutions)
        set(value) = setIntParam(CplexLibrary.CPXPARAM_MIP_Limits_Solutions, value)

    override var objective: LinExpr
        get() {
            val obj = LinExpr(objectiveConst)
            obj.sense = sense
            for (v in model.vars)
                obj += v.obj * v
            return obj
        }
        set(linExpr: LinExpr) {
            TODO("Not yet implemented")
        }

    override var objectiveConst: Double
        get() {
            lib.CPXgetobjoffset(env, lp, dblBuffer)
            return dblBuffer.getDouble(0)
        }
        set(value) {
            lib.CPXchgobjoffset(env, lp, value)
        }

    override var optTol: Double
        get() = getDblParam(CplexLibrary.CPXPARAM_MIP_Tolerances_RelObjDifference)
        set(value) = setDblParam(CplexLibrary.CPXPARAM_MIP_Tolerances_RelObjDifference, value)

    // override var plog: Boolean
    // override var preprocess: Int
    // override var roundIntVars: Boolean

    override var seed: Int
        get() = getIntParam(CplexLibrary.CPXPARAM_RandomSeed)
        set(value) = setIntParam(CplexLibrary.CPXPARAM_RandomSeed, value)

    override var sense: String
        get() = if (lib.CPXgetobjsen(env, lp) == CplexLibrary.CPX_MIN) MINIMIZE else MAXIMIZE
        set(value) {
            when (value) {
                MINIMIZE -> lib.CPXchgobjsen(env, lp, CplexLibrary.CPX_MIN)
                MAXIMIZE -> lib.CPXchgobjsen(env, lp, CplexLibrary.CPX_MAX)
                else -> throw IllegalArgumentException("Model sense '$value' is invalid.")
            }
        }

    // override var solPoolSize: Boolean
    // override var start: Int
    // override var storeSearchProgressLog: Double

    override var threads: Int
        get() = getIntParam(CplexLibrary.CPXPARAM_Threads)
        set(value) = setIntParam(CplexLibrary.CPXPARAM_Threads, value)

    // endregion properties override

    // region buffers

    private var statusInt = 0
    private val statusPtr = Memory.allocateDirect(runtime, 8)

    private var bufferLength = 8192
    private var dblBuffer = Memory.allocateDirect(runtime, bufferLength * 8)
    private var intBuffer = Memory.allocateDirect(runtime, bufferLength * 4)
    private var strBuffer = Memory.allocateDirect(runtime, bufferLength * 1)

    private val chrByRef1 = Memory.allocateDirect(runtime, 1)
    private val chrByRef2 = Memory.allocateDirect(runtime, 1)

    private val intByRef1 = Memory.allocateDirect(runtime, 4)
    private val intByRef2 = Memory.allocateDirect(runtime, 4)
    private val intByRef3 = Memory.allocateDirect(runtime, 4)
    private val intByRef4 = Memory.allocateDirect(runtime, 4)

    private val dblByRef1 = Memory.allocateDirect(runtime, 8)
    private val dblByRef2 = Memory.allocateDirect(runtime, 8)
    private val dblByRef3 = Memory.allocateDirect(runtime, 8)
    private val dblByRef4 = Memory.allocateDirect(runtime, 8)

    private var pi: Pointer? = null
        get() {
            if (field == null && hasSolution) {
                field = Memory.allocateDirect(runtime, 8 * numRows)
                val res = lib.CPXgetpi(env, lp, field, 0, numRows)
                assert(res == 0)
            }
            return field
        }

    private var rc: Pointer? = null
        get() {
            if (field == null && hasSolution) {
                field = Memory.allocateDirect(runtime, 8 * numCols)
                val res = lib.CPXgetdj(env, lp, field, 0, numCols)
                assert(res == 0)
            }
            return field
        }

    private var solution: Pointer? = null
        get() {
            if (field == null && hasSolution) {
                field = Memory.allocateDirect(runtime, 8 * numCols)
                val res = lib.CPXgetx(env, lp, field, 0, numCols)
                assert(res == 0)
            }
            return field
        }

    private var solutions = HashMap<Int, Pointer>()

    // endregion buffers

    init {
        // initializing environment and problem
        env = lib.CPXopenCPLEX(statusPtr)
        lp = lib.CPXcreateprob(env, statusPtr, name)

        // setting sense (if needed)
        if (sense == MAXIMIZE)
            lib.CPXchgobjsen(env, lp, CplexLibrary.CPX_MAX)

        lib.fflush(null)
    }

    override fun addConstr(linExpr: LinExpr, name: String) {
        val nz = linExpr.size
        dblByRef1.putDouble(0, -linExpr.const)

        val sense = when (linExpr.sense) {
            LEQ -> 'L'.toByte()
            EQ -> 'E'.toByte()
            GEQ -> 'G'.toByte()
            else -> throw IllegalArgumentException("Invalid sense")
        }
        chrByRef1.putByte(0, sense)

        // matbeg array (one item with value 0)
        intByRef2.putInt(0, 0)

        checkBuffer(nz)
        var i = 0L
        for ((v, coeff) in linExpr.terms) {
            intBuffer.putInt(4L * i, v.idx)
            dblBuffer.putDouble(8L * i, coeff)
            i++
        }

        strBuffer.putString(0, name, name.length, Charset.defaultCharset())

        lib.CPXaddrows(env, lp, 0, 1, nz, dblByRef1, chrByRef1, intByRef1, intBuffer, dblBuffer,
            null, PointerByReference(strBuffer))
    }

    override fun addVar(name: String, obj: Double, lb: Double, ub: Double, varType: VarType,
                        column: Column) {
        // val nz = column.size
        dblByRef1.putDouble(0, obj)
        dblByRef2.putDouble(0, if (lb == -INF) -GurobiLibrary.GRB_INFINITY else lb)
        dblByRef3.putDouble(0, if (ub == INF) GurobiLibrary.GRB_INFINITY else ub)

        val vtype = when (varType) {
            VarType.Binary -> 'B'.toByte()
            VarType.Continuous -> 'C'.toByte()
            VarType.Integer -> 'I'.toByte()
        }
        chrByRef1.putByte(0, vtype)

        // if (nz > 0) {
        //     checkBuffer(nz)
        //     var i = 0L
        //     for ((constr, coeff) in column.terms) {
        //         intBuffer.putInt(4 * i, constr.idx)
        //         dblBuffer.putDouble(8 * i, coeff)
        //         i++
        //     }
        //     // TODO lib.GRBaddvar(lp, nz, intBuffer, dblBuffer, obj, lowerBound, upperBound, vtype, name)
        // }

        lib.CPXnewcols(env, lp, 1, dblByRef1, dblByRef2, dblByRef3, chrByRef1, PointerByReference(strBuffer))
    }

    override fun optimize(): OptimizationStatus {
        // resetting buffers
        removeSolution()

        // optimizing... and flushing stdout
        statusInt = lib.CPXlpopt(env, lp)
        assert(statusInt != 0)
        lib.fflush(null)

        return status
    }

    override fun removeConstrs(constrs: Iterable<Constr>) {
        TODO("Not yet implemented")
    }

    override fun removeVars(vars: Iterable<Var>) {
        TODO("Not yet implemented")
    }

    override fun write(path: String) {
        lib.CPXwriteprob(env, lp, path, "LP")
    }

    // region constraints getters and setters

    override fun getConstrExpr(idx: Int): LinExpr = throw NotImplementedError()
    override fun setConstrExpr(idx: Int, value: LinExpr): Unit = throw NotImplementedError()

    override fun getConstrName(idx: Int): String {
        val pointer = PointerByReference()
        // TODO lib.GRBgetstrattrelement(lp, "VarName", idx, pointer)
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
        // TODO lib.GRBgetdblattrelement(lp, "LB", idx, dblBuffer)
        return dblBuffer.getDouble(0)
    }

    override fun setVarLB(idx: Int, value: Double) {
        // TODO lib.GRBsetdblattrelement(lp, "LB", idx, value)
    }

    override fun getVarName(idx: Int): String {
        val pointer = PointerByReference()
        // TODO lib.GRBgetstrattrelement(lp, "VarName", idx, pointer)
        return pointer.value.getString(0)
    }

    override fun getVarObj(idx: Int): Double {
        // TODO lib.GRBgetdblattrelement(lp, "Obj", idx, dblBuffer)
        return dblBuffer.getDouble(0)
    }

    override fun setVarObj(idx: Int, value: Double) {
        // TODO lib.GRBsetdblattrelement(lp, "Obj", idx, value)
    }

    override fun getVarRC(idx: Int): Double =
        rc?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available.")

    override fun getVarType(idx: Int): VarType {
        // TODO lib.GRBgetcharattrelement(lp, "VType", idx, strBuffer)
        // val vtype = strBuffer.getByte(0).toChar()

        return when (strBuffer.getByte(0).toChar()) {
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
        // TODO lib.GRBsetcharattrelement(lp, "VType", idx, vtype)
    }

    override fun getVarUB(idx: Int): Double {
        // TODO lib.GRBgetdblattrelement(lp, "UB", idx, dblBuffer)
        return dblBuffer.getDouble(0)
    }

    override fun setVarUB(idx: Int, value: Double) {
        // TODO lib.GRBsetdblattrelement(lp, "UB", idx, value)
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

    private fun getDblParam(param: Int): Double {
        lib.CPXgetdblparam(env, param, dblBuffer)
        return dblBuffer.getDouble(0)
    }

    private fun setDblParam(param: Int, value: Double): Unit {
        lib.CPXsetdblparam(env, param, value)
    }

    private fun getIntParam(param: Int): Int {
        lib.CPXgetintparam(env, param, intBuffer)
        return intBuffer.getInt(0)
    }

    private fun setIntParam(param: Int, value: Int): Unit {
        lib.CPXsetintparam(env, param, value)
    }

    private fun removeSolution() {
        pi = null
        rc = null
        solution = null
        solutions.clear()
    }

    // endregion private useful functions
}
