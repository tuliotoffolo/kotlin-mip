package br.ufop.jmip.entities

import kotlin.math.abs

class LinSum {
    var const = 0.0
    var sense = ""
    val terms = ArrayList<LinTerm>()

    constructor(iterable: Iterable<Any>) {
        add(iterable)
    }

    @JvmOverloads
    constructor(terms: Map<Var, Double>, const: Number = 0.0) {
        // setting constant and sense
        this.const = const.toDouble()

        // adding variables and their coefficients
        this.terms.addAll(terms.map { LinTerm(it.key, it.value) })
    }

    @JvmOverloads
    constructor(terms: List<LinTerm>, const: Number = 0.0) {
        // setting constant and sense
        this.const = const.toDouble()

        // adding variables and their coefficients
        this.terms.addAll(terms)
    }

    @JvmOverloads
    constructor(vars: List<Var>, coeffs: List<Number>, const: Number = 0.0) {
        // setting constant and sense
        this.const = const.toDouble()

        // adding variables and their coefficients
        assert(vars.size == coeffs.size)
        this.terms.ensureCapacity(vars.size)
        for (i in vars.indices) this.terms.add(LinTerm(vars[i], coeffs[i]))
    }

    @JvmOverloads
    constructor(term: LinTerm, const: Number = 0.0) {
        this.const = const.toDouble()
        this.terms.add(term)
    }

    @JvmOverloads
    constructor(variable: Var, coeff: Number = 1.0, const: Number = 0.0) {
        this.const = const.toDouble()
        this.terms.add(LinTerm(variable, coeff))
    }

    constructor(const: Number) {
        this.const = const.toDouble()
    }

    constructor()

    @JvmOverloads
    fun add(iterable: Iterable<Any>, mult: Double = 1.0) {
        for (term in iterable) {
            when (term) {
                is LinExpr -> add(term as LinExpr, mult)
                is LinSum -> add(term as LinSum, mult)
                is LinTerm -> add(term as LinTerm, mult)
                is Var -> add(term as Var, mult)
                is Number -> add(term.toDouble() * mult)
                else -> throw IllegalArgumentException()
            }
        }
    }

    @JvmOverloads
    fun add(linExpr: LinExpr, mult: Double = 1.0) {
        const += linExpr.const * mult
        for ((v, coeff) in linExpr.terms) {
            add(v, coeff * mult)
        }
    }

    @JvmOverloads
    fun add(linSum: LinSum, mult: Double = 1.0) {
        const += linSum.const * mult
        for (term in linSum.terms) {
            add(term.variable, term.coeff * mult)
        }
    }

    @JvmOverloads
    fun add(linTerm: LinTerm, mult: Double = 1.0) {
        terms.add(linTerm)
    }

    @JvmOverloads
    fun add(variable: Var, coeff: Number = 1.0) {
        terms.add(LinTerm(variable, coeff))
    }

    fun add(const: Number) {
        this.const += const.toDouble()
    }

    fun copy(): LinSum {
        return LinSum(terms, const)
    }

    fun divide(const: Number) {
        val multiplier = const.toDouble();
        if (multiplier == 0.0)
            throw ArithmeticException()
        if (multiplier == 1.0)
            return

        this.const /= multiplier;
        for (i in terms.indices) {
            terms[i] = terms[i] / multiplier;
        }
    }

    fun multiply(const: Number) {
        val multiplier = const.toDouble();
        if (multiplier == 0.0) {
            this.const = 0.0
            terms.clear()
        }
        if (multiplier == 1.0)
            return

        this.const *= multiplier;
        for (i in terms.indices) {
            terms[i] = terms[i] * multiplier;
        }
    }

    fun sub(iterable: Iterable<Any>) = add(iterable, -1.0)
    fun sub(linExpr: LinExpr) = add(linExpr, -1.0)
    fun sub(linSum: LinSum) = add(linSum, -1.0)
    fun sub(linTerm: LinTerm) = add(linTerm, -1.0)
    fun sub(variable: Var, coeff: Number) = add(variable, coeff.toDouble() * -1.0)
    fun sub(variable: Var) = add(variable, -1.0)
    fun sub(const: Number) = add(const.toDouble() * -1.0)

    fun toLinExpr() = LinExpr(terms)

    override fun toString(): String {
        var counter = 0
        val s = StringBuilder()
        for ((v, coeff) in terms) {
            if (coeff == 0.0) continue
            s.append(if (coeff < 0) "- " else if (counter++ == 0) "" else "+ ")
            s.append(if (coeff != 1.0) "${abs(coeff)} " else "")
            s.append(v.name)
            s.append(" ")
        }
        if (const != 0.0) {
            s.append(if (const < 0) "- " else if (s.isEmpty()) "" else "+ ")
            s.append(abs(const))
        }
        return s.toString()
    }

    // region kotlin operators

    operator fun plusAssign(iterable: Iterable<Any>) = add(iterable)
    operator fun plusAssign(linExpr: LinExpr) = add(linExpr)
    operator fun plusAssign(linSum: LinSum) = add(linSum)
    operator fun plusAssign(linTerm: LinTerm) = add(linTerm)
    operator fun plusAssign(variable: Var) = add(variable)
    operator fun plusAssign(const: Number) = add(const)

    operator fun minusAssign(iterable: Iterable<Any>) = sub(iterable)
    operator fun minusAssign(linExpr: LinExpr) = sub(linExpr)
    operator fun minusAssign(linSum: LinSum) = sub(linSum)
    operator fun minusAssign(linTerm: LinTerm) = sub(linTerm)
    operator fun minusAssign(variable: Var) = sub(variable)
    operator fun minusAssign(const: Number) = sub(const)

    operator fun timesAssign(const: Number) = multiply(const)
    operator fun divAssign(const: Number) = divide(const)

    operator fun plus(iterable: Iterable<Any>) = copy().apply { add(iterable) }
    operator fun plus(linExpr: LinExpr) = copy().apply { add(linExpr) }
    operator fun plus(linSum: LinSum) = copy().apply { add(linSum) }
    operator fun plus(linTerm: LinTerm) = copy().apply { add(linTerm) }
    operator fun plus(variable: Var) = copy().apply { add(variable) }
    operator fun plus(const: Number) = copy().apply { add(const) }
    operator fun unaryPlus() = this

    operator fun minus(iterable: Iterable<Any>) = copy().apply { sub(iterable) }
    operator fun minus(linExpr: LinExpr) = copy().apply { sub(linExpr) }
    operator fun minus(linSum: LinSum) = copy().apply { sub(linSum) }
    operator fun minus(linTerm: LinTerm) = copy().apply { sub(linTerm) }
    operator fun minus(variable: Var) = copy().apply { sub(variable) }
    operator fun minus(const: Number) = copy().apply { sub(const) }
    operator fun unaryMinus() = copy().apply { multiply(-1) }

    operator fun times(const: Number) = copy().apply { multiply(const) }
    operator fun div(const: Number) = copy().apply { divide(const) }

    // endregion kotlin operators
}
