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
            for (i in 0 until nz) {
                intBuffer.putInt(4 * i.toLong(), column.constrs[i].idx)
                dblBuffer.putDouble(8 * i.toLong(), column.coeffs[i])
            }
        }
        else {
            lib.Cbc_addCol(cbc, name, lb, ub, obj, isInteger, nz, intBuffer, dblBuffer)
        }
    }

    override fun get(param: String): Any {
        return when (param) {
            "objective" -> getObjectiveExpr()
            "objectiveBound" -> lib.Cbc_getObjValue(cbc)
            "objectiveValue" -> lib.Cbc_getBestPossibleObjValue(cbc)
            "sense"-> if (lib.Cbc_getObjSense(cbc) > 0) MINIMIZE else MAXIMIZE
            else -> throw NotImplementedError("Parameter currently unavailable in CBC interface")
        }
    }

    override fun optimize(): OptimizationStatus {
        // resetting buffers
        rc = null
        solution = null

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
            for ((i,idx) in constrs.withIndex())
                intBuffer.putInt(4*i.toLong(), idx)
            lib.Cbc_deleteRows(cbc, constrs.size, intBuffer)
        }

        // remove variables
        if (vars.isNotEmpty()) {
            checkBuffer(vars.size)
            for ((i,idx) in vars.withIndex())
                intBuffer.putInt(4*i.toLong(), idx)
            lib.Cbc_deleteCols(cbc, vars.size, intBuffer)
        }
    }

    override fun <T> set(param: String, value: T) {
        when (param) {
            "objective" -> setObjectiveExpr(value as LinExpr)
            "sense"-> lib.Cbc_setObjSense(cbc, if (value == MAXIMIZE) 1.0 else -1.0)
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

    private fun getObjectiveExpr(): LinExpr {
        TODO("Not yet implemented")
    }

    private fun setObjectiveExpr(linExpr: LinExpr) {
        TODO("Not yet implemented")
    }

    // region variable getters and setters

    override fun getVarColumn(idx: Int): Column = throw NotImplementedError()
    override fun setVarColumn(idx: Int, value: Column): Unit = throw NotImplementedError()

    override fun getVarIdx(name: String): Int = throw NotImplementedError()

    override fun getVarLB(idx: Int) = lib.Cbc_getColLB(cbc, idx)
    override fun setVarLB(idx: Int, value: Double) = lib.Cbc_setColLower(cbc, idx, value)

    override fun getVarName(idx: Int): String {
        lib.Cbc_getColName(cbc, idx, strBuffer, 1024)
        return strBuffer.getString(0)
    }

    override fun setVarName(idx: Int, value: String) = throw NotImplementedError()

    override fun getVarObj(idx: Int): Double = lib.Cbc_getColObj(cbc, idx)
    override fun setVarObj(idx: Int, value: Double) = lib.Cbc_setObjCoeff(cbc, idx, value)

    override fun getVarRC(idx: Int): Double {
        return rc?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available.")
    }

    override fun getVarType(idx: Int): VarType = throw NotImplementedError()
    override fun setVarType(idx: Int, value: VarType): Unit = throw NotImplementedError()

    override fun getVarUB(idx: Int) = lib.Cbc_getColUB(cbc, idx)
    override fun setVarUB(idx: Int, value: Double) = lib.Cbc_setColUpper(cbc, idx, value)

    override fun getVarX(idx: Int): Double {
        return solution?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available")
    }

    override fun getVarXi(idx: Int, i: Int): Double = throw NotImplementedError()

    // endregion variable getters and setters
}
