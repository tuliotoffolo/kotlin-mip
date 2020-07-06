package mip.solvers

import jnr.ffi.*
import mip.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty

class Cbc(model: Model, name: String, sense: String) : Solver(model, name, sense) {

    override val solverName = "CBC"

    private var cbc: Pointer
    private val lib = CbcJnr.lib
    private val runtime: Runtime = Runtime.getRuntime(lib)
    private var nSolutions = 0

    // region properties override

    override val hasSolution get() = nSolutions > 0

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

    override var status = OptimizationStatus.Loaded
        private set

    // endregion properties override

    // region buffers

    private var bufferLength = 8192
    private var dblBuffer = Memory.allocateDirect(runtime, bufferLength * 8)
    private var intBuffer = Memory.allocateDirect(runtime, bufferLength * 4)
    private var strBuffer = Memory.allocateDirect(runtime, bufferLength * 1)

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
        lib.Cbc_storeNameIndexes(cbc, CbcJnr.CHAR_ONE)

        // setting sense (if needed)
        if (sense == MAXIMIZE)
            this.sense = MAXIMIZE

        lib.fflush(null)
    }

    fun finalize() {
        // lib.Cbc_deleteModel(cbc)
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

        lib.Cbc_addRow(cbc, name, nz, intBuffer, dblBuffer, sense, rhs)
    }

    override fun addVar(name: String, obj: Double, lb: Double, ub: Double, varType: VarType,
                        column: Column) {
        val nz = column.size

        val isInteger = when (varType) {
            VarType.Binary -> CbcJnr.CHAR_ONE
            VarType.Continuous -> CbcJnr.CHAR_ZERO
            VarType.Integer -> CbcJnr.CHAR_ONE
        }

        if (nz > 0) {
            checkBuffer(nz)
            var i = 0L
            for ((constr, coeff) in column.terms) {
                intBuffer.putInt(4 * i, constr.idx)
                dblBuffer.putDouble(8 * i, coeff)
                i++
            }
            lib.Cbc_addCol(cbc, name, lb, ub, obj, isInteger, nz, intBuffer, dblBuffer)
        }
        else {
            lib.Cbc_addCol(cbc, name, lb, ub, obj, isInteger, nz, null, null)
        }
    }

    override fun get(property: KProperty<*>): Any = when (property.name) {
        "cutoff" -> lib.Cbc_getCutoff(cbc)
        "maxMipGap" -> lib.Cbc_getAllowableFractionGap(cbc)
        "maxMipGapAbs" -> lib.Cbc_getAllowableGap(cbc)
        "maxNodes" -> lib.Cbc_getMaximumNodes(cbc)
        "maxSeconds" -> lib.Cbc_getMaximumSeconds(cbc)
        "numSolutions" -> lib.Cbc_numberSavedSolutions(cbc)
        "objectiveBound" -> lib.Cbc_getBestPossibleObjValue(cbc)
        "objectiveValue" -> lib.Cbc_getObjValue(cbc)
        "sense" -> if (lib.Cbc_getObjSense(cbc) > 0) MINIMIZE else MAXIMIZE

        else -> throw NotImplementedError("Parameter currently unavailable in CBC interface")
    }

    override fun optimize(): OptimizationStatus {
        // resetting buffers
        removeSolution()

        // optimizing...
        lib.Cbc_solve(cbc)
        if (lib.Cbc_isProvenOptimal(cbc) != 0 || lib.Cbc_getNumIntegers(cbc) > 0) {
            nSolutions = lib.Cbc_numberSavedSolutions(cbc)
        }

        // flushing stdout
        lib.fflush(null)

        return OptimizationStatus.Other
    }

    override fun removeConstrs(constrs: Iterable<Constr>) {
        val size = if (constrs is Collection<*>) constrs.size else constrs.count()
        if (size > 0) {
            checkBuffer(size)
            for ((i, constr) in constrs.withIndex())
                intBuffer.putInt(4 * i.toLong(), constr.idx)
            lib.Cbc_deleteRows(cbc, size, intBuffer)
        }
    }

    override fun removeVars(vars: Iterable<Var>) {
        val size = if (vars is Collection<*>) vars.size else vars.count()
        if (size > 0) {
            checkBuffer(size)
            for ((i, variable) in vars.withIndex())
                intBuffer.putInt(4 * i.toLong(), variable.idx)
            lib.Cbc_deleteCols(cbc, size, intBuffer)
        }
    }

    override fun <T> set(property: KMutableProperty<*>, value: T) {
        when (property.name) {
            "cutoff" -> lib.Cbc_setCutoff(cbc, value as Double)
            "maxMipGap" -> lib.Cbc_setAllowableFractionGap(cbc, value as Double)
            "maxMipGapAbs" -> lib.Cbc_setAllowableGap(cbc, value as Double)
            "maxNodes" -> lib.Cbc_setMaximumNodes(cbc, value as Int)
            "maxSeconds" -> lib.Cbc_setMaximumSeconds(cbc, value as Double)
            "seed" -> lib.Cbc_setIntParam(cbc, CbcJnr.INT_PARAM_RANDOM_SEED, value as Int)
            "sense" -> lib.Cbc_setObjSense(cbc, if (value == MAXIMIZE) -1.0 else 1.0)
            "threads" -> lib.Cbc_setParameter(cbc, "threads", value.toString())
            "timeLimit" -> lib.Cbc_setMaximumSeconds(cbc, value as Double)

            else -> throw NotImplementedError("Parameter ${property.name} is currently unavailable in CBC interface")
        }
    }

    override fun write(path: String) {
        if (path.endsWith(".lp"))
            lib.Cbc_writeLp(cbc, path)
        else if (path.endsWith(".mps"))
            lib.Cbc_writeMps(cbc, path)
    }


    // region constraints getters and setters

    override fun getConstrExpr(idx: Int): LinExpr = throw NotImplementedError()
    override fun setConstrExpr(idx: Int, value: LinExpr): Unit = throw NotImplementedError()

    override fun getConstrName(idx: Int): String {
        lib.Cbc_getRowName(cbc, idx, strBuffer, 1024)
        return strBuffer.getString(0)
    }

    override fun getConstrPi(idx: Int): Double =
        pi?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available")

    override fun getConstrRHS(idx: Int): Double = throw NotImplementedError()
    override fun setConstrRHS(idx: Int, value: Double): Unit = throw NotImplementedError()

    override fun getConstrSlack(idx: Int): Double = throw NotImplementedError()

    // endregion constraints getters and setters

    // region variable getters and setters

    override fun getVarColumn(idx: Int): Column {
        val nz = lib.Cbc_getColNz(cbc, idx)
        if (nz == 0) return Column()

        val cidx = lib.Cbc_getColIndices(cbc, idx)
        val ccoeff = lib.Cbc_getColCoeffs(cbc, idx)

        if (cidx.getPointer(0) == null || ccoeff.getPointer(0) == null)
            throw Error("Error getting column indices and/or column coefficients")

        val constrs = List<Constr>(nz) { model.constrs[cidx.getInt(4 * it.toLong())] }
        val coeffs = List<Double>(nz) { ccoeff.getDouble(8 * it.toLong()) }

        return Column(constrs, coeffs)
    }

    override fun setVarColumn(idx: Int, value: Column) =
        throw UnsupportedOperationException("CBC currently does not permit setting column")

    override fun getVarLB(idx: Int) = lib.Cbc_getColLB(cbc, idx)
    override fun setVarLB(idx: Int, value: Double) = lib.Cbc_setColLower(cbc, idx, value)

    override fun getVarName(idx: Int): String {
        lib.Cbc_getColName(cbc, idx, strBuffer, 1024)
        return strBuffer.getString(0)
    }

    override fun getVarObj(idx: Int): Double = lib.Cbc_getColObj(cbc, idx)
    override fun setVarObj(idx: Int, value: Double) = lib.Cbc_setObjCoeff(cbc, idx, value)

    override fun getVarRC(idx: Int): Double =
        rc?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available.")

    override fun getVarType(idx: Int): VarType {
        if (lib.Cbc_isInteger(cbc, idx) != 0 && getVarLB(idx) == 0.0 && getVarUB(idx) == 1.0)
            return VarType.Binary
        else if (lib.Cbc_isInteger(cbc, idx) != 0)
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

        return getSolutionIdx(i)?.getDouble(8 * idx.toLong())
            ?: throw Error("Solution $i not available")
    }

    // endregion variable getters and setters

    // region private useful functions

    private fun checkBuffer(nz: Int) {
        if (nz > bufferLength) {
            bufferLength = nz
            dblBuffer = Memory.allocateDirect(runtime, bufferLength * 8)
            intBuffer = Memory.allocateDirect(runtime, bufferLength * 4)
        }
    }

    private fun getSolutionIdx(idx: Int): Pointer? {
        if (idx in solutions) return solutions[idx]

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