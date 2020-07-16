package mip.examples

import mip.*
import kotlin.random.Random

private
fun main() {
    val start = System.currentTimeMillis()
    var checkpoint = System.currentTimeMillis()
    val runtime: (Long) -> Double = { (System.currentTimeMillis() - it) / 1000.0 }

    // number of queens
    val n = 250
    val ns = 0 until n

    val model = Model("RuntimeTest", MINIMIZE, GUROBI)

    println("Model started in ${runtime(checkpoint)} seconds!")
    checkpoint = System.currentTimeMillis()

    val x = Array(n) { i -> Array(n) { j -> model.addBinVar("x($i,$j)") } }

    println("${model.vars.size} variables created in ${runtime(checkpoint)} seconds!")
    checkpoint = System.currentTimeMillis()

    val random = Random(0)
    for (i in ns) {
        for (j in ns) {
            model += ns.filter { random.nextBoolean() }.flatMap { k->
                ns.filter { random.nextBoolean() }.map { l->
                    x[k][l]
                }
            } leq random.nextInt(100) named "constr_${i}_$j"
        }
    }

    println("${model.constrs.size} constraints created in ${runtime(checkpoint)} seconds!")

    // queens.write("queens.lp")
    // queens.optimize()
    //
    // for (i in ns) {
    //     for (j in ns)
    //         print(if (x[i][j].x >= EPS) "O " else ". ")
    //     println()
    // }
    println()
    println("Total runtime: ${runtime(start)} seconds!")
}

fun QueensRuntime() = main()