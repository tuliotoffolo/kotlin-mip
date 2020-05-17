package br.ufop.jmip.entities

import kotlin.math.abs

class LinExpr {
    var const = 0.0
    var sense = ' '
    val terms = HashMap<Var, Double>()

    val isAffine: Boolean get() = sense == ' '
    val size: Int get() = terms.size

    constructor(iterable: Iterable<Any>) {
        add(iterable)
    }

    @JvmOverloads
    constructor(terms: Map<Var, Double>, const: Number = 0.0) {
        // setting constant and sense
        this.const = const.toDouble()

        // adding variables and their coefficients
        this.terms.putAll(terms)
    }

    @JvmOverloads
    constructor(terms: List<LinTerm>, const: Number = 0.0) {
        // setting constant and sense
        this.const = const.toDouble()

        // adding variables and their coefficients
        for (p in terms) add(p)
    }

    @JvmOverloads
    constructor(vars: List<Var>, coeffs: List<Number>, const: Number = 0.0) {
        // setting constant and sense
        this.const = const.toDouble()

        // adding variables and their coefficients
        assert(vars.size == coeffs.size)
        for (i in vars.indices) add(vars[i], coeffs[i])
    }

    @JvmOverloads
    constructor(pair: LinTerm, const: Number = 0.0) {
        this.const = const.toDouble()
        this.terms[pair.variable] = pair.coeff
    }

    @JvmOverloads
    constructor(variable: Var, coeff: Number = 1.0, const: Number = 0.0) {
        this.const = const.toDouble()
        this.terms[variable] = coeff.toDouble()
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
        add(linTerm.variable, linTerm.coeff * mult)
    }

    @JvmOverloads
    fun add(variable: Var, coeff: Number = 1.0) {
        if (coeff == 0.0) return

        val res = coeff.toDouble() + (terms[variable] ?: 0.0)
        if (res != 0.0)
            terms[variable] = res
        else
            terms.remove(variable)
    }

    fun add(const: Number) {
        this.const += const.toDouble()
    }

    fun copy(): LinExpr {
        val copy = LinExpr()
        copy.const = const
        copy.terms.putAll(terms)
        return copy
    }

    fun divide(const: Number) {
        val multiplier = const.toDouble();
        if (multiplier == 0.0)
            throw ArithmeticException()
        if (multiplier == 1.0)
            return

        this.const /= multiplier;
        for ((v, coeff) in terms) {
            terms[v] = coeff / multiplier;
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
        for ((v, coeff) in terms) {
            terms[v] = coeff * multiplier;
        }
    }

    fun sub(iterable: Iterable<Any>) = add(iterable, -1.0)
    fun sub(linExpr: LinExpr) = add(linExpr, -1.0)
    fun sub(linSum: LinSum) = add(linSum, -1.0)
    fun sub(linTerm: LinTerm) = add(linTerm, -1.0)
    fun sub(variable: Var) = add(variable, -1.0)
    fun sub(const: Number) = add(const.toDouble() * -1.0)

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

    // region addLHS and addRHS aliases

    fun addLHS(iterable: Iterable<Any>) = add(iterable)
    fun addLHS(linExpr: LinExpr) = add(linExpr)
    fun addLHS(linSum: LinSum) = add(linSum)
    fun addLHS(linTerm: LinTerm) = add(linTerm)
    fun addLHS(variable: Var) = add(variable)
    fun addLHS(const: Number) = add(const)

    fun addRHS(iterable: Iterable<Any>) = sub(iterable)
    fun addRHS(linExpr: LinExpr) = sub(linExpr)
    fun addRHS(linSum: LinSum) = sub(linSum)
    fun addRHS(linTerm: LinTerm) = sub(linTerm)
    fun addRHS(variable: Var) = sub(variable)
    fun addRHS(const: Number) = sub(const)

    // endregion addLHS and addRHS aliases

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

data class NamedExpr(val linExpr: LinExpr, val name: String)