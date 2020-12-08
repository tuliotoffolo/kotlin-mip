package mip.solvers

import jnr.ffi.*
import mip.*

class Cbc(model: Model, name: String, sense: String) : Solver(model, name, sense) {

    override val solverName = "CBC"

    override val INF = Double.MAX_VALUE
    override val INT_MAX = Int.MAX_VALUE

    private var cbc: Pointer
    private val lib = CbcJnrLib.loadLibrary()
    private val runtime: Runtime = Runtime.getRuntime(lib)

    // region properties override

    override val hasSolution get() = numSolutions > 0

    override var objective: LinExpr
        get() {
            val obj = LinExpr(objectiveConst)
            obj.sense = sense
            for (v in model.vars)
                obj += v.obj * v
            return obj
        }
        set(linExpr: LinExpr) {
            if (linExpr.sense == MINIMIZE || linExpr.sense == MAXIMIZE)
                this.sense = linExpr.sense
            else if (!linExpr.isAffine)
                throw Error("Only affine expressions are acceptable in objective functions.")

            this.objectiveConst = linExpr.const
            for ((v, coeff) in linExpr.terms)
                lib.Cbc_setObjCoeff(cbc, v.idx, coeff)
        }

    override var objectiveConst: Double = 0.0

    override val status: OptimizationStatus
        get() {
            if (lib.Cbc_isProvenOptimal(cbc) != 0)
                return OptimizationStatus.Optimal

            if (lib.Cbc_isAbandoned(cbc) != 0)
                return OptimizationStatus.Error

            if (lib.Cbc_isProvenInfeasible(cbc) != 0)
                return OptimizationStatus.Infeasible

            if (lib.Cbc_isContinuousUnbounded(cbc) != 0)
                return OptimizationStatus.Unbounded

            if (lib.Cbc_getNumIntegers(cbc) != 0)
                return OptimizationStatus.Feasible

            return OptimizationStatus.Other
        }

    // endregion properties override

    // region buffers

    private var pi: Pointer? = null
        get() {
            if (field == null && hasSolution)
                field = lib.Cbc_getRowPrice(cbc)
            return field
        }

    private var rc: Pointer? = null
        get() {
            if (field == null && hasSolution)
                field = lib.Cbc_getReducedCost(cbc)
            return field
        }

    private var solution: Pointer? = null
        get() {
            if (field == null && hasSolution)
                field = lib.Cbc_getColSolution(cbc)
            return field
        }

    private var solutions = HashMap<Int, Pointer>()

    // endregion buffers


    init {
        // initializing the solver/model
        this.cbc = lib.Cbc_newModel()
        lib.Cbc_storeNameIndexes(cbc, CbcJnrLib.CHAR_ONE)

        // setting sense (if needed)
        if (sense == MAXIMIZE)
            this.sense = MAXIMIZE

        // lib.fflush(null)
    }

    protected fun finalize() {
        // lib.Cbc_deleteModel(cbc)
    }


    override fun addConstr(linExpr: LinExpr, name: String) {
        val nz = linExpr.size
        val rhs = -linExpr.const
        val sense = linExpr.sense[0].toByte()

        val intBuffer = IntArray(nz)
        val dblBuffer = DoubleArray(nz)
        var i = 0
        for ((v, coeff) in linExpr.terms) {
            intBuffer[i] = v.idx
            dblBuffer[i] = coeff
            i++
        }

        lib.Cbc_addRow(cbc, name, nz, intBuffer, dblBuffer, sense, rhs)
    }

    override fun addVar(name: String, obj: Double, lb: Double, ub: Double, varType: VarType,
                        column: Column) {
        val nz = column.size

        val isInteger = when (varType) {
            VarType.Binary -> CbcJnrLib.CHAR_ONE
            VarType.Continuous -> CbcJnrLib.CHAR_ZERO
            VarType.Integer -> CbcJnrLib.CHAR_ONE
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
            lib.Cbc_addCol(cbc, name, lb, ub, obj, isInteger, nz, intBuffer, dblBuffer)
        }
        else {
            lib.Cbc_addCol(cbc, name, lb, ub, obj, isInteger, nz, null, null)
        }
    }

    override fun optimize(relax: Boolean): OptimizationStatus {
        if (relax) TODO("Not yet implemented")

        // resetting buffers
        removeSolution()

        // optimizing and flushing stdout
        lib.Cbc_solve(cbc)
        lib.fflush(null)

        return status
    }

