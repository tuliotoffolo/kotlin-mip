package mip.solvers

import jnr.ffi.*
import jnr.ffi.byref.IntByReference
import jnr.ffi.byref.PointerByReference
import mip.*
import kotlin.jvm.internal.Ref

class Gurobi(model: Model, name: String, sense: String) : Solver(model, name, sense) {

    override val hasSolution get() = nSolutions > 0

    private var env: Pointer
    private var gurobi: Pointer
    private val lib = GurobiLibrary.lib
    private val runtime: Runtime = Runtime.getRuntime(lib)
    private var nSolutions = 0

    // region properties override

    override val nCols: Int
        get() {
            lib.GRBgetintattr(gurobi, "NumVars", intBuffer)
            return intBuffer.getInt(0)
        }

    override val nRows: Int
        get() {
            lib.GRBgetintattr(gurobi, "NumConstrs", intBuffer)
            return intBuffer.getInt(0)
        }

    override var objective: LinExpr
        get() {
            TODO("Not yet implemented")
        }
        set(value:LinExpr) {
            TODO("Not yet implemented")
        }

    override var status = OptimizationStatus.Loaded
        private set // TODO("Implement this...")

    // endregion properties override

    // region buffers

    private var bufferLength = 8192
    private var dblBuffer = Memory.allocateDirect(runtime, bufferLength * 8)
    private var intBuffer = Memory.allocateDirect(runtime, bufferLength * 4)
    private var strBuffer = Memory.allocateDirect(runtime, bufferLength * 1)

    private var pi: Pointer? = null
        get() {
            if (field == null && hasSolution) {
                field = Memory.allocateDirect(runtime, 8 * nRows)
                val res = lib.GRBgetdblattrarray(gurobi, "Pi", 0, nRows, field)
            }
            return field
        }

    private var rc: Pointer? = null
        get() {
            if (field == null && hasSolution) {
                field = Memory.allocateDirect(runtime, 8 * nCols)
                val res = lib.GRBgetdblattrarray(gurobi, "RC", 0, nCols, field)
            }
            return field
        }

