package mip.solvers

import jnr.ffi.*
import jnr.ffi.byref.*
import mip.*

class Cplex(model: Model, name: String, sense: String) : Solver(model, name, sense) {

    override val solverName = "Cplex"

    override val INF = Double.MAX_VALUE
    override val INT_MAX = Int.MAX_VALUE

    private var env: Pointer? = null
    private var lp: Pointer? = null
    private val lib = CplexJnrJavaLib.loadLibrary()
    private val runtime: Runtime = Runtime.getRuntime(lib)

    private var hasIntVars = false

    // region properties override

    // override val gap by Param<Double>()
    override val hasSolution get() = true//numSolutions > 0
    override val numCols get() = lib.CPXgetnumcols(env, lp)
    override val numRows get() = lib.CPXgetnumrows(env, lp)
    override val numSolutions get() = lib.CPXgetsolnpoolnumsolns(env, lp)

    override val objectiveBound
        get(): Double {
            val dblRef = DoubleByReference()
            lib.CPXgetbestobjval(env, lp, dblRef)
            return dblRef.value
        }

    override val objectiveValue
        get(): Double {
            val dblRef = DoubleByReference()
            lib.CPXgetobjval(env, lp, dblRef)
            return dblRef.value
        }

    override val status
        // http://www-eio.upc.es/lceio/manuals/cplex75/doc/refmanccpp/html/appendixB.html#151095
        get(): OptimizationStatus = when (lib.CPXgetstat(env, lp)) {
            1, 101, 102 -> OptimizationStatus.Optimal
            2, 19, 32, 34, 103 -> OptimizationStatus.Infeasible
            3, 33, 37 -> OptimizationStatus.Unbounded
            4, 35 -> OptimizationStatus.Cutoff

            in 5 .. 11, in 37 .. 42, in 104 .. 114 ->
                if (numSolutions >= 1) OptimizationStatus.Feasible
                else OptimizationStatus.NoSolutionFound

            else -> OptimizationStatus.Other
        }

    // override var clique: Int
    override var cutoff: Double
        get() = getDblParam(if (sense == MAXIMIZE) CplexJnrLib.CPX_PARAM_CUTUP else CplexJnrLib.CPX_PARAM_CUTLO)
        set(value) = setDblParam(if (sense == MAXIMIZE) CplexJnrLib.CPX_PARAM_CUTUP else CplexJnrLib.CPX_PARAM_CUTLO, value)

    // override var cutPasses: Int
    // override var cuts: Int
    // override var cutsGenerator: Int

    override var infeasTol: Double
        get() = getDblParam(CplexJnrLib.CPXPARAM_Feasopt_Tolerance)
        set(value) = setDblParam(CplexJnrLib.CPXPARAM_Feasopt_Tolerance, value)

    // override var integerTol: Int
    // override var lazyConstrsGenerator: Int
    // override var lpMethod: LPMethod

    override var maxMipGap: Double
        get() = getDblParam(CplexJnrLib.CPXPARAM_MIP_Tolerances_MIPGap)
        set(value) = setDblParam(CplexJnrLib.CPXPARAM_MIP_Tolerances_MIPGap, value)

    override var maxMipGapAbs: Double
        get() = getDblParam(CplexJnrLib.CPXPARAM_MIP_Tolerances_AbsMIPGap)
        set(value) = setDblParam(CplexJnrLib.CPXPARAM_MIP_Tolerances_AbsMIPGap, value)

    override var maxNodes: Int
        get() = getIntParam(CplexJnrLib.CPXPARAM_MIP_Limits_Nodes)
        set(value) = setIntParam(CplexJnrLib.CPXPARAM_MIP_Limits_Nodes, value)

    override var maxSeconds: Double
        get() = getDblParam(CplexJnrLib.CPXPARAM_TimeLimit)
        set(value) = setDblParam(CplexJnrLib.CPXPARAM_TimeLimit, value)

