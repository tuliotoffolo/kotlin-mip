package mip

import kotlin.reflect.KProperty

/**
 * Abstract class used to map all parameters inherited by both Model and Solver classes.
 *
 * @author Túlio A. M. Toffolo
 */
abstract class Properties {

    /**
     * Name of the solver ("CBC" or "Gurobi", for example).
     */
    abstract val solverName: String


    // region vals

    /**
     * Gap between solution cost and lower (or upper) bound.
     */
    open val gap by Param<Double>()

    /**
     * Returns if the model/solver has a feasible solution
     */
    open val hasSolution by Param<Boolean>()

    /**
     * Number of columns (variables) in the model.
     */
    open val numCols by Param<Int>()

    /**
     * Number of integer variables in the model
     */
    open val numInt by Param<Int>()

    /**
     * Number of rows (constraints) in the model.
     */
    open val numRows by Param<Int>()

    /**
     * Number of non-zero values in the constraint matrix.
     */
    open val numNZ by Param<Int>()

    /**
     * Number of solutions found during the MIP search.
     */
    open val numSolutions by Param<Int>()

    /**
     * A valid estimate computed for the optimal solution cost, lower bound in the case of
     * minimization, or upper bound in the case of a maximization problem. This value is equal to
     * [objectiveValue] if an optimal solution is found.
     */
    open val objectiveBound by Param<Double>()

    /**
     * Objective function value of the solution found.
     */
    open val objectiveValue by Param<Double>()

    /**
     * Optimization status, which can be OPTIMAL(0), ERROR(-1), INFEASIBLE(1), UNBOUNDED(2). When
     * optimizing problems with integer variables some additional cases may happen, FEASIBLE(3)
     * for the case when a feasible solution was found but optimality was not proved,
     * INT_INFEASIBLE(4) for the case when the lp relaxation is feasible but no feasible integer
     * solution exists and NO_SOLUTION_FOUND(5) for the case when an integer solution was not
     * found in the optimization.
     */
    open val status by Param<OptimizationStatus>()

    // endregion vals

    // region vars

    /**
     * Controls the generation of clique cuts. -1 means automatic, 0 disables it, 1 enables it and
     * 2 enables more aggressive clique generation.
     */
    open var clique by Param<Int>()

    /**
     * Lower (or upper) limit for the solution cost. Note that solutions with cost > cutoff in
     * minimization problems (or with cost < cutoff in maximization problems) will be pruned away
     * from the search space. A tight cutoff value may significantly speedup the search, but this
     * parameter may render the model infeasible if a value too low (or too high) is set.
     */
    open var cutoff by Param<Double>()

    /**
     * Maximum number of rounds of cutting planes. You may set this parameter to low values if you
     * see that a significant amount of time is being spent generating cuts without any improvement
     * in the lower bound. -1 means automatic, values greater than zero specify the maximum number
     * of rounds.
     */
    open var cutPasses by Param<Int>()

    /**
     * Controls the generation of cutting planes, -1 means automatic, 0 disables completely, 1
     * (default) generates cutting planes in a moderate way, 2 generates cutting planes
     * aggressively and 3 generates even more cutting planes. Cutting planes usually improve the
     * LP relaxation bound but also make the solution time of the LP relaxation larger, so the
     * overall effect is hard to predict and experimenting different values for this parameter may
     * be beneficial.
     */
    open var cuts by Param<Int>()

    /**
     * A cuts generator is a [mip.ConstrsGenerator] object that receives a fractional solution and
     * tries to generate one or more constraints (cuts) to remove it. The cuts generator is called
     * in  every node of the branch-and-cut tree where a solution that violates the integrality
     * constraint of one or more variables is found.
     */
    open var cutsGenerator by Param<Int>()

    /**
     * The main objective of the search. Three options are currently supported:
     *
     * - if [SearchEmphasis.Feasibility], then the search process will focus on try to find
     * quickly feasible solutions and improving them;
     * - if [SearchEmphasis.Optimality], then the search process will try to find a provable
     * optimal solution, procedures to further improve the lower bounds will be activated in this
     * setting, this may increase the time to produce the first feasible solutions but will probably
     * pay off in longer runs;
     * - if [SearchEmphasis.Default], the default option, the search will sough at a balance
     * between optimality and feasibility.
     */
    open var emphasis by Param<SearchEmphasis>()

