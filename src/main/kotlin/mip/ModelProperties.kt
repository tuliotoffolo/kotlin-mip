package mip

/**
 * Simple class that redirects all parameter getters/setters to the solver. This class is
 * implemented as a reflection replacement.
 */
abstract class ModelProperties : Properties() {

    internal abstract var solver: Solver


    // region vals

    override val gap: Double get() = solver.gap
    override val numCols: Int get() = solver.numCols
    override val numInt: Int get() = solver.numInt
    override val numRows: Int get() = solver.numRows
    override val numNZ: Int get() = solver.numNZ
    override val numSolutions: Int get() = solver.numSolutions
    override val objectiveBound: Double get() = solver.objectiveBound
    override val objectiveValue: Double get() = solver.objectiveValue
    override val status: OptimizationStatus get() = solver.status
    override val solverName: String get() = solver.solverName

    // endregion vals


    // region vars

    override var clique: Int
        get() = solver.clique
        set(value) {
            solver.clique = value
        }

    override var cutoff: Double
        get() = solver.cutoff
        set(value) {
            solver.cutoff = value
        }

    override var cutPasses: Int
        get() = solver.cutPasses
        set(value) {
            solver.cutPasses = value
        }

    override var cuts: Int
        get() = solver.cuts
        set(value) {
            solver.cuts = value
        }

    override var cutsGenerator: Int
        get() = solver.cutsGenerator
        set(value) {
            solver.cutsGenerator = value
        }

    override var emphasis: SearchEmphasis
        get() = solver.emphasis
        set(value) {
            solver.emphasis = value
        }

    override var infeasTol: Double
        get() = solver.infeasTol
        set(value) {
            solver.infeasTol = value
        }

    override var integerTol: Double
        get() = solver.integerTol
        set(value) {
            solver.integerTol = value
        }

    override var lazyConstrsGenerator: Int
        get() = solver.lazyConstrsGenerator
        set(value) {
            solver.lazyConstrsGenerator = value
        }

    override var lpMethod: LPMethod
        get() = solver.lpMethod
        set(value) {
            solver.lpMethod = value
        }

    override var maxMipGap: Double
        get() = solver.maxMipGap
        set(value) {
            solver.maxMipGap = value
        }

    override var maxMipGapAbs: Double
        get() = solver.maxMipGapAbs
        set(value) {
            solver.maxMipGapAbs = value
        }

    override var maxNodes: Int
        get() = solver.maxNodes
        set(value) {
            solver.maxNodes = value
        }

    override var maxSeconds: Double
        get() = solver.maxSeconds
        set(value) {
            solver.maxSeconds = value
        }

    override var maxSolutions: Int
        get() = solver.maxSolutions
        set(value) {
            solver.maxSolutions = value
        }

    override var objective: LinExpr
        get() = solver.objective
        set(value) {
            solver.objective = value
        }

    override var objectiveConst: Double
        get() = solver.objectiveConst
        set(value) {
            solver.objectiveConst = value
        }

    override var optTol: Double
        get() = solver.optTol
        set(value) {
            solver.optTol = value
        }

    override var preprocess: Int
        get() = solver.preprocess
        set(value) {
            solver.preprocess = value
        }

    override var roundIntVars: Boolean
        get() = solver.roundIntVars
        set(value) {
            solver.roundIntVars = value
        }

    override var seed: Int
        get() = solver.seed
        set(value) {
            solver.seed = value
        }

    override var sense: String
        get() = solver.sense
        set(value) {
            solver.sense = value
        }

    override var solPoolSize: Int
        get() = solver.solPoolSize
        set(value) {
            solver.solPoolSize = value
        }

    override var start: Map<Var, Double>
        get() = solver.start
        set(value) {
            solver.start = value
        }

    override var searchProgressLog: String
        get() = solver.searchProgressLog
        set(value) {
            solver.searchProgressLog = value
        }

    override var storeSearchProgressLog: Boolean
        get() = solver.storeSearchProgressLog
        set(value) {
            solver.storeSearchProgressLog = value
        }

    override var threads: Int
        get() = solver.threads
        set(value) {
            solver.threads = value
        }

    override var verbose: Boolean
        get() = solver.verbose
        set(value) {
            solver.verbose = value
        }

    // endregion vars


    override fun get(property: String) = when (property) {
        // vals
        "gap" -> gap
        "numCols" -> numCols
        "numInt" -> numInt
        "numRows" -> numRows
        "numNZ" -> numNZ
        "numSolutions" -> numSolutions
        "objectiveBound" -> objectiveBound
        "objectiveValue" -> objectiveValue
        "status" -> status
        "solverName" -> solverName

        // vars
        "clique" -> clique
        "cutoff" -> cutoff
        "cutPasses" -> cutPasses
        "cuts" -> cuts
        "cutsGenerator" -> cutsGenerator
        "emphasis" -> emphasis
        "infeasTol" -> infeasTol
        "integerTol" -> integerTol
        "lazyConstrsGenerator" -> lazyConstrsGenerator
        "lpMethod" -> lpMethod
        "maxMipGap" -> maxMipGap
        "maxMipGapAbs" -> maxMipGapAbs
        "maxNodes" -> maxNodes
        "maxSeconds" -> maxSeconds
        "maxSolutions" -> maxSolutions
        "objective" -> objective
        "objectiveConst" -> objectiveConst
        "optTol" -> optTol
        "preprocess" -> preprocess
        "roundIntVars" -> roundIntVars
        "seed" -> seed
        "sense" -> sense
        "solPoolSize" -> solPoolSize
        "start" -> start
        "storeSearchProgressLog" -> storeSearchProgressLog
        "threads" -> threads
        "verbose" -> verbose

        else -> solver.get(property)
    }

    override fun getDouble(property: String) = solver.getDouble(property)

    override fun getInt(property: String) = solver.getInt(property)

    override fun getString(property: String) = solver.getString(property)

    override fun <T> set(property: String, value: T) = when (property) {
        // vars
        "clique" -> clique = value as Int
        "cutoff" -> cutoff = value as Double
        "cutPasses" -> cutPasses = value as Int
        "cuts" -> cuts = value as Int
        "cutsGenerator" -> cutsGenerator = value as Int
        "emphasis" -> emphasis = value as SearchEmphasis
        "infeasTol" -> infeasTol = value as Double
        "integerTol" -> integerTol = value as Double
        "lazyConstrsGenerator" -> lazyConstrsGenerator = value as Int
        "lpMethod" -> lpMethod = value as LPMethod
        "maxMipGap" -> maxMipGap = value as Double
        "maxMipGapAbs" -> maxMipGapAbs = value as Double
        "maxNodes" -> maxNodes = value as Int
        "maxSeconds" -> maxSeconds = value as Double
        "maxSolutions" -> maxSolutions = value as Int
        "objective" -> objective = value as LinExpr
        "objectiveConst" -> objectiveConst = value as Double
        "optTol" -> optTol = value as Double
        "preprocess" -> preprocess = value as Int
        "roundIntVars" -> roundIntVars = value as Boolean
        "seed" -> seed = value as Int
        "sense" -> sense = value as String
        "solPoolSize" -> solPoolSize = value as Int
        "start" -> start = value as Map<Var, Double>
        "storeSearchProgressLog" -> storeSearchProgressLog = value as Boolean
        "threads" -> threads = value as Int
        "verbose" -> verbose = value as Boolean

        else -> solver.set(property, value)
    }
}