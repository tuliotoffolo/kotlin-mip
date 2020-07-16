package mip.examples

import kotlin.math.*
import mip.*
import java.util.*

private

fun main() {
    val start = System.currentTimeMillis()
    var checkpoint = System.currentTimeMillis()
    val runtime: (Long) -> Double = { (System.currentTimeMillis() - it) / 1000.0 }

    // number of cities (n) and range of them (V)
    val n = 20
    val V = 0 until n

    // locations (randomly generated)
    val rand = Random(0)
    val location = Array(n) { rand.nextInt(1000) to rand.nextInt(1000) }

    // building the distance matrix
    val c = Array(n) { i -> DoubleArray(n) }
    for (i in V)
        for (j in V) if (i != j)
            c[i][j] = round(sqrt((location[i].first - location[j].first).toDouble().pow(2.0)
                + (location[i].second - location[j].second).toDouble().pow(2.0)))

    println("Data created in ${runtime(checkpoint)} seconds!\n")
    checkpoint = System.currentTimeMillis()

    val model = Model("TSPLarge")

    println("Model created in ${runtime(checkpoint)} seconds!\n")
    checkpoint = System.currentTimeMillis()

    // binary variables indicating if arc (i,j) is used or not
    val x = V.list { i -> V.list { j -> model.addBinVar("x($i,$j)", obj = c[i][j]) } }

    println("${model.vars.size} variables created in ${runtime(checkpoint)} seconds!\n")
    checkpoint = System.currentTimeMillis()

    // Constraints 1 and 2: leave and enter each city exactly only once
    for (i in V) {
        model += V.filter { it != i }.sum { x[i][it] } eq 1 named "out_$i"
        model += V.filter { it != i }.sum { x[it][i] } eq 1 named "in_$i"
    }

    // Constraints 3: subtour elimination (there are an exponential number of constraints here)
    for (m in 2 until n) {
        combinations(V.toList(), m).forEach {
            val pairs = it.flatMap { i -> it.filter { j -> j != i }.map { j -> i to j } }
            model += pairs.sum { (i, j) -> x[i][j] } leq m - 1
        }
    }

    println("${model.constrs.size} constraints created in ${runtime(checkpoint)} seconds!\n")
    checkpoint = System.currentTimeMillis()

    // model.maxSeconds = 30000.0
    // model.optimize()
    //
    // // checking if a solution was found and printing it
    // if (model.hasSolution) {
    //     println("Route with total distance ${model.objectiveValue} found:")
    //     print("  0")
    //     var i = 0
    //     do {
    //         for (j in V) if (x[i][j].x >= 0.99) {
    //             i = j
    //             print(" -> $i")
    //             break
    //         }
    //     }
    //     while (i != 0)
    // }

    // sanity tests
    // assert(model.status == OptimizationStatus.Optimal)
    // assert(model.objectiveValue.roundToInt() == 547)
    // assert(model.validateOptimizationResult())
}

private inline fun <reified T> combinations(arr: List<T>, m: Int) = sequence {
    val n = arr.size
    val result = Array(m) { arr[0] }
    val stack = LinkedList<Int>()
    stack.push(0)
    while (stack.isNotEmpty()) {
        var resIndex = stack.size - 1;
        var arrIndex = stack.pop()

        while (arrIndex < n) {
            result[resIndex++] = arr[arrIndex++]
            stack.push(arrIndex)

            if (resIndex == m) {
                yield(result.toList())
                break
            }
        }
    }
}

fun runTSPVeryLarge() = main()
