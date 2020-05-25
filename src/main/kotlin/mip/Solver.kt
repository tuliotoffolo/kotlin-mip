package mip

import kotlin.reflect.KProperty

abstract class Solver(val model: Model, var name: String, sense: String = MINIMIZE) : Parameters() {

    abstract val hasSolution: Boolean

    abstract fun addConstr(linExpr: LinExpr, name: String): Unit

    abstract fun addVar(name: String, obj: Double, lb: Double, ub: Double, varType: VarType,
                        column: Column): Unit

    abstract fun optimize(): OptimizationStatus

    open fun relax(): Unit = throw NotImplementedError()

    abstract fun removeConstrs(constrs: Iterable<Constr>)

    abstract fun removeVars(vars: Iterable<Var>)

    open fun write(path: String): Unit = throw NotImplementedError()

    // region constraints getters and setters

    open fun getConstrExpr(idx: Int): LinExpr = throw NotImplementedError()
    open fun setConstrExpr(idx: Int, value: LinExpr): Unit = throw NotImplementedError()

    open fun getConstrName(idx: Int): String = throw NotImplementedError()
    open fun setConstrName(idx: Int, value: String): Unit = throw NotImplementedError()

    open fun getConstrPi(idx: Int): Double = throw NotImplementedError()

    open fun getConstrRHS(idx: Int): Double = throw NotImplementedError()
    open fun setConstrRHS(idx: Int, value: Double): Unit = throw NotImplementedError()

    open fun getConstrSlack(idx: Int): Double = throw NotImplementedError()

    // endregion constraints getters and setters

    // region variable getters and setters

    open fun getVarColumn(idx: Int): Column = throw NotImplementedError()
    open fun setVarColumn(idx: Int, value: Column): Unit = throw NotImplementedError()

    open fun getVarLB(idx: Int): Double = throw NotImplementedError()
    open fun setVarLB(idx: Int, value: Double): Unit = throw NotImplementedError()

    open fun getVarName(idx: Int): String = throw NotImplementedError()
    open fun setVarName(idx: Int, value: String): Unit = throw NotImplementedError()

    open fun getVarObj(idx: Int): Double = throw NotImplementedError()
    open fun setVarObj(idx: Int, value: Double): Unit = throw NotImplementedError()

    open fun getVarRC(idx: Int): Double = throw NotImplementedError()

    open fun getVarType(idx: Int): VarType = throw NotImplementedError()
    open fun setVarType(idx: Int, value: VarType): Unit = throw NotImplementedError()

    open fun getVarUB(idx: Int): Double = throw NotImplementedError()
    open fun setVarUB(idx: Int, value: Double): Unit = throw NotImplementedError()

    open fun getVarX(idx: Int): Double = throw NotImplementedError()

    open fun getVarXi(idx: Int, i: Int): Double = throw NotImplementedError()

    // endregion variable getters and setters
}
