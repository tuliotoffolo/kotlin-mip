@file:JvmName("Constants")

package br.ufop.jmip

/** Epsilon number (practical zero) */
const val EPS = 10e-64

/** Infinity representation */
const val INF = 1.0 / 0.0

// constraint senses
const val EQUAL = "="
const val LESS_OR_EQUAL = "<"
const val GREATER_OR_EQUAL = ">"

// optimization directions
const val MIN = "MIN"
const val MAX = "MAX"
const val MINIMIZE = "MIN"
const val MAXIMIZE = "MAX"

// solvers
const val CBC = "CBC"
const val CPX = "CPX"    // we plan to support CPLEX in the future
const val CPLEX = "CPX"  // we plan to support CPLEX in the future
const val GRB = "GRB"
const val GUROBI = "GRB"
const val SCIP = "SCIP"  // we plan to support SCIP in the future

// variable types
const val BINARY = 'B'
const val CONTINUOUS = 'C'
const val INTEGER = 'I'


/**
 * Different type of cuts (for enabling/disabling)
 */
enum class CutType(val value: Int) {
    /**  Types of cuts that can be generated */

    /** Gomory Mixed Integer cuts [Gomo69] . */
    GOMORY (0),

    /** Mixed-Integer Rounding cuts [Marc01]. */
    MIR (1),

    /** Zero/Half cuts [Capr96]. */
    ZERO_HALF (2),

    /** Clique cuts [Padb73]. */
    CLIQUE (3),

    /** Knapsack cover cuts [Bala75]. */
    KNAPSACK_COVER (4),

    /** Lift-and-project cuts [BCC93]. */
    LIFT_AND_PROJECT (5),
}

/**
 * Different methods to solve the linear programming problem.
 */
enum class LPMethod(val value: Int) {
    /*** Let the solver decide which is the best method */
    AUTO(0),

    /** The dual simplex algorithm */
    DUAL(1),

    /** The primal simplex algorithm */
    PRIMAL(2),

    /** The barrier algorithm */
    BARRIER(3),
}

/**
 * Possible optimization status.
 */
enum class OptimizationStatus(val value: Int) {
    /**  Solver returned an error */
    ERROR(-1),

    /** Optimal solution was computed */
    OPTIMAL(0),

    /** The model is proven infeasible */
    INFEASIBLE(1),

    /** One or more variables that appear in the objective function are not  included in binding
     * constraints and the optimal objective value is infinity. */
    UNBOUNDED(2),

    /** An integer feasible solution was found during the search but the search was interrupted
     * before concluding if this is the optimal solution or
     * not. */
    FEASIBLE(3),

    /** A feasible solution exist for the relaxed linear program but not for the problem with
     * existing integer variables */
    INT_INFEASIBLE(4),

    /** A truncated search was executed and no integer feasible solution was found */
    NO_SOLUTION_FOUND(5),

    /** The problem was loaded but no optimization was performed */
    LOADED(6),

    /** No feasible solution exists for the current cutoff */
    CUTOFF(7),

    OTHER(10000),
}

/**
 * Possible search emphasis options
 */
enum class SearchEmphasis(val value: Int) {
    /** Default search emphasis, try to balance between improving the dual bound and producing
     * integer feasible solutions. */
    DEFAULT (0),

    /** More aggressive search for feasible solutions. */
    FEASIBILITY (1),

    /** Focuses more on producing improved dual bounds even if the production of integer feasible
     *  solutions is delayed. */
    OPTIMALITY (2),
}

/**
 * Possible variable types.
 */
enum class VarType(val value: Char) {
    /** Binary variable, i.e. integer variable such that 0 <= value <= 1. */
    BINARY('B'),

    /** Continuous variable. */
    CONTINUOUS('C'),

    /** Integer variable. */
    INTEGER('I'),
}
