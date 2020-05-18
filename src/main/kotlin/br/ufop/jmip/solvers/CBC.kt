package br.ufop.jmip.solvers

import br.ufop.jmip.*
import jnr.ffi.LibraryLoader
import jnr.ffi.Memory
import jnr.ffi.Pointer
import jnr.ffi.Runtime

class CBC(model: Model, name: String, sense: String) : Solver(model, name, sense) {

    // region override properties

    override val hasSolution get() = nSolutions > 0
    override val objectiveValue get() = lib.Cbc_getObjValue(cbc)

    // endregion override properties

    // cbc-related variables
    private var cbc: Pointer
    private val lib: CBCLibrary = LibraryLoader.create(CBCLibrary::class.java)
        .load("/Docs/Dev/python-mip/mip/libraries/cbc-c-darwin-x86-64.dylib")
    private val runtime: Runtime = Runtime.getRuntime(lib)

    // buffers (to speed up things)
    private var bufferLength = 8192
    private var dblBuffer = Memory.allocateDirect(runtime, bufferLength * 8)
    private var intBuffer = Memory.allocateDirect(runtime, bufferLength * 4)
    private var strBuffer = Memory.allocateDirect(runtime, bufferLength * 1)

    // solution information
    private var nSolutions = 0

    // region solution buffers
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

    // endregion solution buffers

    init {
        // initializing the solver/model
        cbc = lib.Cbc_newModel()

        // setting sense (if needed)
        if (sense == MAXIMIZE)
            lib.Cbc_setObjSense(cbc, -1.0)

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
            VarType.BINARY -> CBCLibrary.CHAR_ONE
            VarType.CONTINUOUS -> CBCLibrary.CHAR_ZERO
            VarType.INTEGER -> CBCLibrary.CHAR_ONE
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

    override fun optimize(): OptimizationStatus {
        nSolutions = lib.Cbc_solve(cbc)
        lib.fflush(null)
        return OptimizationStatus.OTHER
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
