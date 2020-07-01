package mip

import kotlin.math.abs

@Suppress("NOTHING_TO_INLINE")
/**
 * This class represents a linear expression, which may be affine or not. Basic operations are
 * implemented and a number of overloaded methods are included as to facilitate using the class.
 *
 * @author Túlio Toffolo
 */
class LinExpr {

    var const = 0.0
    var sense = ' '
    val terms = HashMap<Var, Double>()

    /**
     * Returns if this linear expression is affine, i.e. if it has no sense.
     */
    val isAffine get() = sense == ' '

    /**
     * Returns the number of variables within the linear expression.
     */
    val size get() = terms.size

    constructor(linExpr: LinExpr?) {
        if (linExpr == null) return;

        this.const = linExpr.const
        this.sense = linExpr.sense
        this.terms.putAll(linExpr.terms)
    }

    constructor(iterable: Iterable<Any?>?) {
        add(iterable)
    }

    @JvmOverloads
    constructor(terms: Map<Var, Double>, const: Number? = 0.0) {
        // setting constant and sense
        this.const = const?.toDouble() ?: 0.0

        // adding variables and their coefficients
        this.terms.putAll(terms)
    }

    @JvmOverloads
    constructor(vars: List<Var?>, coeffs: List<Number?>, const: Number? = 0.0) {
        // setting constant and sense
        this.const = const?.toDouble() ?: 0.0

        // adding variables and their coefficients
        assert(vars.size == coeffs.size)
        for (i in vars.indices) add(vars[i], coeffs[i] ?: 0.0)
    }

    @JvmOverloads
    constructor(vars: List<Var?>, coeffs: DoubleArray, const: Number? = 0.0) {
        // setting constant and sense
        this.const = const?.toDouble() ?: 0.0

        // adding variables and their coefficients
        assert(vars.size == coeffs.size)
        for (i in vars.indices) add(vars[i], coeffs[i])
    }

    @JvmOverloads
    constructor(variable: Var?, coeff: Number? = 1.0, const: Number? = 0.0) {
        this.const = const?.toDouble() ?: 0.0
        if (variable != null)
            this.terms[variable] = coeff?.toDouble() ?: 0.0
    }

    @JvmOverloads
    constructor(coeff: Number?, variable: Var?, const: Number? = 0.0) {
        this.const = const?.toDouble() ?: 0.0
        if (variable != null)
            this.terms[variable] = coeff?.toDouble() ?: 0.0
    }

    constructor(const: Number?) {
        this.const = const?.toDouble() ?: 0.0
    }

    constructor()

    @JvmOverloads
    fun add(iterable: Iterable<Any?>?, mult: Number? = 1.0) {
        if (iterable == null || mult == null || mult == 0.0) return

        val mult = mult.toDouble()
        for (term in iterable) {
            when (term) {
                null -> continue
                is LinExpr -> add(term, mult)
                is Var -> add(term, mult)
                is Number -> add(term.toDouble() * mult)
                else -> throw IllegalArgumentException()
            }
        }
    }

    @JvmOverloads
    fun add(linExpr: LinExpr?, mult: Number? = 1.0) {
        if (linExpr == null || mult == null || mult == 0.0) return;

        val mult = mult.toDouble()
        const += linExpr.const * mult
        for ((v, coeff) in linExpr.terms) {
            add(v, coeff * mult)
        }

    }

    @JvmOverloads
    fun add(variable: Var?, coeff: Number? = 1.0) {
        if (variable == null || coeff == null || coeff == 0.0) return

        val res = coeff.toDouble() + (terms[variable] ?: 0.0)
        if (res != 0.0)
            terms[variable] = res
        else
            terms.remove(variable)
    }

    fun add(const: Number?) {
        this.const += const?.toDouble() ?: 0.0
    }

    fun copy(): LinExpr = LinExpr(this)

