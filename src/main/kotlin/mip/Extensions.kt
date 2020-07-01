@file:Suppress("NOTHING_TO_INLINE")

/**
 * Useful extensions to quickly create linear expressions and constraints in Kotlin.
 *
 * @author Túlio Toffolo
 */

package mip

operator fun Number.plus(expr: Iterable<Any?>) = LinExpr(expr).also { it.add(this) }
operator fun Number.plus(expr: LinExpr?) = LinExpr(expr).also { it.add(this) }
operator fun Number.plus(variable: Var?) = LinExpr(variable).also { it.add(this) }

operator fun Number.minus(expr: Iterable<Any?>) = LinExpr(expr).also { it.multiply(-1.0); it.add(this) }
operator fun Number.minus(expr: LinExpr?) = LinExpr(expr).also { it.multiply(-1.0); it.add(this) }
operator fun Number.minus(variable: Var?) = LinExpr(variable, -1.0).also { it.add(this) }

operator fun Number.times(expr: Iterable<Any?>) = LinExpr(expr).also { it.multiply(this) }
operator fun Number.times(expr: LinExpr?) = LinExpr(expr).also { it.multiply(this) }
operator fun Number.times(variable: Var?) = LinExpr(variable).also { it.multiply(this) }

inline infix fun Number.leq(other: Iterable<Any?>) = Constr.leq(this, other)
inline infix fun Number.leq(other: LinExpr?) = Constr.leq(this, other)
inline infix fun Number.leq(other: Var?) = Constr.leq(this, other)

inline infix fun Number.geq(other: Iterable<Any?>) = Constr.geq(this, other)
inline infix fun Number.geq(other: LinExpr?) = Constr.geq(this, other)
inline infix fun Number.geq(other: Var?) = Constr.geq(this, other)

inline infix fun Number.eq(other: Iterable<Any?>) = Constr.eq(this, other)
inline infix fun Number.eq(other: LinExpr?) = Constr.eq(this, other)
inline infix fun Number.eq(other: Var?) = Constr.eq(this, other)


operator fun Iterable<Any?>.plus(expr: Iterable<Any?>) = LinExpr(this).apply { add(expr) }
operator fun Iterable<Any?>.plus(expr: LinExpr?) = LinExpr(this).apply { add(expr) }
operator fun Iterable<Any?>.plus(variable: Var?) = LinExpr(this).apply { add(variable) }
operator fun Iterable<Any?>.plus(number: Number) = LinExpr(this).apply { add(number) }

operator fun Iterable<Any?>.minus(expr: Iterable<Any?>) = LinExpr(this).apply { sub(expr) }
operator fun Iterable<Any?>.minus(expr: LinExpr?) = LinExpr(this).apply { sub(expr) }
operator fun Iterable<Any?>.minus(variable: Var?) = LinExpr(this).apply { sub(variable) }
operator fun Iterable<Any?>.minus(number: Number) = LinExpr(this).apply { sub(number) }

operator fun Iterable<Any?>.times(number: Number) = LinExpr(this).apply { multiply(number) }

inline infix fun Iterable<Any?>.leq(other: Iterable<Any?>) = Constr.leq(this, other)
inline infix fun Iterable<Any?>.leq(other: LinExpr?) = Constr.leq(this, other)
inline infix fun Iterable<Any?>.leq(other: Var?) = Constr.leq(this, other)
inline infix fun Iterable<Any?>.leq(other: Number) = Constr.leq(this, other)

inline infix fun Iterable<Any?>.geq(other: Iterable<Any?>) = Constr.geq(this, other)
inline infix fun Iterable<Any?>.geq(other: LinExpr?) = Constr.geq(this, other)
inline infix fun Iterable<Any?>.geq(other: Var?) = Constr.geq(this, other)
inline infix fun Iterable<Any?>.geq(other: Number) = Constr.geq(this, other)

inline infix fun Iterable<Any?>.eq(other: Iterable<Any?>) = Constr.eq(this, other)
inline infix fun Iterable<Any?>.eq(other: LinExpr?) = Constr.eq(this, other)
inline infix fun Iterable<Any?>.eq(other: Var?) = Constr.eq(this, other)
inline infix fun Iterable<Any?>.eq(other: Number) = Constr.eq(this, other)

inline fun xsum(iterable: Iterable<Any?>): LinExpr = LinExpr(iterable)
inline fun xsum(vararg elements: Any?): LinExpr = LinExpr(elements.asIterable())
