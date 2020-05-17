package br.ufop.jmip

import br.ufop.jmip.entities.BINARY
import br.ufop.jmip.entities.Model
import br.ufop.jmip.entities.Var
import br.ufop.jmip.entities.leq
import kotlin.random.Random

fun main() {
    val random = Random.Default
    val model = Model()

    // creating variables
    val x = (1..10000).map {
        model.addVar("x($it)", ub=1, obj=random.nextInt(1, 100))
    }

    // adding a simple constraint
    model += leq(xsum(x), 100)

    // solving the model and writing an LP file
    model.optimize()
    model.write("model.lp")
}
