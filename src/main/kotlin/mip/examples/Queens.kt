package mip.examples

import mip.*

private
fun main() {
    // number of queens (n) and range of queens (ns)
    val n = 16
    val ns = 0 until n

    val queens = Model("NQueens")

    // creating variables
    val x = ns.map { i -> ns.map { j -> queens.addBinVar("x($i,$j)") } }

    // one queen per row
    for (i in ns)
        queens += ns.map { j -> x[i][j] } eq 1

    // one per column
    for (j in ns)
        queens += ns.map { i -> x[i][j] } eq 1

    // diagonal \
    for (k in 2 - n until n - 1) {
        val lhs = LinExpr()
        for (i in ns) if (i - k in ns)
            lhs += x[i][i - k]
        queens += lhs leq 1 named "diag1_${k.toString().replace("-", "m")}"
    }

    // diagonal \
    for (k in 3 until n + n) {
        val lhs = LinExpr()
        for (i in ns) if (k - i in ns)
            lhs += x[i][k - i]
        queens += lhs leq 1 named "diag2_$k"
    }

    queens.optimize()

    if (queens.hasSolution) {
        for (i in ns) {
            for (j in ns)
                print(if (x[i][j].x >= EPS) "O " else ". ")
            println()
        }
    }

    assert(queens.validateOptimizationResult())
}

fun Queens() = main()