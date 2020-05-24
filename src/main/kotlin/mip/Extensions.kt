@file:Suppress("NOTHING_TO_INLINE")

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

inline infix fun Number.le(other: Iterable<Any?>) = Constr.le(this, other)
inline infix fun Number.le(other: LinExpr?) = Constr.le(this, other)
inline infix fun Number.le(other: Var?) = Constr.le(this, other)

inline infix fun Number.ge(other: Iterable<Any?>) = Constr.ge(this, other)
inline infix fun Number.ge(other: LinExpr?) = Constr.ge(this, other)
inline infix fun Number.ge(other: Var?) = Constr.ge(this, other)

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

inline infix fun Iterable<Any?>.le(other: Iterable<Any?>) = Constr.le(this, other)
inline infix fun Iterable<Any?>.le(other: LinExpr?) = Constr.le(this, other)
inline infix fun Iterable<Any?>.le(other: Var?) = Constr.le(this, other)
inline infix fun Iterable<Any?>.le(other: Number) = Constr.le(this, other)

inline infix fun Iterable<Any?>.ge(other: Iterable<Any?>) = Constr.ge(this, other)
inline infix fun Iterable<Any?>.ge(other: LinExpr?) = Constr.ge(this, other)
inline infix fun Iterable<Any?>.ge(other: Var?) = Constr.ge(this, other)
inline infix fun Iterable<Any?>.ge(other: Number) = Constr.ge(this, other)

inline infix fun Iterable<Any?>.eq(other: Iterable<Any?>) = Constr.eq(this, other)
inline infix fun Iterable<Any?>.eq(other: LinExpr?) = Constr.eq(this, other)
inline infix fun Iterable<Any?>.eq(other: Var?) = Constr.eq(this, other)
inline infix fun Iterable<Any?>.eq(other: Number) = Constr.eq(this, other)

inline fun xsum(iterable: Iterable<Any?>): LinExpr = LinExpr(iterable)
inline fun xsum(vararg elements: Any?): LinExpr = LinExpr(elements.asIterable())
