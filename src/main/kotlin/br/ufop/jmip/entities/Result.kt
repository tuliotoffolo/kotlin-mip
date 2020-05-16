package br.ufop.jmip.entities

import br.ufop.jmip.util.Logger
import kotlin.reflect.KProperty

class Result(val model: Model, map: Map<String, Any?> = emptyMap()) {

    private val delegate: Delegate = Delegate(this)

    val map = mutableMapOf(
        "cuts" to -1,
        "cut_passes" to -1,
        "clique" to -1,
        "preprocess" to -1,
        "cuts_generator" to null,
        "lazy_constrs_generator" to null,
        "start" to null,
        "threads" to 0,
        "lp_method" to LPMethod.AUTO,
        "n_cols" to 0,
        "n_rows" to 0,
        "gap" to INF,
        "store_search_progress_log" to false,
        "plog" to Logger(),
        "integer_tol" to 1e-6,
        "infeas_tol" to 1e-6,
        "opt_tol" to 1e-6,
        "max_mip_gap" to 1e-4,
        "max_mip_gap_abs" to 1e-10,
        "seed" to 0,
        "round_int_vars" to true,
        "sol_pool_size" to 10,
    )

    var cuts by delegate
    var cut_passes by delegate
    var clique by delegate
    var preprocess by delegate
    var cuts_generator by delegate
    var lazy_constrs_generator by delegate
    var start by delegate
    var threads by delegate
    var lp_method by delegate
    var n_cols by delegate
    var n_rows by delegate
    var gap by delegate
    var store_search_progress_log by delegate
    var plog by delegate
    var integer_tol by delegate
    var infeas_tol by delegate
    var opt_tol by delegate
    var max_mip_gap by delegate
    var max_mip_gap_abs by delegate
    var seed by delegate
    var round_int_vars by delegate
    var sol_pool_size by delegate

    init {
        for ((k,v) in map) {
            this[k] = v
        }
    }

    operator fun get(arg: String) = map[arg]

    operator fun set(arg: String, value: Any?) {
        if (arg !in map)
            throw IllegalArgumentException("$arg is not a valid settings argument")

        model.set(arg, value)
        map[arg] = value
    }

    private class Delegate(val result: Result) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): Any? {
            return result[property.name]
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Any?) {
            result[property.name] = value
        }
    }
}