    /**
     * Maximum allowed violation for constraints. Default value: 1e-6. Tightening this value can
     * increase the numerical precision but also probably increase the running time. As floating
     * point computations always involve some loss of precision, values too close to zero will
     * likely render some models impossible to optimize.
     */
    open var infeasTol by Param<Double>()

    /**
     * Maximum distance to the nearest integer for a variable to be considered with an integer
     * value. Default value: 1e-6. Tightening this value can increase the numerical precision but
     * also probably increase the running time. As floating point computations always involve some
     * loss of precision, values too close to zero will likely render some models impossible to
     * solve.
     */
    open var integerTol by Param<Double>()

    /**
     * A lazy constraints generator is an [mip.ConstrsGenerator] object that receives an integer
     * solution and checks its feasibility. If the solution is not feasible then one or more
     * constraints can be generated to remove it.
     *
     * When a lazy constraints generator is informed it is assumed that the initial formulation is
     * incomplete. Thus, a restricted pre-processing routine may be applied. If the initial
     * formulation is incomplete, it may be interesting to use the same [mip.ConstrsGenerator] to
     * generate cuts *and* lazy constraints. The use of **only** lazy constraints may be useful
     * then integer solutions rarely violate these constraints.
     */
    open var lazyConstrsGenerator by Param<Int>()

    /**
     * Defines which method should be used to solve the linear programming problem. If the problem
     * has integer variables, this will affect only the solution of the linear programming
     * relaxation.
     */
    open var lpMethod by Param<LPMethod>()

    /**
     * Tolerance for the quality of the optimal solution, if a solution with cost :math:`c` and a
     * lower bound :math:`l` are available and :math:`c-l<` :code:`mip_gap_abs`, the search will
     * be concluded, see :attr:`~mip.Model.max_mip_gap` to determine a percentage value.
     */
    open var maxMipGap by Param<Double>()

    /**
     * Value indicating the tolerance for the maximum percentage deviation from the optimal
     * solution cost, if a solution with cost :math:`c` and a lower bound :math:`l` are available
     * and :math:`(c-l)/l <` :code:`max_mip_gap` the search will be concluded.
     */
    open var maxMipGapAbs by Param<Double>()

    /**
     * Limit on the number of nodes explored by the search. Note that the solver will return if
     * the limit is reached.
     */
    open var maxNodes by Param<Int>()

    /**
     * Runtime limit in seconds for the search.
     */
    open var maxSeconds by Param<Double>()

    /**
     * Limit on the number of solutions generated by the solver. Note that the solver will return
     * if the limit is reached.
     */
    open var maxSolutions by Param<Int>()

    /**
     * The objective function of the problem as a linear expression.
     *
     * Examples:
     *
     * The following code adds all `x` variables `x[0], ..., x[n-1]`, to the objective function of
     * model `m` with the same cost `w`:
     *
     *     m.objective = minimize(w * x)
     *
     * A simpler way to define the objective function is the use of the model operator `+=`
     *
     *     m += minimize(w * x)
     *
     * Note that the only difference of adding a constraint is the lack of *sense* and *rhs*.
     */
    open var objective by Param<LinExpr>()

    /**
     * The constant part of the objective function.
     */
    open var objectiveConst by Param<Double>()

    /**
     * Maximum reduced cost value for a solution of the LP relaxation to be considered optimal.
     * Default value: 1e-6.
     *
     * Tightening this value can increase the numerical precision but also probably increase the
     * running time. As floating point computations always involve some loss of precision, values
     * too close to zero will likely render some models impossible to optimize.
     */
    open var optTol by Param<Double>()

    /**
     * Status of the preprocessing.
     *
     *     - -1 means the solver decides whether to apply it or not, i.e. automatically.
     *     - 0 means disabled.
     *     - 1 means enabled.
     */
    open var preprocess by Param<Int>()

    /**
     * MIP solvers perform computations using *limited precision* arithmetic, meaning a variable
     * with value 0 may appear in the solution with value 0.000000000001. Thus, comparing this var
     * to zero would return `false`. The safest approach would be to use something like
     * `abs(v.x) < 1e-7`. However, to simplify your code, the solution value of integer variables
     * can be automatically rounded to the nearest integer, validating comparisons like `v.x == 0`.
     * Note, however, that rounding is not always a good idea, particularly in models with
     * numerical instability, since it can lead to infeasibility.
     */
    open var roundIntVars by Param<Boolean>()

