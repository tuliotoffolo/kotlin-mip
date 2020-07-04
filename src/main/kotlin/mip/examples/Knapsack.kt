package mip.examples

import mip.*
import kotlin.math.roundToInt

fun main() {
    // problem's input data
    val p = arrayOf(10, 13, 18, 31, 7, 15)
    val w = arrayOf(11, 15, 20, 35, 10, 33)
    val c = 47
    val I = 0 until w.size - 1

    // creating model
    val m = Model("Knapsack", sense = MAXIMIZE)

    // creating vars
    val x = I.map { m.addBinVar("item_$it", obj = p[it]) }

    // adding constraint and solving model
    m += I.map { w[it] * x[it] } leq c
    m.optimize()

    // printing result
    val selected = x.filter { it.x >= 0.99 }
    print("Selected items: ${selected}")

    // sanity tests
    assert(m.status == OptimizationStatus.Optimal)
    assert(m.objectiveValue.roundToInt() == 41)
    assert(m.constrs[0].slack.roundToInt() == 1)
}
