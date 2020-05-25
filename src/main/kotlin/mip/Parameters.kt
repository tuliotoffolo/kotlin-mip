package mip

import kotlin.reflect.KProperty

abstract class Parameters {

    open var objective by Param<LinExpr>()
    open var sense by Param<String>()
    open var cutoff by Param<Double>()

    open var cuts by Param<Int>()
    open var cutPasses by Param<Int>()
    open var clique by Param<Int>()
    open var preprocess by Param<Int>()
    open var cutsGenerator by Param<Int>()
    open var lazyConstrsGenerator by Param<Int>()
    open var start by Param<Int>()
    open var threads by Param<Int>()
    open var lpMethod by Param<LPMethod>()
    open var nCols by Param<Int>()
    open var nRows by Param<Int>()
    open val gap by Param<Double>()
    open var storeSearchProgressLog by Param<Double>()
    open var plog by Param<Boolean>()
    open var integerTol by Param<Int>()
    open var infeasTol by Param<Double>()
    open var optTol by Param<Double>()
    open var maxMipGap by Param<Double>()
    open var maxMipGapAbs by Param<Double>()
    open var seed by Param<Int>()
    open var roundIntVars by Param<Boolean>()
    open var solPoolSize by Param<Boolean>()
    open var timeLimit by Param<Double>()
    open val objectiveValue by Param<Double>()
    open val status by Param<OptimizationStatus>()

    abstract fun get(param: String): Any

    abstract fun <T> set(param: String, value: T): Unit

    @Suppress("NOTHING_TO_INLINE")
    private class Param<T>(val default: T? = null) {

        inline operator fun getValue(param: Parameters, property: KProperty<*>): T {
            return param.get(property.name) as T
        }

        inline operator fun setValue(param: Parameters, property: KProperty<*>, value: T) {
            param.set(property.name, value)
        }
    }
}
