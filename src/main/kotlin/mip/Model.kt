package mip

import mip.solvers.Gurobi
import java.lang.Double.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.KProperty

/**
 * Model class
 *
 * Creates a Mixed-Integer Linear Programming Model. The default model optimization direction is
 * Minimization. To store and optimize the model the MIP package automatically searches and
 * connects in runtime to the dynamic library of some MIP solver installed on your computer.
 * Nowadays gurobi and cbc are supported. This solver is automatically selected, but you can
 * force the selection of a specific solver with the parameter [solverName].
 *
 * @property name: model name
 * @property sense: minimization ("MIN") or maximization ("MAX")
 * @property solverName: solver name ("CBC" or "GUROBI")
 */
class Model(var name: String = "JMipModel", sense: String = MINIMIZE,
            var solverName: String = CBC) : Parameters() {

    // region main components

    val hasSolution: Boolean get() = solver.hasSolution

    val constrs = ArrayList<Constr>()
    val vars = ArrayList<Var>()

    val solver: Solver

    // endregion main components

    init {
        // creating a solver instance
        solver = when (solverName.toUpperCase()) {
            CBC -> mip.solvers.CBC(this, name, sense)
            // CPLEX -> Cplex(this, name, sense)
            GUROBI -> Gurobi(this, name, sense)
            else -> findSolver(sense)
        }
    }

    private fun findSolver(sense: String): Solver {
        return mip.solvers.CBC(this, name, sense)
        TODO("Find an available solver")
    }

    @JvmOverloads
    fun addConstr(expr: LinExpr, name: String = "c_${constrs.size}"): Constr {
        solver.addConstr(expr, name)
        constrs.add(Constr(this, constrs.size))
        return constrs.last()
    }

    // region aliases for addConstr

    fun addLe(lhs: LinExpr, rhs: LinExpr) = addConstr(lhs le rhs)
    fun addGe(lhs: LinExpr, rhs: LinExpr) = addConstr(lhs ge rhs)
    fun addEq(lhs: LinExpr, rhs: LinExpr) = addConstr(lhs eq rhs)
    fun addLe(lhs: LinExpr, rhs: LinExpr, name: String) = addConstr(lhs le rhs, name)
    fun addGe(lhs: LinExpr, rhs: LinExpr, name: String) = addConstr(lhs ge rhs, name)
    fun addEq(lhs: LinExpr, rhs: LinExpr, name: String) = addConstr(lhs eq rhs, name)

    fun addConstr(pair: NamedLinExpr) = addConstr(pair.first, pair.second)

    fun addConstr(lhs: LinExpr, sense: Char, rhs: LinExpr, name: String = "c_${constrs.size}"):
        Constr {
        val expr = (lhs - rhs).apply { this.sense = sense }
        return addConstr(expr, name)
    }

    fun addConstr(lhs: LinExpr, sense: Char, rhs: Var, name: String = "c_${constrs.size}"): Constr {
        val expr = (lhs - rhs).apply { this.sense = sense }
        return addConstr(expr, name)
    }

    fun addConstr(lhs: LinExpr, sense: Char, rhs: Number, name: String = "c_${constrs.size}"): Constr {
        val expr = (lhs - rhs).apply { this.sense = sense }
        return addConstr(expr, name)
    }

    fun addConstr(lhs: Var, sense: Char, rhs: LinExpr, name: String = "c_${constrs.size}"): Constr {
        val expr = LinExpr(lhs).apply { sub(rhs); this.sense = sense }
        return addConstr(expr, name)
    }

    fun addConstr(lhs: Var, sense: Char, rhs: Var, name: String = "c_${constrs.size}"): Constr {
        val expr = LinExpr(lhs).apply { sub(rhs); this.sense = sense }
        return addConstr(expr, name)
    }

    fun addConstr(lhs: Var, sense: Char, rhs: Number, name: String = "c_${constrs.size}"): Constr {
        val expr = LinExpr(lhs).apply { sub(rhs); this.sense = sense }
        return addConstr(expr, name)
    }

    fun addConstr(lhs: Number, sense: Char, rhs: LinExpr, name: String = "c_${constrs.size}"): Constr {
        val expr = LinExpr(lhs).apply { sub(rhs); this.sense = sense }
        return addConstr(expr, name)
    }

    fun addConstr(lhs: Number, sense: Char, rhs: Var, name: String = "c_${constrs.size}"): Constr {
        val expr = LinExpr(lhs).apply { sub(rhs); this.sense = sense }
        return addConstr(expr, name)
    }

    // endregion aliases for addConstr

    @JvmOverloads
    fun addVar(name: String = "v_${vars.size}", varType: VarType = VarType.Continuous,
               obj: Number = 0.0, lb: Number = 0.0, ub: Number = INF,
               column: Column = Column.EMPTY
    ): Var {
        var lb = lb.toDouble()
        var ub = ub.toDouble()

        // ensuring binary variables have correct LB/UB
        if (varType == VarType.Binary) {
            lb = max(lb.toDouble(), 0.0)
            ub = min(ub.toDouble(), 1.0)
        }

        solver.addVar(name, obj.toDouble(), lb, ub, varType, column)
        vars.add(Var(this, vars.size))
        return vars.last()
    }

    // region addVar aliases

    fun addBinVar(name: String = "v_${vars.size}", obj: Number = 0.0,
                  column: Column = Column.EMPTY): Var =
        addVar(name = name, varType = VarType.Binary, obj = obj, lb = 0.0, ub = 1.0,
            column = column)

    fun addIntVar(name: String = "v_${vars.size}", obj: Number = 0.0, lb: Number = 0.0,
                  ub: Number = INF, column: Column = Column.EMPTY): Var =
        addVar(name = name, varType = VarType.Binary, obj = obj, lb = lb, ub = ub,
            column = column)

    fun addNumVar(name: String = "v_${vars.size}", obj: Number = 0.0, lb: Number = 0.0,
                  ub: Number = INF, column: Column = Column.EMPTY): Var =
        addVar(name = name, varType = VarType.Continuous, obj = obj, lb = lb, ub = ub,
            column = column)

    // endregion addVar aliases

    override inline fun get(param: String) = solver.get(param)

    fun optimize(): OptimizationStatus {
        solver.optimize()
        return OptimizationStatus.Optimal
    }

    operator fun plusAssign(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                addConstr(arg.first as LinExpr, arg.second as String)
            }
            is LinExpr -> {
                if (arg.isAffine)
                    objective = arg
                else
                    addConstr(arg)
            }
        }
    }

    fun remove(iterable: Iterable<Any?>) {
        val constrsToRemove = TreeSet<Constr>()
        val varsToRemove = TreeSet<Var>()

        for (term in iterable) {
            when (term) {
                null -> continue
                is Constr -> constrsToRemove.add(term)
                is Var -> varsToRemove.add(term)
                else -> throw IllegalArgumentException()
            }
        }

        // removing constraints
        if (constrsToRemove.isNotEmpty()) {
            solver.removeConstrs(constrsToRemove)
            constrs.removeAll(constrsToRemove)
            for (i in constrsToRemove.first().idx until constrs.size)
                constrs[i].idx = i
        }

        // removing variables
        if (varsToRemove.isNotEmpty()) {
            solver.removeVars(varsToRemove)
            vars.removeAll(varsToRemove)
            for (i in varsToRemove.first().idx until vars.size)
                vars[i].idx = i
        }
    }

    inline fun remove(constr: Constr) = remove(listOf(constr))

    inline fun remove(variable: Var) = remove(listOf(variable))

    override inline fun <T> set(param: String, value: T) = solver.set(param, value)

    /**
     * Checks the consistency of the optimization results, i.e., if the solution(s) produced by
     * the MIP solver respects all constraints and variable values are within acceptable bounds,
     * being integral whenever requested.
     */
    fun validateOptimizationResult(): Boolean {
        if (status in arrayOf(OptimizationStatus.Feasible, OptimizationStatus.Optimal)) {
            assert(numSolutions >= 1)

            if (sense == MINIMIZE)
                assert(objectiveBound <= objectiveValue + EPS)
            else
                assert(objectiveBound + EPS >= objectiveValue)

            // for (c in constrs) {
            //     if (c.violation >= infeasTol * 1.1) {
            //
            //     }
            // }

            TODO("Finish this method based on Python-MIP's")
        }
        return true

        // for c in constrs:
        // if c.expr.violation >= infeas_tol + infeas_tol * 0.1:
        // raise mip.InfeasibleSolution(
        //     "Constraint {}:\n{}\n is violated."
        // "Computed violation is {}."
        // "Tolerance for infeasibility is {}."
        // "Solution status is {}.".format(
        //     c.name,
        //     str(c),
        //     c.expr.violation,
        //     infeas_tol,
        //     status,
        // )
        // )
        // for v in vars:
        // if v.x <= v.lb - 1e-10 or v.x >= v.ub + 1e-10:
        // raise mip.InfeasibleSolution(
        //     "Invalid solution value for "
        // "variable {}={} variable bounds"
        // " are [{}, {}].".format(v.name, v.x, v.lb, v.ub)
        // )
        // if v.var_type in [mip.BINARY, mip.INTEGER]:
        // if (round(v.x) - v.x) >= integer_tol + integer_tol * 0.1:
        // raise mip.InfeasibleSolution(
        //     "Variable {}={} should be integral.".format(v.name, v.x)
        //     )
    }

    fun write(path: String) = solver.write(path)

    private class Param<T>(val default: T? = null) {

        operator fun getValue(model: Model, property: KProperty<*>): T {
            return model.solver.get(property.name) as T
        }

        operator fun setValue(model: Model, property: KProperty<*>, value: T) {
            model.solver.set(property.name, value)
        }
    }
}
