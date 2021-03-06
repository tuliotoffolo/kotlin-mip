package mip.examples

import mip.*
import kotlin.math.*

private

fun main() {
    // resource capacities (c), processing times (p), and resource consumptions (u)
    val c = arrayOf(6, 8)
    val p = arrayOf(0, 3, 2, 5, 4, 2, 3, 4, 2, 4, 6, 0) // dummy jobs (0 and n+1) are included
    val u = arrayOf(
        arrayOf(0, 0), arrayOf(5, 1), arrayOf(0, 4), arrayOf(1, 4),
        arrayOf(1, 3), arrayOf(3, 2), arrayOf(3, 1), arrayOf(2, 4),
        arrayOf(4, 0), arrayOf(5, 2), arrayOf(2, 5), arrayOf(0, 0),
    )

    // precedence constraints (s)
    val S = arrayOf(
        0 to 1, 0 to 2, 0 to 3, 1 to 4, 1 to 5, 2 to 9, 2 to 10, 3 to 8, 4 to 6,
        4 to 7, 5 to 9, 5 to 10, 6 to 8, 6 to 9, 7 to 8, 8 to 11, 9 to 11, 10 to 11,
    )

    // ranges to simplify code
    val R = 0 until c.size
    val J = 0 until p.size
    val T = 0 until p.sum() // p.sum() represents a weak upper bound on the total time

    val model = Model("RCPSP")
    val x = J.list { j -> T.list { t -> model.addBinVar("x($j,$t)") } }
    model.objective = minimize(T.sum { t -> t * x[J.last][t] })

    for (j in J)
        model += T.sum { t -> x[j][t] } eq 1

    for (r in R) for (t in T) {
        val lhs = LinExpr()
        for (j in J) for (t2 in max(0, t - p[j] + 1)..t)
            lhs += u[j][r] * x[j][t2]
        model += lhs leq c[r]
    }

    for ((j, s) in S)
        model += T.sum { t -> t * x[s][t] - t * x[j][t] } geq p[j]

    model.optimize()

    // printing solution
    println("Makespan: ${model.objectiveValue}")
    println("Schedule:")
    for (j in J)
        for (t in T) if (x[j][t].x >= 0.99)
            println("  ($j, $t)")

    // sanity tests
    assert(model.hasSolution)
    assert(model.status == OptimizationStatus.Optimal)
    assert(abs(model.objectiveValue - 21) <= 1e-4)
    assert(model.validateOptimizationResult())
}

fun runRCPSP() = main()