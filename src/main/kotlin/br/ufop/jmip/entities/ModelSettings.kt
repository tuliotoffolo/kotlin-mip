package br.ufop.jmip.entities

import br.ufop.jmip.util.Logger
import kotlin.reflect.KProperty

class ModelSettings(val model: Model, map: Map<String, Any?> = emptyMap()) {

    val map = mutableMapOf(
        "cuts" to Data(-1),
        "cutPasses" to Data(-1),
        "clique" to Data(-1),
        "preprocess" to Data(-1),

        "cutsGenerator" to Data(null),
        "lazyConstrsGenerator" to Data(null),
        "start" to Data(null),
        "threads" to Data(0),
        "lpMethod" to Data(LPMethod.AUTO),
        "nCols" to Data(0),
        "nRows" to Data(0),

        "gap" to Data(INF, querySolver = true, mutable = false),
        "storeSearchProgressLog" to Data(false),
        "plog" to Data(Logger()),
        "integerTol" to Data(1e-6, querySolver = true, mutable = true),
        "infeasTol" to Data(1e-6, querySolver = true, mutable = true),
        "optTol" to Data(1e-6, querySolver = true, mutable = true),
        "maxMipGap" to Data(1e-4, querySolver = true, mutable = true),
        "maxMipGapAbs" to Data(1e-10, querySolver = true, mutable = true),
        "seed" to Data(0, querySolver = true, mutable = true),
        "roundIntVars" to Data(true),
        "solPoolSize" to Data(10),
    )

    init {
        for ((k, v) in map) {
            if (k !in this.map)
                throw NoSuchElementException("Argument $k is not valid")
            this.map[k]!!.value = v
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Any? {
        if (property.name !in map)
            throw NoSuchElementException("Argument ${property.name} is not valid")

        val data = map[property.name]!!
        if (data.querySolver) {
            return model.solver.get(property.name)
        }
        else {
            return map[property.name]!!.value
        }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Any?) {
        // checking for errors...
        if (property.name !in map)
            throw NoSuchElementException("Argument ${property.name} is not valid")

        val data = map[property.name]!!
        if (!data.mutable)
            throw UnsupportedOperationException("Argument ${property.name} can't be modified")

        // setting property value
        if (data.querySolver) {
            model.solver.set(property.name, value)
        }
        else {
            map[property.name]!!.value = value
        }
    }

    class Data(var value: Any?, val querySolver: Boolean = false, val mutable: Boolean = true)
}