    fun divide(const: Number?) {
        val divisor = const?.toDouble() ?: 1.0
        if (divisor == 0.0)
            throw ArithmeticException()
        if (divisor == 1.0)
            return

        this.const /= divisor
        for ((v, coeff) in terms) {
            terms[v] = coeff / divisor
        }
    }

    fun multiply(const: Number?) {
        val multiplier = const?.toDouble() ?: 1.0
        if (multiplier == 0.0) {
            this.const = 0.0
            terms.clear()
        }
        if (multiplier == 1.0)
            return

        this.const *= multiplier
        for ((v, coeff) in terms) {
            terms[v] = coeff * multiplier
        }
    }

    fun sub(iterable: Iterable<Any?>?) = add(iterable, -1.0)
    fun sub(linExpr: LinExpr?) = add(linExpr, -1.0)
    fun sub(variable: Var?) = add(variable, -1.0)
    fun sub(const: Number?) = add((const?.toDouble() ?: 0.0) * -1.0)

    inline infix fun leq(other: LinExpr?) = Constr.leq(this, other)
    inline infix fun leq(other: Var?) = Constr.leq(this, other)
    inline infix fun leq(other: Number?) = Constr.leq(this, other)
    inline infix fun geq(other: LinExpr?) = Constr.geq(this, other)
    inline infix fun geq(other: Var?) = Constr.geq(this, other)
    inline infix fun geq(other: Number?) = Constr.geq(this, other)
    inline infix fun eq(other: LinExpr?) = Constr.eq(this, other)
    inline infix fun eq(other: Var?) = Constr.eq(this, other)
    inline infix fun eq(other: Number?) = Constr.eq(this, other)

    inline infix fun named(name: String) = NamedLinExpr(this, name)

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

    fun addLHS(iterable: Iterable<Any?>?) = add(iterable)
    fun addLHS(linExpr: LinExpr?) = add(linExpr)
    fun addLHS(variable: Var?) = add(variable)
    fun addLHS(const: Number?) = add(const)

    fun addRHS(iterable: Iterable<Any?>?) = sub(iterable)
    fun addRHS(linExpr: LinExpr?) = sub(linExpr)
    fun addRHS(variable: Var?) = sub(variable)
    fun addRHS(const: Number?) = sub(const)

    // endregion addLHS and addRHS aliases

    // region kotlin operators

    operator fun plusAssign(iterable: Iterable<Any?>?) = add(iterable)
    operator fun plusAssign(linExpr: LinExpr?) = add(linExpr)
    operator fun plusAssign(variable: Var?) = add(variable)
    operator fun plusAssign(const: Number?) = add(const)

    operator fun minusAssign(iterable: Iterable<Any?>?) = sub(iterable)
    operator fun minusAssign(linExpr: LinExpr?) = sub(linExpr)
    operator fun minusAssign(variable: Var?) = sub(variable)
    operator fun minusAssign(const: Number?) = sub(const)

    operator fun timesAssign(const: Number?) = multiply(const)
    operator fun divAssign(const: Number?) = divide(const)

    operator fun plus(iterable: Iterable<Any?>?) = copy().apply { add(iterable) }
    operator fun plus(linExpr: LinExpr?) = copy().apply { add(linExpr) }
    operator fun plus(variable: Var?) = copy().apply { add(variable) }
    operator fun plus(const: Number?) = copy().apply { add(const) }
    operator fun unaryPlus() = this

    operator fun minus(iterable: Iterable<Any?>?) = copy().apply { sub(iterable) }
    operator fun minus(linExpr: LinExpr?) = copy().apply { sub(linExpr) }
    operator fun minus(variable: Var?) = copy().apply { sub(variable) }
    operator fun minus(const: Number?) = copy().apply { sub(const) }
    operator fun unaryMinus() = copy().apply { multiply(-1) }

    operator fun times(const: Number?) = copy().apply { multiply(const) }
    operator fun div(const: Number?) = copy().apply { divide(const) }

    // endregion kotlin operators
}

typealias NamedLinExpr = Pair<LinExpr, String>
