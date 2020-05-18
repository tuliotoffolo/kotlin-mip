import br.ufop.jmip.*

fun main() {
    val start = System.currentTimeMillis()
    var checkpoint = System.currentTimeMillis()
    val runtime: (Long) -> Double = { (System.currentTimeMillis() - it) / 1000.0 }

    // number of queens
    val n = 1000
    val ns = 0 until n

    val queens = Model("NQueens", MINIMIZE, GUROBI)

    println("Model started in ${runtime(checkpoint)} seconds!")
    checkpoint = System.currentTimeMillis()

    val x = ns.map { i ->
        ns.map { j ->
            queens.addVar("x($i,$j)", varType = VarType.BINARY)
        }
    }

    println("Variables created in ${runtime(checkpoint)} seconds!")
    checkpoint = System.currentTimeMillis()

    // one per row
    for (i in ns)
        queens += Constr.eq(ns.map { j -> x[i][j] }, 1)

    // one per column
    for (j in ns)
        queens += Constr.eq(ns.map { i -> x[i][j] }, 1)

    println("First constraint set created in ${runtime(checkpoint)} seconds!")
    checkpoint = System.currentTimeMillis()

    // diagonal \
    for (k in 2 - n until n - 1) {
        val lhs = LinExpr()
        for (i in ns) for (j in ns) if (i - j == k)
            lhs += x[i][j]
        queens += Constr.leq(lhs, 1)
    }

    println("Second constraint set created in ${runtime(checkpoint)} seconds!")
    checkpoint = System.currentTimeMillis()

    // diagonal \
    for (k in 3 until n + n) {
        val lhs = LinExpr()
        for (i in ns) for (j in ns) if (i + j == k)
            lhs += x[i][j]
        queens += Constr.leq(lhs, 1)
    }

    println("Third constraint set created in ${runtime(checkpoint)} seconds!")

    // queens.write("queens.lp")
    // queens.optimize()
    //
    // println()
    // for (i in ns) {
    //     for (j in ns)
    //         print(if (x[i][j].x >= EPS) "O " else ". ")
    //     println()
    // }

    println("Total runtime: ${runtime(start)} seconds!")
}
