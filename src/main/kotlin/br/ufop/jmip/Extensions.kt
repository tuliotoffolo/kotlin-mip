@file:Suppress("NOTHING_TO_INLINE")

package br.ufop.jmip

operator fun Number.plus(linExpr: LinExpr) = linExpr + this
operator fun Number.plus(variable: Var) = variable + this

operator fun Number.minus(linExpr: LinExpr) = -linExpr + this
operator fun Number.minus(variable: Var) = -variable + this

operator fun Number.times(linExpr: LinExpr) = linExpr * this
operator fun Number.times(variable: Var) = variable * this

inline infix fun Number.leq(other: LinExpr) = Constr.leq(this, other)
inline infix fun Number.leq(other: Var) = Constr.leq(this, other)

inline infix fun Number.geq(other: LinExpr) = Constr.geq(this, other)
inline infix fun Number.geq(other: Var) = Constr.geq(this, other)

inline infix fun Number.eq(other: LinExpr) = Constr.eq(this, other)
inline infix fun Number.eq(other: Var) = Constr.eq(this, other)

fun xsum(iterable: Iterable<Any>): LinExpr = LinExpr(iterable)
fun xsum(vararg elements: Any): LinExpr = LinExpr(if (elements.isNotEmpty()) elements.asList() else emptyList())
