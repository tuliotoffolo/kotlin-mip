package br.ufop.jmip

data class LinTerm(val variable: Var, val coeff: Double = 1.0) {

    constructor(variable: Var, coeff: Number) : this(variable, coeff.toDouble())

    @JvmOverloads
    constructor(term: LinTerm, mult: Number = 1.0) : this(term.variable, term.coeff * mult.toDouble())

    fun toLinExpr(coeff: Number = 1.0) = LinExpr(variable, this.coeff * coeff.toDouble())

    // region override functions

    override fun toString(): String = "$coeff * $variable"

    // endregion override functions

    // region kotlin operators

    operator fun plus(iterable: Iterable<Any>) = toLinExpr().apply { add(iterable) }
    operator fun plus(linExpr: LinExpr) = linExpr.copy().apply { add(this) }
    operator fun plus(variable: Var) = toLinExpr().apply { add(variable) }
    operator fun plus(const: Number) = toLinExpr().apply { add(const) }
    operator fun unaryPlus() = this

    operator fun minus(iterable: Iterable<Any>) = toLinExpr().apply { sub(iterable) }
    operator fun minus(linExpr: LinExpr) = linExpr.copy().apply { multiply(-1); add(this) }
    operator fun minus(variable: Var) = toLinExpr().apply { add(variable) }
    operator fun minus(const: Number) = toLinExpr().apply { sub(const) }
    operator fun unaryMinus() = toLinExpr(-1.0)

    operator fun times(mult: Number) = LinTerm(this, mult)
    operator fun div(mult: Number) = LinTerm(this, 1.0 / mult.toDouble())

    operator fun compareTo(other: LinTerm) = variable.compareTo(other.variable)

    // endregion kotlin operators
}
