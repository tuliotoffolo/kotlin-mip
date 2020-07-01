package mip

import java.lang.Double.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty

/**
 * Model class
 *
 * Creates a Mixed-Integer Linear Programming Model. The default model optimization direction is
 * Minimization. To store and optimize the model the MIP package automatically searches and
 * connects in runtime to the dynamic library of some MIP solver installed on your computer.
 * Nowadays Gurobi and CBC are supported. The solver is automatically selected, but you can
 * force the selection of a specific solver with the parameter [solverName].
 *
 * @param name: model name
 * @param sense: minimization ("MIN") or maximization ("MAX")
 * @param solverName: solver name ("CBC" or "GUROBI")
 *
 * @author Túlio Toffolo
 */
class Model(var name: String = "Model", sense: String = MINIMIZE,
            override var solverName: String = "") : Parameters() {

    // region main components

    val constrs = ArrayList<Constr>()
    val vars = ArrayList<Var>()

    val solver: Solver

    // endregion main components

    init {
        // creating a solver instance
        solver = when (solverName.toUpperCase()) {
            CBC -> mip.solvers.CBC(this, name, sense)
            CPLEX -> mip.solvers.Cplex(this, name, sense)
            GUROBI -> mip.solvers.Gurobi(this, name, sense)
            else -> findSolver(sense)
        }

        // updating solver name
        solverName = solver.solverName
    }

    private fun findSolver(sense: String): Solver {
        return mip.solvers.CBC(this, name, sense)
        // TODO("Find an available solver")
    }

    @JvmOverloads
    fun addConstr(expr: LinExpr, name: String? = null): Constr {
        solver.addConstr(expr, name ?: "c_${constrs.size}")
        constrs.add(Constr(this, constrs.size))
        return constrs.last()
    }

    // region aliases for addConstr: addLeq, addGeq, addEq

    inline fun addConstr(pair: NamedLinExpr) = addConstr(pair.first, pair.second)

    @JvmOverloads
    inline fun addLeq(lhs: Iterable<Any?>?, rhs: Iterable<Any?>?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addLeq(lhs: Iterable<Any?>?, rhs: LinExpr?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addLeq(lhs: Iterable<Any?>?, rhs: Var?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addLeq(lhs: Iterable<Any?>?, rhs: Number?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addLeq(lhs: LinExpr?, rhs: Iterable<Any?>?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addLeq(lhs: LinExpr?, rhs: LinExpr?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addLeq(lhs: LinExpr?, rhs: Var?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addLeq(lhs: LinExpr?, rhs: Number?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addLeq(lhs: Var?, rhs: Iterable<Any?>?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addLeq(lhs: Var?, rhs: LinExpr?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addLeq(lhs: Var?, rhs: Var?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addLeq(lhs: Var?, rhs: Number?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addLeq(lhs: Number?, rhs: Iterable<Any?>?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addLeq(lhs: Number?, rhs: LinExpr?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addLeq(lhs: Number?, rhs: Var?, name: String? = null) = addConstr(leq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: Iterable<Any?>?, rhs: Iterable<Any?>?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: Iterable<Any?>?, rhs: LinExpr?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: Iterable<Any?>?, rhs: Var?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: Iterable<Any?>?, rhs: Number?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: LinExpr?, rhs: Iterable<Any?>?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: LinExpr?, rhs: LinExpr?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: LinExpr?, rhs: Var?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: LinExpr?, rhs: Number?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: Var?, rhs: Iterable<Any?>?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: Var?, rhs: LinExpr?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: Var?, rhs: Var?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: Var?, rhs: Number?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: Number?, rhs: Iterable<Any?>?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: Number?, rhs: LinExpr?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addGeq(lhs: Number?, rhs: Var?, name: String? = null) = addConstr(geq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: Iterable<Any?>?, rhs: Iterable<Any?>?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: Iterable<Any?>?, rhs: LinExpr?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: Iterable<Any?>?, rhs: Var?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: Iterable<Any?>?, rhs: Number?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: LinExpr?, rhs: Iterable<Any?>?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: LinExpr?, rhs: LinExpr?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: LinExpr?, rhs: Var?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: LinExpr?, rhs: Number?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: Var?, rhs: Iterable<Any?>?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: Var?, rhs: LinExpr?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: Var?, rhs: Var?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: Var?, rhs: Number?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: Number?, rhs: Iterable<Any?>?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: Number?, rhs: LinExpr?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    @JvmOverloads
    inline fun addEq(lhs: Number?, rhs: Var?, name: String? = null) = addConstr(eq(lhs, rhs), name)

    // endregion aliases for addConstr

    @JvmOverloads
    fun addVar(name: String? = null, varType: VarType = VarType.Continuous,
               obj: Number? = 0.0, lb: Number? = 0.0, ub: Number? = INF,
               column: Column? = Column.EMPTY
    ): Var {
        var lbComputed = lb?.toDouble() ?: 0.0
        var ubComputed = ub?.toDouble() ?: INF

        // ensuring binary variables have correct LB/UB
        if (varType == VarType.Binary) {
            lbComputed = max(lbComputed, 0.0)
            ubComputed = min(ubComputed, 1.0)
        }

        solver.addVar(name ?: "v_${vars.size}", obj?.toDouble() ?: 0.0, lbComputed, ubComputed,
            varType, column ?: Column.EMPTY)
        vars.add(Var(this, vars.size))
        return vars.last()
    }

    // region addVar aliases

    @JvmOverloads
    fun addBinVar(name: String? = null, obj: Number? = 0.0,
                  column: Column? = Column.EMPTY): Var =
        addVar(name = name, varType = VarType.Binary, obj = obj, lb = 0.0, ub = 1.0,
            column = column)

    @JvmOverloads
    fun addIntVar(name: String? = null, obj: Number? = 0.0, lb: Number? = 0.0,
                  ub: Number? = INF, column: Column? = Column.EMPTY): Var =
        addVar(name = name, varType = VarType.Integer, obj = obj, lb = lb, ub = ub,
            column = column)

    @JvmOverloads
    fun addNumVar(name: String? = null, obj: Number? = 0.0, lb: Number? = 0.0,
                  ub: Number? = INF, column: Column? = Column.EMPTY): Var =
        addVar(name = name, varType = VarType.Continuous, obj = obj, lb = lb, ub = ub,
            column = column)

    // endregion addVar aliases

    override fun get(property: KProperty<*>) = property.getter.call(solver)!!

    fun optimize(): OptimizationStatus {
        solver.optimize()
        return status
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

    fun remove(iterable: Iterable<Any?>?) {
        if (iterable == null) return;

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

    inline fun remove(constr: Constr?) = remove(listOf(constr))

    inline fun remove(variable: Var?) = remove(listOf(variable))

    override fun <T> set(property: KMutableProperty<*>, value: T) = property.setter.call(solver)

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
}