    /**
     * Random seed used by the solver. Small changes in the first decisions while solving the LP
     * relaxation and the MIP can have a large impact in the performance. This behaviour can be
     * exploited, for example, by multiple independent runs with different random seeds.
     */
    open var seed by Param<Int>()

    /**
     * The optimization sense: either MINIMIZE or MAXIMIZE.
     */
    open var sense by Param<String>()

    /**
     * Size of the solution pool, i.e. maximum number of solutions that will be stored during
     * the search. To check how many solutions were found during the search, use [numSolutions].
     */
    open var solPoolSize by Param<Int>()

    /**
     * Initial feasible solution. This property is used to provide an initial feasible solution to
     * the solver. Note that only the main binary/integer decision variables which appear with
     * non-zero values in the initial feasible solution need to be informed. Auxiliary or
     * continuous variables will have their values automatically computed.
     */
    open var start by Param<Int>()

    /**
     * Log of bound improvements in the search. The output of MIP solvers is a sequence of
     * improving incumbent solutions (primal bound) and estimates for the optimal cost (dual
     * bound). When the costs of these two bounds match the search is concluded. In truncated
     * searches, the most common situation for hard problems, at the end of the search there is a
     * [gap] between these bounds. This property stores the detailed events of improving these
     * bounds during the search process. Analyzing the evolution of these bounds you can see if
     * you need to improve your solver w.r.t. the production of feasible solutions, by including
     * an heuristic to produce a better initial feasible solution, for example, or improve the
     * formulation with cutting planes, for example, to produce better dual bounds. To enable
     * storing the [searchProgressLog] set [storeSearchProgressLog] to `true`.
     */
    open val searchProgressLog by Param<String>()

    /**
     * Defines whether [searchProgressLog] will be stored or not when optimizing. Activate it if
     * you want to analyze bound improvements over time.
     */
    open var storeSearchProgressLog by Param<Boolean>()

    /**
     * Number of threads to be used when solving the problem. 0 uses solver default configuration,
     * -1 uses the number of available processing cores and values greater than zero specify the
     * number of threads. Note that while an increased number of threads may improve the solution
     * time, it may also increase memory consumption.
     */
    open var threads by Param<Int>()

    /**
     * The level of verbosity:
     *
     *     - 0: disable solver output messages (note that some solvers prohibit the omition of
     *     specific messages related to licenses).
     *     - 1: enable solver output messages.
     */
    open var verbose by Param<Boolean>()

    // endregion vars


    /**
     * Getter used to directly query solver parameters and/or arguments.
     */
    open fun get(property: String): Any {
        throw NotImplementedError("Parameter or attribute '$property' unavailable in solver $solverName")
    }

    /**
     * Getter used to directly query solver *Double* parameters and/or arguments.
     */
    open fun getDouble(property: String): Double {
        throw NotImplementedError("Parameter or attribute '$property' unavailable in solver $solverName")
    }

    /**
     * Getter used to directly query solver *Int* parameters and/or arguments.
     */
    open fun getInt(property: String): Int {
        throw NotImplementedError("Parameter or attribute '$property' unavailable in solver $solverName")
    }

    /**
     * Getter used to directly query solver *String* parameters and/or arguments.
     */
    open fun getString(property: String): String {
        throw NotImplementedError("Parameter or attribute '$property' unavailable in solver $solverName")
    }

    /**
     * Setter used to directly set solver parameters and/or arguments.
     */
    open fun <T> set(property: String, value: T) {
        throw NotImplementedError("Parameter or attribute '$property' unavailable in solver $solverName")
    }


    /**
     * Getter used by delegated properties.
     */
    internal open fun propertyGet(property: String): Any {
        throw NotImplementedError("Parameter or attribute '$property' unavailable in solver $solverName")
    }

    /**
     * Getter used by delegated properties.
     */
    internal open fun <T> propertySet(property: String, value: T) {
        throw NotImplementedError("Parameter or attribute '$property' unavailable in solver $solverName")
    }


    /**
     * Delegate class which is used as the default manager for properties of class [Properties].
     */
    @Suppress("NOTHING_TO_INLINE")
    private class Param<T> {

        /**
         * Delegate getter.
         */
        inline operator fun getValue(param: Properties, property: KProperty<*>): T {
            return param.propertyGet(property.name) as T
        }

        /**
         * Delegate setter.
         */
        inline operator fun setValue(param: Properties, property: KProperty<*>, value: T) {
            param.propertySet(property.name, value)
        }
    }
}