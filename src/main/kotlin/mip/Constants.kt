@file:JvmName("MIP")
@file:JvmMultifileClass

/**
 * Constants used by the package.
 *
 * @author Túlio Toffolo
 */

package mip

// region constants

/** Epsilon number (practical zero) */
const val EPS = 10e-10

/** Infinity representation */
val INF = Double.MAX_VALUE

// constraint senses
const val EQ = "=="
const val LEQ = "<="
const val GEQ = ">="

// optimization directions
const val MIN = "MIN"
const val MAX = "MAX"
const val MINIMIZE = MIN
const val MAXIMIZE = MAX

// solvers
const val CBC = "CBC"
const val CPX = "CPX"    // we plan to support CPLEX in the future
const val CPLEX = CPX  // we plan to support CPLEX in the future
const val EMPTY_SOLVER = "EMPTY_SOLVER"
const val GRB = "GRB"
const val GUROBI = GRB
const val SCIP = "SCIP"  // we plan to support SCIP in the future

// variable types
const val BINARY = 'B'
const val CONTINUOUS = 'C'
const val INTEGER = 'I'

// endregion constants

// region enums


/**
 * Different type of cuts (for enabling/disabling)
 */
enum class CutType(val value: Int) {
    /** Types of cuts that can be generated. */

    /** Gomory Mixed Integer cuts [Gomo69]. */
    Gomory(0),

    /** Mixed-Integer Rounding cuts [Marc01]. */
    MIR(1),

    /** Zero/Half cuts [Capr96]. */
    ZeroHalf(2),

    /** Clique cuts [Padb73]. */
    Clique(3),

    /** Knapsack cover cuts [Bala75]. */
    KnapsackCover(4),

    /** Lift-and-project cuts [BCC93]. */
    LiftAndProject(5),
}

/**
 * Different methods to solve the linear programming problem.
 */
enum class LPMethod(val value: Int) {
    /*** Let the solver decide which is the best method. */
    Auto(0),

    /** The dual simplex algorithm. */
    Dual(1),

    /** The primal simplex algorithm. */
    Primal(2),

    /** The barrier algorithm. */
    Barrier(3),
}

/**
 * Parameters to set
 */
enum class Parameter(val value: String) {
    Cutoff("CUTOFF"),
}

/**
 * Possible optimization status.
 */
enum class OptimizationStatus(val value: Int) {
    /**  Solver returned an error */
    Error(-1),

    /** Optimal solution was computed */
    Optimal(0),

    /** The model is proven infeasible */
    Infeasible(1),

    /**
     * One or more variables that appear in the objective function are not  included in binding
     * constraints and the optimal objective value is infinity.
     */
    Unbounded(2),

    /**
     * An integer feasible solution was found during the search but the search was interrupted
     * before concluding if this is the optimal solution or not.
     */
    Feasible(3),

    /**
     * A feasible solution exist for the relaxed linear program but not for the problem with
     * existing integer variables
     */
    IntInfeasible(4),

    /** A truncated search was executed and no integer feasible solution was found */
    NoSolutionFound(5),

    /** The problem was loaded but no optimization was performed */
    Loaded(6),

    /** No feasible solution exists for the current cutoff */
    Cutoff(7),

    Other(10000),
}

/**
 * Possible search emphasis options
 */
enum class SearchEmphasis(val value: Int) {
    /**
     * Default search emphasis, try to balance between improving the dual bound and producing
     * integer feasible solutions.
     */
    Default(0),

    /** More aggressive search for feasible solutions. */
    Feasibility(1),

    /**
     * Focuses more on producing improved dual bounds even if the production of integer feasible
     * solutions is delayed.
     */
    Optimality(2),
}

/**
 * Possible variable types.
 */
enum class VarType(val value: Char) {
    /** Binary variable, i.e. integer variable such that 0 <= value <= 1. */
    Binary('B'),

    /** Continuous variable. */
    Continuous('C'),

    /** Integer variable. */
    Integer('I'),
}


/**
 * Possible boolean parameters.
 */
enum class BooleanParam {
    NumericalEmphasis
}

/**
 * Possible double parameters.
 */
enum class DoubleParam {
    Cutoff,
    MIPGap,
    MIPGapAbs,
    ObjDif,
    RelObjDif,
    TimeLimit;
}

/**
 * Possible integer parameters.
 */
enum class IntParam {
    LogToConsole,
    PopulateLim,
    RootAlg,
    Threads
}

/**
 * Possible long parameters.
 */
enum class LongParam {
    IntSolLim,
    IterLimit
}

/**
 * Possible String parameters.
 */
enum class StringParam {
    LogFile
}

// endregion enums
