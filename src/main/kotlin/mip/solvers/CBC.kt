package mip.solvers

import mip.*
import jnr.ffi.LibraryLoader
import jnr.ffi.Memory
import jnr.ffi.Pointer
import jnr.ffi.Runtime

class CBC(model: Model, name: String, sense: String) : Solver(model, name, sense) {

    override val hasSolution get() = nSolutions > 0

    // cbc-related variables
    private var cbc: Pointer

    private val lib: CBCLibrary = LibraryLoader.create(CBCLibrary::class.java)
        .load("/Docs/Dev/python-mip/mip/libraries/cbc-c-darwin-x86-64.dylib")

    private val runtime: Runtime = Runtime.getRuntime(lib)

    private var nSolutions = 0

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
        cbc = lib.Cbc_newModel()

        // setting sense (if needed)
        if (sense == MAXIMIZE)
            this.sense = MAXIMIZE

        lib.fflush(null)
    }

    override fun addConstr(linExpr: LinExpr, name: String) {
        val nz = linExpr.size
        val rhs = -linExpr.const
        val sense = linExpr.sense.toByte()

        checkBuffer(nz)
        var i: Long = 0
        for ((v, coeff) in linExpr.terms) {
            intBuffer.putInt(4 * i, v.idx)
            dblBuffer.putDouble(8 * i, coeff)
            i++
        }

        lib.Cbc_addRow(cbc, name, nz, intBuffer, dblBuffer, sense, rhs)
    }

    override fun addVar(name: String, obj: Double, lb: Double, ub: Double, varType: VarType,
                        column: Column) {
        val nz = column.size

        val isInteger = when (varType) {
            VarType.Binary -> CBCLibrary.CHAR_ONE
            VarType.Continuous -> CBCLibrary.CHAR_ZERO
            VarType.Integer -> CBCLibrary.CHAR_ONE
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

    override fun get(param: String): Any {
        return when (param) {
            "objective" -> getObjectiveExpr()
            "objectiveBound" -> lib.Cbc_getObjValue(cbc)
            "objectiveValue" -> lib.Cbc_getBestPossibleObjValue(cbc)
            "sense" -> if (lib.Cbc_getObjSense(cbc) > 0) MINIMIZE else MAXIMIZE
            else -> throw NotImplementedError("Parameter currently unavailable in CBC interface")
        }
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

    override fun remove(iterable: Iterable<Any?>) {
        val constrs = ArrayList<Int>()
        val vars = ArrayList<Int>()

        for (term in iterable) {
            if (term == null) continue
            when (term) {
                is Constr -> constrs.add(term.idx)
                is Var -> vars.add(term.idx)
                else -> throw IllegalArgumentException()
            }
        }

        // remove constraints
        if (constrs.isNotEmpty()) {
            checkBuffer(constrs.size)
            for ((i, idx) in constrs.withIndex())
                intBuffer.putInt(4 * i.toLong(), idx)
            lib.Cbc_deleteRows(cbc, constrs.size, intBuffer)
        }

        // remove variables
        if (vars.isNotEmpty()) {
            checkBuffer(vars.size)
            for ((i, idx) in vars.withIndex())
                intBuffer.putInt(4 * i.toLong(), idx)
            lib.Cbc_deleteCols(cbc, vars.size, intBuffer)
        }
    }

    override fun <T> set(param: String, value: T) {
        when (param) {
            "objective" -> setObjectiveExpr(value as LinExpr)
            "sense" -> lib.Cbc_setObjSense(cbc, if (value == MAXIMIZE) 1.0 else -1.0)
            "threads" -> lib.Cbc_setParameter(cbc, "threads", value.toString())
            else -> throw NotImplementedError("Parameter currently unavailable in CBC interface")
        }
    }

    override fun write(path: String) {
        if (path.endsWith(".lp"))
            lib.Cbc_writeLp(cbc, path)
        else if (path.endsWith(".mps"))
            lib.Cbc_writeMps(cbc, path)
    }

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

    private fun getObjectiveExpr(): LinExpr {
        TODO("Not yet implemented")
    }

    private fun setObjectiveExpr(linExpr: LinExpr) {
        TODO("Not yet implemented")
    }

    private fun removeSolution() {
        pi = null
        rc = null
        solution = null
        solutions.clear()
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
}