    override fun propertyGet(property: String): Any = when (property) {
        // vals
        "gap" -> if (hasSolution) (objectiveValue - objectiveBound) / objectiveBound else 1.0
        // "hasSolution" -> using property
        "numCols" -> lib.Cbc_getNumCols(cbc)
        "numInt" -> lib.Cbc_getNumIntegers(cbc)
        "numRows" -> lib.Cbc_getNumRows(cbc)
        "numNZ" -> lib.Cbc_getNumElements(cbc)
        "numSolutions" -> lib.Cbc_numberSavedSolutions(cbc)
        "objectiveBound" -> lib.Cbc_getBestPossibleObjValue(cbc)
        "objectiveValue" -> lib.Cbc_getObjValue(cbc)
        // "status" -> using property

        // vars
        // "clique" -> ???
        "cutoff" -> lib.Cbc_getCutoff(cbc)
        // "cutPasses" -> ???
        // "cuts" -> ???
        // "cutsGenerator" -> ???
        // "emphasis" -> ???
        "infeasTol" -> lib.Cbc_getPrimalTolerance(cbc)
        // "integerTol" -> lib.Cbc_getDblParam(cbc, CbcJnrLibrary.DBL_PARAM_INT_TOL)
        // "integerTol" -> ???
        // "lazyConstrsGenerator" -> ???
        // "lpMethod" -> ???
        "maxMipGap" -> lib.Cbc_getAllowableFractionGap(cbc)
        "maxMipGapAbs" -> lib.Cbc_getAllowableGap(cbc)
        "maxNodes" -> lib.Cbc_getMaximumNodes(cbc)
        "maxSeconds" -> lib.Cbc_getMaximumSeconds(cbc)
        // "objective" -> using property
        // "objectiveConst" -> using property
        // "optTol" -> ???
        // "preprocess" -> ???
        // "roundIntVars" -> ???
        // "seed" -> ???
        "sense" -> if (lib.Cbc_getObjSense(cbc) > 0) MINIMIZE else MAXIMIZE
        // "solPoolSize" -> ???
        // "start" -> ???
        // "searchProgressLog" -> ???
        // "storeSearchProgressLog" -> ???
        // "threads" -> ???
        "verbose" -> lib.Cbc_getLogLevel(cbc) == 1

        else -> throw NotImplementedError("Parameter $property is currently unavailable in CBC interface")
    }

    override fun <T> propertySet(property: String, value: T) = when (property) {
        "clique" -> lib.Cbc_setParameter(cbc, "clique", if (value == 0) "off" else "forceon")
        "cutoff" -> lib.Cbc_setCutoff(cbc, value as Double)
        // "cutPasses" -> ???
        // "cuts" -> ???
        // "cutsGenerator" -> ???
        // "emphasis" -> ???
        "infeasTol" -> lib.Cbc_setPrimalTolerance(cbc, value as Double)
        "integerTol" -> lib.Cbc_setDblParam(cbc, CbcJnrLib.DBL_PARAM_INT_TOL, value as Double)
        // "lazyConstrsGenerator" -> ???
        // "lpMethod" -> ???
        "maxMipGap" -> lib.Cbc_setAllowableFractionGap(cbc, value as Double)
        "maxMipGapAbs" -> lib.Cbc_setAllowableGap(cbc, value as Double)
        "maxNodes" -> lib.Cbc_setMaximumNodes(cbc, value as Int)
        "maxSeconds" -> lib.Cbc_setMaximumSeconds(cbc, value as Double)
        // "objective" -> using property
        // "objectiveConst" -> using property
        // "optTol" -> ???
        // "preprocess" -> ???
        // "roundIntVars" -> ???
        "seed" -> lib.Cbc_setIntParam(cbc, CbcJnrLib.INT_PARAM_RANDOM_SEED, value as Int)
        "sense" -> lib.Cbc_setObjSense(cbc, if (value == MAXIMIZE) -1.0 else 1.0)
        // "solPoolSize" -> ???
        // "start" -> ???
        // "searchProgressLog" -> ???
        // "storeSearchProgressLog" -> ???
        "threads" -> lib.Cbc_setIntParam(cbc, CbcJnrLib.INT_PARAM_THREADS, value as Int)
        "verbose" -> lib.Cbc_setLogLevel(cbc, if (value as Boolean) 1 else 0)

        else -> throw NotImplementedError("Parameter $property is currently unavailable in CBC interface")
    }

    override fun removeConstrs(constrs: Iterable<Constr>) {
        val size = if (constrs is Collection<*>) constrs.size else constrs.count()
        if (size > 0) {
            val intBuffer = IntArray(size)
            for ((i, constr) in constrs.withIndex())
                intBuffer[i] = constr.idx
            lib.Cbc_deleteRows(cbc, size, intBuffer)
        }
    }

    override fun removeVars(vars: Iterable<Var>) {
        val size = if (vars is Collection<*>) vars.size else vars.count()
        if (size > 0) {
            val intBuffer = IntArray(size)
            for ((i, variable) in vars.withIndex())
                intBuffer[i] = variable.idx
            lib.Cbc_deleteCols(cbc, size, intBuffer)
        }
    }

    override fun setProcessingLimits(maxSeconds: Double, maxNodes: Int, maxSolutions: Int) {
        TODO("Not yet implemented")
    }

    override fun <T> set(property: String, value: T) {
        when (value) {
            is Int -> lib.Cbc_setIntParam(cbc, convertParam(property), value)
            is Double -> lib.Cbc_setDblParam(cbc, convertParam(property), value)
            is String -> lib.Cbc_setParameter(cbc, property, value)

            else -> lib.Cbc_setParameter(cbc, property, value.toString())
        }
    }

    override fun write(path: String) {
        if (path.endsWith(".lp"))
            lib.Cbc_writeLp(cbc, path)
        else if (path.endsWith(".mps"))
            lib.Cbc_writeMps(cbc, path)
    }


