package mip.examples

import mip.*
import kotlin.math.roundToInt

private

fun main() {
    // names of places to visit
    val places = arrayOf(
        "Antwerp", "Bruges", "C-Mine", "Dinant", "Ghent", "Grand-Place de Bruxelles",
        "Hasselt", "Leuven", "Mechelen", "Mons", "Montagne de Bueren", "Namur",
        "Remouchamps", "Waterloo"
    )

    // distance matrix
    val dists = arrayOf(
        arrayOf(0, 83, 81, 113, 52, 42, 73, 44, 23, 91, 105, 90, 124, 57),
        arrayOf(83, 0, 161, 160, 39, 89, 151, 110, 90, 99, 177, 143, 193, 100),
        arrayOf(81, 161, 0, 90, 125, 82, 13, 57, 71, 123, 38, 72, 59, 82),
        arrayOf(113, 160, 90, 0, 123, 77, 81, 71, 91, 72, 64, 24, 62, 63),
        arrayOf(52, 39, 125, 123, 0, 51, 114, 72, 54, 69, 139, 105, 155, 62),
        arrayOf(42, 89, 82, 77, 51, 0, 70, 25, 22, 52, 90, 56, 105, 16),
        arrayOf(73, 151, 13, 81, 114, 70, 0, 45, 61, 111, 36, 61, 57, 70),
        arrayOf(44, 110, 57, 71, 72, 25, 45, 0, 23, 71, 67, 48, 85, 29),
        arrayOf(23, 90, 71, 91, 54, 22, 61, 23, 0, 74, 89, 69, 107, 36),
        arrayOf(91, 99, 123, 72, 69, 52, 111, 71, 74, 0, 117, 65, 125, 43),
        arrayOf(105, 177, 38, 64, 139, 90, 36, 67, 89, 117, 0, 54, 22, 84),
        arrayOf(90, 143, 72, 24, 105, 56, 61, 48, 69, 65, 54, 0, 60, 44),
        arrayOf(124, 193, 59, 62, 155, 105, 57, 85, 107, 125, 22, 60, 0, 97),
        arrayOf(57, 100, 82, 63, 62, 16, 70, 29, 36, 43, 84, 44, 97, 0),
    )

    // number of cities (n) and range of them (V)
    val n = places.size
    val V = 0 until n

    val model = Model("TSPCompact", solverName = CBC)

    // binary variables indicating if arc (i,j) is used or not
    val x = V.map { i -> V.map { j -> model.addBinVar("x($i,$j)", dists[i][j]) } }

    // continuous variable to prevent subtours: each city will have a different
    // sequential id in the planned route (except the first one)
    val y = V.map { model.addNumVar("y($it)") }

    // Constraints 1 and 2: leave and enter each city exactly only once
    for (i in V) {
        model += V.filter { it != i }.map { x[i][it] } eq 1
        model += V.filter { it != i }.map { x[it][i] } eq 1
    }

    // Constraints 3: subtour elimination
    for (i in 1 until n)
        for (j in 1 until n) if (i != j)
            model += y[i] - n * x[i][j] geq y[j] - (n - 1)

    model.optimize()

    // checking if a solution was found and printing it
    if (model.hasSolution) {
        println("\nRoute with total distance ${model.objectiveValue} found:")
        var i = 0
        do {
            println("- ${places[i]}")
            for (j in V) if (x[i][j].x >= 0.99) {
                i = j
                break
            }
        }
        while (i != 0)
    }

    // sanity tests
    assert(model.status == OptimizationStatus.Optimal)
    assert(model.objectiveValue.roundToInt() == 547)
    // TODO model.checkOptimizationStatus()
}

fun TSPCompact() = main()