    override var maxSolutions: Int
        get() = getIntParam(CplexJnrLib.CPXPARAM_MIP_Limits_Solutions)
        set(value) = setIntParam(CplexJnrLib.CPXPARAM_MIP_Limits_Solutions, value)

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
            lib.CPXchgobj(env, lp, nz, vars, objs)
        }

    override var objectiveConst: Double
        get() {
            val dblRef = DoubleByReference()
            lib.CPXgetobjoffset(env, lp, dblRef)
            return dblRef.value
        }
        set(value) {
            lib.CPXchgobjoffset(env, lp, value)
        }

    override var optTol: Double
        get() = getDblParam(CplexJnrLib.CPXPARAM_MIP_Tolerances_RelObjDifference)
        set(value) = setDblParam(CplexJnrLib.CPXPARAM_MIP_Tolerances_RelObjDifference, value)

    // override var plog: Boolean
    // override var preprocess: Int
    // override var roundIntVars: Boolean

    override var seed: Int
        get() = getIntParam(CplexJnrLib.CPXPARAM_RandomSeed)
        set(value) = setIntParam(CplexJnrLib.CPXPARAM_RandomSeed, value)

    override var sense: String
        get() = if (lib.CPXgetobjsen(env, lp) == CplexJnrLib.CPX_MIN) MINIMIZE else MAXIMIZE
        set(value) {
            when (value) {
                MINIMIZE -> lib.CPXchgobjsen(env, lp, CplexJnrLib.CPX_MIN)
                MAXIMIZE -> lib.CPXchgobjsen(env, lp, CplexJnrLib.CPX_MAX)
                else -> throw IllegalArgumentException("Model sense '$value' is invalid.")
            }
        }

    // override var solPoolSize: Boolean
    // override var start: Int
    // override var storeSearchProgressLog: Double

    override var threads: Int
        get() = getIntParam(CplexJnrLib.CPXPARAM_Threads)
        set(value) = setIntParam(CplexJnrLib.CPXPARAM_Threads, value)

    // endregion properties override

    // region buffers

    private var statusInt = 0
    private val statusPtr = IntByReference()

    private var pi: DoubleArray? = null
        get() {
            if (field == null && hasSolution) {
                field = DoubleArray(numRows)
                val res = lib.CPXgetpi(env, lp, field, 0, numRows - 1)
                assert(res == 0)
            }
            return field
        }

    private var rc: DoubleArray? = null
        get() {
            if (field == null && hasSolution) {
                field = DoubleArray(numCols)
                val res = lib.CPXgetdj(env, lp, field, 0, numCols - 1)
                assert(res == 0)
            }
            return field
        }

    private var solution: DoubleArray? = null
        get() {
            if (field == null && hasSolution) {
                field = DoubleArray(numCols)
                val res = lib.CPXgetx(env, lp, field, 0, numCols - 1)
                assert(res == 0)
            }
            return field
        }

    private var solutions = HashMap<Int, Pointer>()

    // endregion buffers

    init {
        // initializing environment and problem
        env = lib.CPXopenCPLEX(statusPtr)
        assert(statusPtr.value == 0)
        lp = lib.CPXcreateprob(env, statusPtr, name)
        assert(statusPtr.value == 0)

        // setting sense (if needed)
        if (sense == MAXIMIZE) {
            val status = lib.CPXchgobjsen(env, lp, CplexJnrLib.CPX_MAX)
            assert(status == 0)
        }

        CLibrary.lib.fflush(null)
    }

    protected fun finalize() {
        // lib.CPXfreeprob(env, PointerByReference(lp))
        // lib.CPXcloseCPLEX(PointerByReference(env))
    }

    override fun addConstr(linExpr: LinExpr, name: String) {
        val nz = linExpr.size
        val rhs = doubleArrayOf(-linExpr.const)

        val sense = byteArrayOf(when (linExpr.sense) {
                                    LEQ -> 'L'.toByte()
                                    EQ -> 'E'.toByte()
                                    GEQ -> 'G'.toByte()
                                    else -> throw IllegalArgumentException("Invalid sense")
                                })

        // matbeg array (one item with value 0)
        val rmatbeg = intArrayOf(0)

        val indices = IntArray(nz)
        val vals = DoubleArray(nz)
        var i = 0
        for ((v, coeff) in linExpr.terms) {
            indices[i] = v.idx
            vals[i] = coeff
            i++
        }

        lib.CPXaddrows(env, lp, 0, 1, nz, rhs, sense, rmatbeg, indices, vals, null, arrayOf(name))
    }

    override fun addVar(name: String, obj: Double, lb: Double, ub: Double, varType: VarType,
                        column: Column) {
        // val nz = column.size
        val objArray = doubleArrayOf(obj)
        val lbArray = doubleArrayOf(lb)
        val ubArray = doubleArrayOf(ub)
        // TODO: if (ub == INF) GurobiJnrLib.GRB_INFINITY else ub)

        if (varType == VarType.Binary || varType == VarType.Integer)
            hasIntVars = true

        val vtype = byteArrayOf(when (varType) {
                                    VarType.Binary -> 'B'.toByte()
                                    VarType.Continuous -> 'C'.toByte()
                                    VarType.Integer -> 'I'.toByte()
                                })

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

        lib.CPXnewcols(env, lp, 1, objArray, lbArray, ubArray, vtype, arrayOf(name))
    }

    override fun optimize(relax: Boolean): OptimizationStatus {
        if (relax) TODO("Not yet implemented")

        // resetting buffers
        removeSolution()

        // optimizing... and flushing stdout
        statusInt = if (hasIntVars) lib.CPXmipopt(env, lp) else lib.CPXlpopt(env, lp)
        assert(statusInt == 0)
        CLibrary.lib.fflush(null)

        return status
    }

    override fun removeConstrs(constrs: Iterable<Constr>) {
        TODO("Not yet implemented")
    }

    override fun removeVars(vars: Iterable<Var>) {
        TODO("Not yet implemented")
    }

    override fun setProcessingLimits(maxSeconds: Double?, maxNodes: Int?, maxSolutions: Int?) {
        if (maxSeconds != null) this.maxSeconds = maxSeconds
        if (maxNodes != null) this.maxNodes = maxNodes
        if (maxSolutions != null) this.maxSolutions = maxSolutions
    }

    override fun write(path: String) {
        lib.CPXwriteprob(env, lp, path, null)
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
        pi?.get(idx) ?: throw Error("Solution not available")

    override fun getConstrRHS(idx: Int): Double = throw NotImplementedError()
    override fun setConstrRHS(idx: Int, value: Double): Unit = throw NotImplementedError()

    override fun getConstrSlack(idx: Int): Double {
        val dblRef = DoubleByReference()
        lib.CPXgetslack(env, lp, dblRef, idx, idx)
        return dblRef.value
    }

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
        lib.CPXgetlb(env, lp, dblRef, idx, idx)
        return dblRef.value
    }

    override fun setVarLB(idx: Int, value: Double) {
        // TODO lib.GRBsetdblattrelement(lp, "LB", idx, value)
    }

    override fun getVarName(idx: Int): String {
        val name = Memory.allocateDirect(runtime, 256)
        val surplus = IntByReference()
        lib.CPXgetcolname(env, lp, arrayOf(name), name, 256, surplus, idx, idx)
        return name.getString(0)
    }

    override fun getVarObj(idx: Int): Double {
        val dblRef = DoubleByReference()
        lib.CPXgetobj(env, lp, dblRef, idx, idx)
        return dblRef.value
    }

    override fun setVarObj(idx: Int, value: Double) {
        // TODO lib.GRBsetdblattrelement(lp, "Obj", idx, value)
    }

    override fun getVarRC(idx: Int): Double =
        rc?.get(idx) ?: throw Error("Solution not available.")

    override fun getVarType(idx: Int): VarType {
        TODO("not implemented yet...")
        // return when (strBuffer.getByte(0).toChar()) {
        //     'B' -> VarType.Binary
        //     'I' -> VarType.Integer
        //     else -> VarType.Continuous
        // }
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
        val dblRef = DoubleByReference()
        lib.CPXgetub(env, lp, dblRef, idx, idx)
        return dblRef.value
    }

    override fun setVarUB(idx: Int, value: Double) {
        // TODO lib.GRBsetdblattrelement(lp, "UB", idx, value)
    }

    override fun getVarX(idx: Int): Double =
        solution?.get(idx) ?: throw Error("Solution not available")

    // override fun getVarXi(idx: Int, i: Int): Double {
    //     if (idx == 0) return getVarX(idx)
    //
    //     return getSolutionIdx(i)?.getDouble(8 * idx.toLong())
    //         ?: throw Error("Solution $i not available")
    // }

    // endregion variable getters and setters

    // region private useful functions

    private fun getDblParam(param: Int): Double {
        val dblRef = DoubleByReference()
        lib.CPXgetdblparam(env, param, dblRef)
        return dblRef.value
    }

    private fun setDblParam(param: Int, value: Double): Unit {
        lib.CPXsetdblparam(env, param, value)
    }

    private fun getIntParam(param: Int): Int {
        val intRef = IntByReference()
        lib.CPXgetintparam(env, param, intRef)
        return intRef.value
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
