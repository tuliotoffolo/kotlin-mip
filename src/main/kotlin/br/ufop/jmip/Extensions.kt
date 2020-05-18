package br.ufop.jmip

operator fun Number.plus(linExpr: LinExpr) = linExpr + this
operator fun Number.minus(linExpr: LinExpr) = -linExpr + this
operator fun Number.times(linExpr: LinExpr) = linExpr * this

operator fun Number.plus(variable: Var) = variable + this
operator fun Number.minus(variable: Var) = -variable + this
operator fun Number.times(variable: Var) = variable * this

fun xsum(iterable: Iterable<Any>): LinExpr = LinExpr(iterable)
fun xsum(vararg elements: Any): LinExpr = LinExpr(if (elements.isNotEmpty()) elements.asList() else emptyList())
