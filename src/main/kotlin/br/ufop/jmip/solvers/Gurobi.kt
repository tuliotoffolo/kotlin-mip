package br.ufop.jmip.solvers

import br.ufop.jmip.*
import jnr.ffi.LibraryLoader
import jnr.ffi.Memory
import jnr.ffi.Pointer
import jnr.ffi.Runtime
import jnr.ffi.byref.DoubleByReference
import jnr.ffi.byref.PointerByReference

class Gurobi(model: Model, name: String, sense: String) : Solver(model, name, sense) {

    // region override properties

    override val hasSolution get() = nSolutions > 0
    override val objectiveValue: Double
        get() {
            var ref = DoubleByReference()
            lib.GRBgetdblattr(gurobi, "ObjVal", ref)
            return ref.value
        }

    // endregion override properties

    // gurobi-related variables
    private var env: Pointer
    private var gurobi: Pointer
    private val lib: GurobiLibrary = LibraryLoader.create(GurobiLibrary::class.java)
        .load("/opt/gurobi900/mac64/lib/libgurobi90.dylib")
    private val runtime: Runtime = Runtime.getRuntime(lib)

    // buffers (to speed up things)
    private var bufferLength = 8192
    private var dblBuffer = Memory.allocateDirect(runtime, bufferLength * 8)
    private var intBuffer = Memory.allocateDirect(runtime, bufferLength * 4)
    private var strBuffer = Memory.allocateDirect(runtime, bufferLength * 1)

    // flag to indicate that updating is required
    private var updated = true

    // solution information
    private val numCols: Int get() = model.vars.size
    private var nSolutions = 0

    // region solution buffers

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

    // endregion solution buffers

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
        val sense = linExpr.sense.toByte()

        checkBuffer(nz);
        var i: Long = 0
        for ((v, coeff) in linExpr.terms) {
            intBuffer.putInt(4 * i, v.idx)
            dblBuffer.putDouble(8 * i, coeff)
            i++
        }

        lib.GRBaddconstr(gurobi, nz, intBuffer, dblBuffer, sense, rhs, name)
    }

    override fun addVar(name: String, obj: Double, lb: Double, ub: Double, varType: VarType,
                        column: Column) {
        val nz = column.size

        val vtype = when (varType) {
            VarType.BINARY -> 'B'.toByte()
            VarType.CONTINUOUS -> 'C'.toByte()
            VarType.INTEGER -> 'I'.toByte()
        }

        if (nz > 0) {
            checkBuffer(nz)
            for (i in 0 until nz) {
                intBuffer.putInt(4 * i.toLong(), column.constrs[i].idx)
                dblBuffer.putDouble(8 * i.toLong(), column.coeffs[i])
            }
            lib.GRBaddvar(gurobi, nz, intBuffer, dblBuffer, obj, lb, ub, vtype, name)
        }
        else {
            lib.GRBaddvar(gurobi, nz, null, null, obj, lb, ub, vtype, name)
        }
    }

    override fun optimize(): OptimizationStatus {
        lib.GRBoptimize(gurobi)
        lib.fflush(null)
        return OptimizationStatus.OTHER
    }

    override fun write(path: String) {
        lib.GRBwrite(gurobi, path)
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

    override fun getVarLB(idx: Int): Double = throw NotImplementedError()
    override fun setVarLB(idx: Int, value: Double): Unit = throw NotImplementedError()

    override fun getVarName(idx: Int): String {
        lib.GRBgetstrattr(gurobi, "VarName", PointerByReference(strBuffer))
        return strBuffer.getString(0)
    }

    override fun setVarName(idx: Int, value: String) {
        lib.GRBsetstrattr(gurobi, "VarName", value)
    }

    override fun getVarObj(idx: Int): Double = throw NotImplementedError()
    override fun setVarObj(idx: Int, value: Double): Unit = throw NotImplementedError()

    override fun getVarRC(idx: Int): Double = throw NotImplementedError()

    override fun getVarType(idx: Int): VarType = throw NotImplementedError()
    override fun setVarType(idx: Int, value: VarType): Unit = throw NotImplementedError()

    override fun getVarUB(idx: Int): Double = throw NotImplementedError()
    override fun setVarUB(idx: Int, value: Double): Unit = throw NotImplementedError()

    override fun getVarX(idx: Int): Double {
        return solution?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available")
    }

    override fun getVarXi(idx: Int, i: Int): Double = throw NotImplementedError()

    // endregion variable getters and setters
}