    // region constraints getters and setters

    override fun getConstrExpr(idx: Int): LinExpr {
        val nz = lib.Cbc_getRowNz(cbc, idx)
        val vars = lib.Cbc_getRowIndices(cbc, idx)
        val coeffs = lib.Cbc_getRowCoeffs(cbc, idx)
        val rhs = lib.Cbc_getRowRHS(cbc, idx)

        val sense = when (lib.Cbc_getRowSense(cbc, idx).toChar().toUpperCase()) {
            'E' -> EQ
            'L' -> LEQ
            'G' -> GEQ
            else -> ""
        }

        val expr = LinExpr(-rhs)
        expr.sense = sense
        for (i in 0 until nz) {
            val v = vars.getInt(i.toLong() * 4)
            val coeff = coeffs.getDouble(i.toLong() * 4)
            expr.add(model.vars[v], coeff)
        }
        return expr
    }
    override fun setConstrExpr(idx: Int, value: LinExpr) {
        throw NotImplementedError()
    }

    override fun getConstrName(idx: Int): String {
        val strBuffer = String()
        lib.Cbc_getRowName(cbc, idx, strBuffer, 1024)
        return strBuffer
    }

    override fun getConstrPi(idx: Int): Double =
        pi?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available")

    override fun getConstrRHS(idx: Int): Double = throw NotImplementedError()
    override fun setConstrRHS(idx: Int, value: Double) {
        throw NotImplementedError()
    }

    override fun getConstrSlack(idx: Int): Double = throw NotImplementedError()

    // endregion constraints getters and setters

    // region variable getters and setters

    override fun getVarColumn(idx: Int): Column {
        // TODO
        // val nz = lib.Cbc_getColNz(cbc, idx)
        // if (nz == 0) return Column()
        //
        // val cidx = lib.Cbc_getColIndices(cbc, idx)
        // val ccoeff = lib.Cbc_getColCoeffs(cbc, idx)
        //
        // if (cidx.getPointer(0) == null || ccoeff.getPointer(0) == null)
        //     throw Error("Error getting column indices and/or column coefficients")
        //
        // val constrs = List<Constr>(nz) { model.constrs[cidx.getInt(4 * it.toLong())] }
        // val coeffs = List<Double>(nz) { ccoeff.getDouble(8 * it.toLong()) }
        //
        // return Column(constrs, coeffs)
        return Column.EMPTY
    }

    override fun setVarColumn(idx: Int, value: Column) =
        throw UnsupportedOperationException("CBC currently does not permit setting column")

    override fun getVarLB(idx: Int) = lib.Cbc_getColLB(cbc, idx)
    override fun setVarLB(idx: Int, value: Double) = lib.Cbc_setColLower(cbc, idx, value)

    override fun getVarName(idx: Int): String {
        val strBuffer = String()
        lib.Cbc_getColName(cbc, idx, strBuffer, 1024)
        return strBuffer
    }

    override fun getVarObj(idx: Int): Double = lib.Cbc_getColObj(cbc, idx)
    override fun setVarObj(idx: Int, value: Double) = lib.Cbc_setObjCoeff(cbc, idx, value)

    override fun getVarRC(idx: Int): Double =
        rc?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available.")

    override fun getVarType(idx: Int): VarType {
        val isInt = lib.Cbc_isInteger(cbc, idx) != 0

        if (isInt && getVarLB(idx) == 0.0 && getVarUB(idx) == 1.0)
            return VarType.Binary
        else if (isInt)
            return VarType.Integer
        else
            return VarType.Continuous
    }

    override fun setVarType(idx: Int, value: VarType) {
        when (value) {
            VarType.Continuous -> lib.Cbc_setContinuous(cbc, idx)
            VarType.Integer -> lib.Cbc_setInteger(cbc, idx)
            VarType.Binary -> {
                lib.Cbc_setInteger(cbc, idx)
                if (getVarLB(idx) != 0.0) setVarLB(idx, 0.0)
                if (getVarUB(idx) != 1.0) setVarUB(idx, 1.0)
            }
        }
    }

    override fun getVarUB(idx: Int) = lib.Cbc_getColUB(cbc, idx)
    override fun setVarUB(idx: Int, value: Double) = lib.Cbc_setColUpper(cbc, idx, value)

    override fun getVarX(idx: Int): Double =
        solution?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available")

    override fun getVarXi(idx: Int, i: Int): Double {
        if (idx == 0) return getVarX(idx)

        return getSolutionIdx(i).getDouble(8 * idx.toLong())
    }

    // endregion variable getters and setters

    // region private useful functions

    private fun convertParam(property: String): Int = CbcJnrLib.constantsMap[property] ?: -1

    private fun getSolutionIdx(idx: Int): Pointer {
        if (idx in solutions) return solutions[idx]!!

        val sol = lib.Cbc_savedSolution(cbc, idx)
        solutions[idx] = sol
        return sol
    }

    private fun removeSolution() {
        pi = null
        rc = null
        solution = null
        solutions.clear()
    }

    // endregion private useful functions
}