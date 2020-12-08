package mip

/**
 * Abstract class used to represent a MIP solver, which should only be instantiated by a [Model].
 *
 * @author Túlio Toffolo
 */
abstract class Solver(val model: Model, var name: String, sense: String = MINIMIZE) : Properties() {

    /** Solver-specific infinity representation */
    internal abstract val INT_MAX: Int
    internal abstract val INF: Double

    internal abstract val hasSolution: Boolean

    internal abstract fun addConstr(linExpr: LinExpr, name: String): Unit

    internal abstract fun addVar(name: String, obj: Double, lb: Double, ub: Double, varType: VarType,
                                 column: Column): Unit

    internal abstract fun optimize(relax: Boolean): OptimizationStatus

    internal open fun relax(): Unit = throw NotImplementedError()

    internal open fun read(path: String): Unit = throw NotImplementedError()

    internal abstract fun removeConstrs(constrs: Iterable<Constr>)

    internal abstract fun removeVars(vars: Iterable<Var>)

    internal abstract fun setProcessingLimits(maxSeconds: Double, maxNodes: Int, maxSolutions: Int)

    internal open fun write(path: String): Unit = throw NotImplementedError()

    // region constraints getters and setters

    internal open fun getConstrExpr(idx: Int): LinExpr = throw NotImplementedError()
    internal open fun setConstrExpr(idx: Int, value: LinExpr): Unit = throw NotImplementedError()

    internal open fun getConstrName(idx: Int): String = throw NotImplementedError()
    internal open fun setConstrName(idx: Int, value: String): Unit = throw NotImplementedError()

    internal open fun getConstrPi(idx: Int): Double = throw NotImplementedError()

    internal open fun getConstrRHS(idx: Int): Double = throw NotImplementedError()
    internal open fun setConstrRHS(idx: Int, value: Double): Unit = throw NotImplementedError()

    internal open fun getConstrSlack(idx: Int): Double = throw NotImplementedError()

    // endregion constraints getters and setters

    // region variable getters and setters

    internal open fun getVarColumn(idx: Int): Column = throw NotImplementedError()
    internal open fun setVarColumn(idx: Int, value: Column): Unit = throw NotImplementedError()

    internal open fun getVarLB(idx: Int): Double = throw NotImplementedError()
    internal open fun setVarLB(idx: Int, value: Double): Unit = throw NotImplementedError()

    internal open fun getVarName(idx: Int): String = throw NotImplementedError()
    internal open fun setVarName(idx: Int, value: String): Unit = throw NotImplementedError()

    internal open fun getVarObj(idx: Int): Double = throw NotImplementedError()
    internal open fun setVarObj(idx: Int, value: Double): Unit = throw NotImplementedError()

    internal open fun getVarRC(idx: Int): Double = throw NotImplementedError()

    internal open fun getVarType(idx: Int): VarType = throw NotImplementedError()
    internal open fun setVarType(idx: Int, value: VarType): Unit = throw NotImplementedError()

    internal open fun getVarUB(idx: Int): Double = throw NotImplementedError()
    internal open fun setVarUB(idx: Int, value: Double): Unit = throw NotImplementedError()

    internal open fun getVarX(idx: Int): Double = throw NotImplementedError()

    internal open fun getVarXi(idx: Int, i: Int): Double = throw NotImplementedError()

    // endregion variable getters and setters
}
