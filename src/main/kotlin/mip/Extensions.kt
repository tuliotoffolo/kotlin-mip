@file:Suppress("NOTHING_TO_INLINE")
@file:JvmName("MIP")
@file:JvmMultifileClass

/**
 * Useful extensions to quickly create linear expressions and constraints in Kotlin.
 *
 * @author Túlio Toffolo
 */

package mip

// region helper function: list and sum

/**
 * Alias for Kotlin's [map] function, which is useful to make the code more readable for
 * mathematicians.
 */
inline fun <T, R> Iterable<T>.list(transform: (T) -> R): List<R> = map(transform)

/**
 * Alias for Kotlin's [map] function, which is useful to make the code more readable for
 * mathematicians.
 */
inline fun <T, R> Iterable<T>.sum(transform: (T) -> R): List<R> = map(transform)

// endregion

// region main operators: +, -, *, /

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
operator fun Iterable<Any?>.plus(number: Number?) = LinExpr(this).apply { add(number) }

operator fun Iterable<Any?>.minus(expr: Iterable<Any?>) = LinExpr(this).apply { sub(expr) }
operator fun Iterable<Any?>.minus(expr: LinExpr?) = LinExpr(this).apply { sub(expr) }
operator fun Iterable<Any?>.minus(variable: Var?) = LinExpr(this).apply { sub(variable) }
operator fun Iterable<Any?>.minus(number: Number?) = LinExpr(this).apply { sub(number) }

operator fun Iterable<Any?>.times(number: Number?) = LinExpr(this).apply { multiply(number) }

// endregion main operators: +, -, *, /

// region leq, geq, eq

inline infix fun Iterable<Any?>.leq(other: Iterable<Any?>) = Constr.leq(this, other)
inline infix fun Iterable<Any?>.leq(other: LinExpr?) = Constr.leq(this, other)
inline infix fun Iterable<Any?>.leq(other: Var?) = Constr.leq(this, other)
inline infix fun Iterable<Any?>.leq(other: Number?) = Constr.leq(this, other)

inline infix fun Iterable<Any?>.geq(other: Iterable<Any?>) = Constr.geq(this, other)
inline infix fun Iterable<Any?>.geq(other: LinExpr?) = Constr.geq(this, other)
inline infix fun Iterable<Any?>.geq(other: Var?) = Constr.geq(this, other)
inline infix fun Iterable<Any?>.geq(other: Number?) = Constr.geq(this, other)

inline infix fun Iterable<Any?>.eq(other: Iterable<Any?>) = Constr.eq(this, other)
inline infix fun Iterable<Any?>.eq(other: LinExpr?) = Constr.eq(this, other)
inline infix fun Iterable<Any?>.eq(other: Var?) = Constr.eq(this, other)
inline infix fun Iterable<Any?>.eq(other: Number?) = Constr.eq(this, other)

// endregion leq, geq, eq

// region objective helpers (min/minimize and max/maximize)

inline fun min(lhs: Iterable<Any?>?) = LinExpr(lhs).apply { sense = MINIMIZE }
inline fun min(lhs: LinExpr?) = LinExpr(lhs).apply { sense = MINIMIZE }
inline fun min(lhs: Var?) = LinExpr(lhs).apply { sense = MINIMIZE }
inline fun min(lhs: Number?) = LinExpr(lhs).apply { sense = MINIMIZE }
inline fun minimize(lhs: Iterable<Any?>?) = LinExpr(lhs).apply { sense = MINIMIZE }
inline fun minimize(lhs: LinExpr?) = LinExpr(lhs).apply { sense = MINIMIZE }
inline fun minimize(lhs: Var?) = LinExpr(lhs).apply { sense = MINIMIZE }
inline fun minimize(lhs: Number?) = LinExpr(lhs).apply { sense = MINIMIZE }

inline fun max(lhs: Iterable<Any?>?) = LinExpr(lhs).apply { sense = MAXIMIZE }
inline fun max(lhs: LinExpr?) = LinExpr(lhs).apply { sense = MAXIMIZE }
inline fun max(lhs: Var?) = LinExpr(lhs).apply { sense = MAXIMIZE }
inline fun max(lhs: Number?) = LinExpr(lhs).apply { sense = MAXIMIZE }
inline fun maximize(lhs: Iterable<Any?>?) = LinExpr(lhs).apply { sense = MAXIMIZE }
inline fun maximize(lhs: LinExpr?) = LinExpr(lhs).apply { sense = MAXIMIZE }
inline fun maximize(lhs: Var?) = LinExpr(lhs).apply { sense = MAXIMIZE }
inline fun maximize(lhs: Number?) = LinExpr(lhs).apply { sense = MAXIMIZE }

// endregion
