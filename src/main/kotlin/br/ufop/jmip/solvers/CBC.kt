package br.ufop.jmip.solvers

import br.ufop.jmip.entities.*
import jnr.ffi.LibraryLoader
import jnr.ffi.Pointer
import jnr.ffi.Runtime


class CBC(model: Model, name: String, sense: String) : Solver(model, name, sense) {

    var cbc: Pointer

    val lib: CBCLibrary = LibraryLoader.create(CBCLibrary::class.java)
        .load("/Docs/Dev/python-mip/mip/libraries/cbc-c-darwin-x86-64.dylib")

    val runtime: Runtime = Runtime.getRuntime(lib)

    init {
        cbc = lib.Cbc_newModel()
        if (sense == MAXIMIZE)
            lib.Cbc_setObjSense(cbc, -1.0)
    }

    override fun addConstr(linExpr: LinExpr, name: String) {
        val nz = linExpr.size
        val cols = IntArray(nz)
        val coeffs = DoubleArray(nz)
        val rhs = -linExpr.const
        val sense = linExpr.sense.toByte()

        var i = 0
        for ((v, coeff) in linExpr.terms) {
            cols[i] = v.idx
            coeffs[i] = coeff
            i++
        }

        lib.Cbc_addRow(cbc, name, nz, cols, coeffs, sense, rhs)
    }

    override fun addVar(name: String, obj: Double, lb: Double, ub: Double, varType: VarType,
                        column: Column) {
        val nz = column.size
        var rows: IntArray? = null
        var coeffs: DoubleArray? = null

        if (nz > 0) {
            rows = IntArray(nz) { column.constrs[it].idx }
            coeffs = DoubleArray(nz) { column.coeffs[it].toDouble() }
        }

        val isInteger = when(varType) {
            VarType.BINARY -> CBCLibrary.CHAR_ONE
            VarType.CONTINUOUS -> CBCLibrary.CHAR_ZERO
            VarType.INTEGER -> CBCLibrary.CHAR_ONE
        }

        lib.Cbc_addCol(cbc, name, lb, ub, obj, isInteger, nz, rows, coeffs)
    }

    override fun optimize(): OptimizationStatus {
        lib.Cbc_solve(cbc)
        return OptimizationStatus.OTHER
    }

    override fun write(path: String) {
        if (path.endsWith(".lp"))
            lib.Cbc_writeLp(cbc, path)
        else if  (path.endsWith(".mps"))
            lib.Cbc_writeMps(cbc, path)
    }
}
