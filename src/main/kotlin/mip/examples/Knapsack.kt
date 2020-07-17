package mip.examples

import mip.*
import kotlin.math.roundToInt

private

fun main() {
    // problem's input data
    val p = arrayOf(10, 13, 18, 31, 7, 15)
    val w = arrayOf(11, 15, 20, 35, 10, 33)
    val c = 47
    val I = 0 until w.size

    // creating model
    val m = Model("Knapsack", solverName = CPLEX)

    // creating vars and setting objective function
    val x = I.list { i -> m.addBinVar("item_$i") }
    m.objective = maximize(I.sum { i -> p[i] * x[i] })

    // adding constraint and solving model
    m += I.sum { i -> w[i] * x[i] } leq c
    m.optimize()
    m.write("queens.lp")

    // printing result
    val selected = x.filter { it.x >= 0.99 }
    print("Total cost: ${m.objectiveValue}")
    print("Selected items: $selected")

    // sanity tests
    assert(m.status == OptimizationStatus.Optimal)
    assert(m.objectiveValue.roundToInt() == 41)
    assert(m.constrs[0].slack.roundToInt() == 1)
    assert(m.validateOptimizationResult())
}

fun runKnapsack() = main()