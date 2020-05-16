package br.ufop.jmip

import br.ufop.jmip.entities.Model
import br.ufop.jmip.entities.Var
import br.ufop.jmip.entities.leq

fun main() {
    val model = Model()
    val x = (1..10).map { model.addVar("x($it)") }

    model += leq(x[0] + x[1] + x[2], 10)

    val v = Var(model, 0)
}
