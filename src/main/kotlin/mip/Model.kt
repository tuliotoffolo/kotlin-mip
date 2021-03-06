package mip

import java.lang.Double.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.round

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
class Model : ModelProperties {

    /** Infinity representation */
    val INF: Double get() = solver.INF
    val INT_MAX: Int get() = solver.INT_MAX

    var name: String
    override var sense: String

    val constrs = ArrayList<Constr>()
    val vars = ArrayList<Var>()

    @get:JvmName("hasSolution")
    val hasSolution
        get() = solver.hasSolution

    override var solver: Solver

    @JvmOverloads
    constructor(name: String = "Model", sense: String = MINIMIZE, solverName: String = "") {
        this.name = name
        this.sense = sense

        // creating a solver instance
        solver = when (solverName.toUpperCase()) {
            CBC -> mip.solvers.Cbc(this, name, sense)
            CPLEX -> mip.solvers.Cplex(this, name, sense)
            EMPTY_SOLVER -> mip.solvers.EmptySolver(this, name, sense)
            GUROBI -> mip.solvers.Gurobi(this, name, sense)
            else -> findSolver(sense)
        }
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
    fun addVar(
        name: String? = null, varType: VarType = VarType.Continuous, obj: Number? = 0.0,
        lb: Number? = 0.0, ub: Number? = INF, column: Column? = Column.EMPTY,
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

    // region aliases for addVar

    @JvmOverloads
    fun addBinVar(
        name: String? = null, obj: Number? = 0.0,
        column: Column? = Column.EMPTY,
    ): Var =
        addVar(name = name, varType = VarType.Binary, obj = obj, lb = 0.0, ub = 1.0,
               column = column)

    @JvmOverloads
    fun addIntVar(
        name: String? = null, obj: Number? = 0.0, lb: Number? = 0.0,
        ub: Number? = INF, column: Column? = Column.EMPTY,
    ): Var =
        addVar(name = name, varType = VarType.Integer, obj = obj, lb = lb, ub = ub,
               column = column)

    @JvmOverloads
    fun addNumVar(
        name: String? = null, obj: Number? = 0.0, lb: Number? = 0.0,
        ub: Number? = INF, column: Column? = Column.EMPTY,
    ): Var =
        addVar(name = name, varType = VarType.Continuous, obj = obj, lb = lb, ub = ub,
               column = column)

    // endregion addVar aliases

    operator fun minusAssign(arg: Any?) {
        when (arg) {
            is Constr? -> remove(arg)
            is Iterable<Any?>? -> remove(arg)
            is Var? -> remove(arg)
        }
    }

    @JvmOverloads
    fun optimize(
        relax: Boolean = false,
        maxSeconds: Double? = null,
        maxNodes: Int? = null,
        maxSolutions: Int? = null,
    ): OptimizationStatus {
        solver.setProcessingLimits(maxSeconds, maxNodes, maxSolutions)
        return solver.optimize(relax)
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
        if (iterable == null) return

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

    /**
     * Checks the consistency of the optimization results, i.e., if the solution(s) produced by
     * the MIP solver respects all constraints and variable values are within acceptable bounds,
     * being integral whenever requested.
     */
    fun validateOptimizationResult(): Boolean {
        if (status in arrayOf(OptimizationStatus.Feasible, OptimizationStatus.Optimal))
            assert(numSolutions >= 1)

        return true

        // TODO: fix this code

        if (numSolutions >= 1 || status in arrayOf(OptimizationStatus.Feasible, OptimizationStatus.Optimal)) {

            if (sense == MINIMIZE)
                assert(objectiveBound <= objectiveValue + 1e-10)
            else
                assert(objectiveBound + 1e-10 >= objectiveValue)

            for (c in constrs) {
                if (c.expr.violation >= infeasTol + infeasTol * 0.1) { // TODO: check this (here and in Python-MIP)
                    throw Error("Constraint ${c.name} is violated:\n" +
                                "    ${c.expr}\n" +
                                "    Computed violation is ${c.expr.violation}\n" +
                                "    Tolerance for infeasibility is $infeasTol\n" +
                                "    Solution status is $status")
                }
            }

            for (v in vars) {
                val x = v.x
                if (x <= v.lb - 1e-10 || x >= v.ub + 1e-10) {
                    throw Error("Variable ${v.name}=${x} is out of its bounds.\n" +
                                "    {$v.lb} <= ${x} <= ${v.ub}")
                }

                if (v.type == VarType.Integer || v.type == VarType.Binary) {
                    if (round(x) - x >= integerTol + integerTol * 0.1)
                        throw Error("Variable ${v.name}=${x} should be integral")
                }
            }
        }

        return true
    }

    fun write(path: String) = solver.write(path)


    private fun findSolver(sense: String): Solver {
        val solverName = System.getProperty("SOLVER_NAME") ?: System.getenv("SOLVER_NAME")
        if (solverName != null) {
            when (System.getProperty("SOLVER_NAME")) {
                CBC -> return mip.solvers.Cbc(this, name, sense)
                CPLEX -> return mip.solvers.Cplex(this, name, sense)
                EMPTY_SOLVER -> return mip.solvers.EmptySolver(this, name, sense)
                GUROBI -> return mip.solvers.Gurobi(this, name, sense)
            }
        }

        // trying Gurobi
        val gurobiHome = System.getProperty("GUROBI_HOME") ?: System.getenv("GUROBI_HOME")
        if (gurobiHome != null)
            return mip.solvers.Gurobi(this, name, sense)

        // trying Cplex
        val cplexHome = System.getProperty("CPLEX_HOME") ?: System.getenv("CPLEX_HOME")
        if (cplexHome != null)
            return mip.solvers.Cplex(this, name, sense)

        return mip.solvers.Cbc(this, name, sense)
    }
}
