package mip.examples

import mip.*

private
fun main() {
    // val temp = readLine()

    val start = System.currentTimeMillis()
    var checkpoint = System.currentTimeMillis()
    val runtime: (Long) -> Double = { (System.currentTimeMillis() - it) / 1000.0 }

    // number of queens
    val n = 1000
    val ns = 0 until n

    val queens = Model("NQueens", MINIMIZE)

    println("Model started in ${runtime(checkpoint)} seconds!")
    checkpoint = System.currentTimeMillis()

    val x = Array(n) { i ->
        Array(n) { j ->
            queens.addBinVar("x($i,$j)")
        }
    }

    println("Variables created in ${runtime(checkpoint)} seconds!")
    checkpoint = System.currentTimeMillis()

    // one per row
    for (i in ns)
        queens += ns.map { j -> x[i][j] } eq 1 named "row_$i"

    // one per column
    for (j in ns)
        queens += ns.map { i -> x[i][j] } eq 1 named "col_$j"

    println("First constraint set created in ${runtime(checkpoint)} seconds!")
    checkpoint = System.currentTimeMillis()

    // diagonal \
    for (k in 2 - n until n - 1) {
        val lhs = LinExpr()
        for (i in ns) for (j in ns) if (i - j == k)
            lhs += x[i][j]
        queens += lhs leq 1 named "diag1_${k.toString().replace("-", "m")}"
    }

    println("Second constraint set created in ${runtime(checkpoint)} seconds!")
    checkpoint = System.currentTimeMillis()

    // diagonal \
    for (k in 3 until n + n) {
        val lhs = LinExpr()
        for (i in ns) for (j in ns) if (i + j == k)
            lhs += x[i][j]
        queens += lhs leq 1 named "diag2_$k"
    }

    println("Third constraint set created in ${runtime(checkpoint)} seconds!")

    // queens.write("queens.lp")
    // queens.optimize()

    // for (i in ns) {
    //     for (j in ns)
    //         print(if (x[i][j].x >= EPS) "O " else ". ")
    //     println()
    // }
    println()
    println("Total runtime: ${runtime(start)} seconds!")
}

fun QueensRuntime() = main()