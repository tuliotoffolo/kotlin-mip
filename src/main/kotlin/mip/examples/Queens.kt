package mip.examples

import mip.*

private

fun main() {
    // number of queens (n) and range of queens (ns)
    val n = 40
    val ns = 0 until n

    val queens = Model("nQueens")
    val x = ns.list { i -> ns.list { j -> queens.addBinVar("x($i,$j)") } }

    // one queen per row
    for (i in ns)
        queens += ns.sum { j -> x[i][j] } eq 1 named "row_$i"

    // one per column
    for (j in ns)
        queens += ns.sum { i -> x[i][j] } eq 1 named "col_$j"

    // diagonal \
    for (k in 2 - n .. n - 2) {
        val lhs = LinExpr()
        for (i in ns) if (i - k in ns)
            lhs += x[i][i - k]
        queens += lhs leq 1 named "diag1_${k.toString().replace("-", "m")}"
    }

    // diagonal /
    for (k in 3 .. n + n - 1) {
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

fun runQueens() = main()