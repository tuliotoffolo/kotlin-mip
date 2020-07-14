package mip.solvers

import mip.*

class EmptySolver(model: Model, name: String, sense: String) : Solver(model, name, sense) {

    override val hasSolution: Boolean = false
    override val solverName: String = "EMPTY-SOLVER"

    override fun addConstr(linExpr: LinExpr, name: String) {}

    override fun addVar(name: String, obj: Double, lb: Double, ub: Double, varType: VarType, column: Column) {}

    override fun optimize(): OptimizationStatus = OptimizationStatus.Error

    override fun removeConstrs(constrs: Iterable<Constr>) {}

    override fun removeVars(vars: Iterable<Var>) {}
}