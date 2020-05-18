package br.ufop.jmip

import br.ufop.jmip.solvers.*

/**
 * Model class
 *
 * Creates a Mixed-Integer Linear Programming Model. The default model
 * optimization direction is Minimization. To store and optimize the model
 * the MIP package automatically searches and connects in runtime to the
 * dynamic library of some MIP solver installed on your computer, nowadays
 * gurobi and cbc are supported. This solver is automatically selected,
 * but you can force the selection of a specific solver with the parameter
 * solver_name.
 *
 * @property name (str): model name
 * @property sense (str): minimization ("MIN") or maximization ("MAX")
 */
class Model(var name: String = "JMipModel", var sense: String = MINIMIZE,
            var solverName: String = CBC) {

    // region main components

    val hasSolution: Boolean get() = solver.hasSolution
    val solver: Solver
    val settings = ModelSettings(this)

    val constrs = ArrayList<Constr>()
    val vars = ArrayList<Var>()

    val objectiveValue: Double get() = solver.objectiveValue
    var status = OptimizationStatus.LOADED

    // endregion main components

    // region settable model properties

    var objective: LinExpr
        get() = solver.getObjective()
        set(value) = solver.setObjective(value)

    // endregion

    // region additional delegated properties

    var cuts by settings
    var cutPasses by settings
    var clique by settings
    var preprocess by settings
    var cutsGenerator by settings
    var lazyConstrsGenerator by settings
    var start by settings
    var threads by settings
    var lpMethod by settings
    var nCols by settings
    var nRows by settings
    var gap by settings
    var storeSearchProgressLog by settings
    var plog by settings
    var integerTol by settings
    var infeasTol by settings
    var optTol by settings
    var maxMipGap by settings
    var maxMipGapAbs by settings
    var seed by settings
    var roundIntVars by settings
    var solPoolSize by settings

    // endregion additional delegated properties

    init {
        // creating a solver instance
        solver = when (solverName.toUpperCase()) {
            CBC -> CBC(this, name, sense)
            // CPLEX -> Cplex(this, name, sense)
            GUROBI -> Gurobi(this, name, sense)
            else -> findSolver()
        }
    }

    private fun findSolver(): Solver {
        TODO("Not yet implemented")
    }

    @JvmOverloads
    fun addVar(name: String = "v_${vars.size}", varType: VarType = VarType.CONTINUOUS,
               obj: Number = 0.0, lb: Number = 0.0, ub: Number = INF,
               column: Column = Column.EMPTY): Var {
        solver.addVar(name, obj.toDouble(), lb.toDouble(), ub.toDouble(), varType, column)
        vars.add(Var(this, vars.size))
        return vars.last()
    }

    @JvmOverloads
    fun addConstr(expr: LinExpr, name: String = "c_${constrs.size}"): Constr {
        solver.addConstr(expr, name)
        constrs.add(Constr(this, constrs.size))
        return constrs.last()
    }

    fun addConstr(pair: NamedLinExpr) = addConstr(pair.first, pair.second)

    fun set(arg: String, value: Any?) {}

    fun write(path: String) = solver.write(path)

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

    // region aliases for addConstr

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

    fun optimize() {
        solver.optimize()
    }

    // endregion aliases for addConstr
}

