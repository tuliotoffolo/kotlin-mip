package mip.solvers

import mip.*

class EmptySolver(model: Model, name: String, sense: String) : Solver(model, name, sense) {

    override val solverName: String = "EMPTY-SOLVER"

    override val INF = Double.MAX_VALUE
    override val INT_MAX = Int.MAX_VALUE

    override val hasSolution: Boolean = false

    override fun addConstr(linExpr: LinExpr, name: String) {}

    override fun addVar(name: String, obj: Double, lb: Double, ub: Double, varType: VarType, column: Column) {}

    override fun optimize(relax: Boolean): OptimizationStatus = OptimizationStatus.Error

    override fun removeConstrs(constrs: Iterable<Constr>) {}

    override fun removeVars(vars: Iterable<Var>) {}

    override fun setProcessingLimits(maxSeconds: Double, maxNodes: Int, maxSolutions: Int) {}
}
