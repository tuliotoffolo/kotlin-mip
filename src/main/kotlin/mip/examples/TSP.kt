package mip.examples

import mip.*
import kotlin.random.Random

fun main(args: Array<String>) {
    val random = Random(0)

    // reading problem data
    val n = 40
    val V = 1..n

    // considering random distances
    val dist = HashMap<Pair<Int, Int>, Double>()
    for (i in V)
        for (j in V) if (i != j)
            dist[i to j] = random.nextInt(1000).toDouble()

    val model = Model(solverName = CBC)
    val x = HashMap<Pair<Int, Int>, Var>()
    val y = HashMap<Int, Var>()

    // creating variables X
    for (i in V)
        for (j in V) if (i != j)
            x[i to j] = model.addBinVar("x($i,$j)", obj = dist[i to j])

    // creating variables Y
    for (i in V)
        y[i] = model.addVar("y($i)")

    // creating constraints
    for (i in V) {
        val lhs = LinExpr()
        for (j in V) if (j != i)
            lhs += x[i to j]
        model += lhs eq 1
    }
    for (j in V)
        model += V.filter { i -> i != j }.map { i -> x[i to j] } eq 1

    // creating MTZ constraints
    for (i in 2..n) {
        for (j in 2..n) if (i != j) {
            model += y[i]!! - y[j] + n * x[i to j] leq n - 1
        }
    }

    model.optimize()

    var tam = 0
    var i = 1
    while (tam < n) {
        for (j in V) if (i != j) {
            if (x[i to j]!!.x > 0.99) {
                println("x[$i,$j] = ${x[i to j]!!.x}")
                i = j
                tam += 1
                break
            }
        }
    }
}