    private var solution: Pointer? = null
        get() {
            if (field == null && hasSolution) {
                field = Memory.allocateDirect(runtime, 8 * nCols)
                val res = lib.GRBgetdblattrarray(gurobi, "X", 0, nCols, field)
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


    override fun addConstr(linExpr: LinExpr, name: String) {
        val nz = linExpr.size
        val rhs = -linExpr.const
        val sense = linExpr.sense.toByte()

        checkBuffer(nz)
        var i = 0L
        for ((v, coeff) in linExpr.terms) {
            intBuffer.putInt(4L * i, v.idx)
            dblBuffer.putDouble(8L * i, coeff)
            i++
        }

        lib.GRBaddconstr(gurobi, nz, intBuffer, dblBuffer, sense, rhs, name)
    }

    override fun addVar(name: String, obj: Double, lb: Double, ub: Double, varType: VarType,
                        column: Column) {
        val nz = column.size

        val vtype = when (varType) {
            VarType.Binary -> 'B'.toByte()
            VarType.Continuous -> 'C'.toByte()
            VarType.Integer -> 'I'.toByte()
        }

        if (nz > 0) {
            checkBuffer(nz)
            var i = 0L
            for ((constr, coeff) in column.terms) {
                intBuffer.putInt(4 * i, constr.idx)
                dblBuffer.putDouble(8 * i, coeff)
                i++
            }
            lib.GRBaddvar(gurobi, nz, intBuffer, dblBuffer, obj, lb, ub, vtype, name)
        }
        else {
            lib.GRBaddvar(gurobi, nz, null, null, obj, lb, ub, vtype, name)
        }
    }

    override fun get(param: String): Any = when (param) {
        // "cutoff" -> lib.Cbc_getCutoff(gurobi)
        // "maxMipGap" -> lib.Cbc_getAllowableFractionGap(gurobi)
        // "maxMipGapAbs" -> lib.Cbc_getAllowableGap(gurobi)
        // "numSolutions" -> lib.Cbc_numberSavedSolutions(gurobi)
        // "objectiveBound" -> lib.Cbc_getBestPossibleObjValue(gurobi)
        // "objectiveValue" -> lib.Cbc_getObjValue(gurobi)
        // "sense" -> if (lib.Cbc_getObjSense(gurobi) > 0) MINIMIZE else MAXIMIZE

        else -> throw NotImplementedError("Parameter currently unavailable in Gurobi interface")
    }

    override fun optimize(): OptimizationStatus {
        // resetting buffers
        removeSolution()

        // optimizing...
        lib.GRBoptimize(gurobi)

        // capturing number of solutions
        lib.GRBgetintattr(gurobi, "SolCount", intBuffer)
        nSolutions = intBuffer.getInt(0)

        lib.fflush(null)

        // lib.Cbc_solve(gurobi)
        // if (lib.Cbc_isProvenOptimal(gurobi) != 0 || lib.Cbc_getNumIntegers(gurobi) > 0) {
        //     nSolutions = lib.Cbc_numberSavedSolutions(gurobi)
        // }

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
            lib.GRBdelconstrs(gurobi, size, intBuffer)
        }
    }

    override fun removeVars(vars: Iterable<Var>) {
        val size = if (vars is Collection<*>) vars.size else vars.count()
        if (size > 0) {
            checkBuffer(size)
            for ((i, variable) in vars.withIndex())
                intBuffer.putInt(4 * i.toLong(), variable.idx)
            lib.GRBdelvars(gurobi, size, intBuffer)
        }
    }

    override fun <T> set(param: String, value: T) {
        when (param) {
            // "cutoff" -> lib.Cbc_setCutoff(gurobi, value as Double)
            // "maxMipGap" -> lib.Cbc_setAllowableFractionGap(gurobi, value as Double)
            // "maxMipGapAbs" -> lib.Cbc_setAllowableGap(gurobi, value as Double)
            // "seed" -> lib.Cbc_setIntParam(gurobi, CBCLibrary.INT_PARAM_RANDOM_SEED, value as Int)
            // "sense" -> lib.Cbc_setObjSense(gurobi, if (value == MAXIMIZE) -1.0 else 1.0)
            // "threads" -> lib.Cbc_setParameter(gurobi, "threads", value.toString())
            // "timeLimit" -> lib.Cbc_setMaximumSeconds(gurobi, value as Double)

            else -> throw NotImplementedError("Parameter currently unavailable in Gurobi interface")
        }
    }

    override fun write(path: String) {
        lib.GRBwrite(gurobi, path)
    }


    // region constraints getters and setters

    override fun getConstrExpr(idx: Int): LinExpr = throw NotImplementedError()
    override fun setConstrExpr(idx: Int, value: LinExpr): Unit = throw NotImplementedError()

    override fun getConstrName(idx: Int): String {
        lib.GRBgetstrattr(gurobi, "VarName", PointerByReference(strBuffer))
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
        lib.GRBgetdblattrelement(gurobi, "LB", idx, dblBuffer)
        return dblBuffer.getDouble(0)
    }
    override fun setVarLB(idx: Int, value: Double) {
        lib.GRBsetdblattrelement(gurobi, "LB", idx, value)
    }

    override fun getVarName(idx: Int): String {
        lib.GRBgetstrattr(gurobi, "VarName", PointerByReference(strBuffer))
        return strBuffer.getString(0)
    }

    override fun getVarObj(idx: Int): Double {
        lib.GRBgetdblattrelement(gurobi, "Obj", idx, dblBuffer)
        return dblBuffer.getDouble(0)
    }
    override fun setVarObj(idx: Int, value: Double) {
        lib.GRBsetdblattrelement(gurobi, "Obj", idx, value)
    }

    override fun getVarRC(idx: Int): Double =
        rc?.getDouble(8 * idx.toLong()) ?: throw Error("Solution not available.")

    override fun getVarType(idx: Int): VarType {
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
        lib.GRBgetdblattrelement(gurobi, "UB", idx, dblBuffer)
        return dblBuffer.getDouble(0)
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

    private fun checkBuffer(nz: Int) {
        if (nz > bufferLength) {
            bufferLength = nz
            dblBuffer = Memory.allocateDirect(runtime, bufferLength * 8)
            intBuffer = Memory.allocateDirect(runtime, bufferLength * 4)
        }
    }

    // private fun getSolutionIdx(idx: Int): Pointer? {
    //     if (idx in solutions) return solutions[idx]
    //
    //     val sol = lib.Cbc_savedSolution(gurobi, idx)
    //     solutions[idx] = sol
    //     return sol
    // }

    private fun removeSolution() {
        pi = null
        rc = null
        solution = null
        solutions.clear()
    }

    // endregion private useful functions
}
