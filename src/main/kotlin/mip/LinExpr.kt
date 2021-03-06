package mip

import java.util.*
import java.util.function.Consumer
import kotlin.collections.LinkedHashMap
import kotlin.math.abs
import kotlin.math.max

@Suppress("NOTHING_TO_INLINE")
/**
 * This class represents a linear expression, which may be affine or not. Basic operations are
 * implemented and a number of overloaded methods are included as to facilitate using the class.
 *
 * @author Túlio Toffolo
 */
class LinExpr {

    var const = 0.0
    var sense = ""
    val terms = LinkedHashMap<Var, Double>()

    /**
     * Returns if this linear expression is affine, i.e. if it has no sense.
     */
    val isAffine get() = sense == "" || sense == MINIMIZE || sense == MAXIMIZE

    /**
     * Returns the number of variables within the linear expression.
     */
    val size get() = terms.size

    /**
     * Amount by which the current solution violates this non-affine linear expression.
     *
     * If a solution is available, than this property indicates how much the current solution
     * violates this linear expression.
     */
    val violation: Double
        get() {
            if (isAffine) return 0.0

            val lhs = terms.entries.sumByDouble { (variable, coeff) -> coeff * variable.x }
            val rhs = -const
            when (sense) {
                EQ -> return abs(lhs - rhs)
                LEQ -> return max(lhs - rhs, 0.0)
                GEQ -> return max(rhs - lhs, 0.0)
            }
            return 0.0
        }

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

        for (term in iterable) {
            when (term) {
                null -> continue
                is LinExpr -> add(term, mult.toDouble())
                is Var -> add(term, mult.toDouble())
                is Number -> add(term.toDouble() * mult.toDouble())
                else -> throw IllegalArgumentException()
            }
        }
    }

    @JvmOverloads
    fun add(linExpr: LinExpr?, mult: Number? = 1.0) {
        if (linExpr == null || mult == null || mult == 0.0) return;

        val multiplier = mult.toDouble()
        const += linExpr.const * multiplier
        for ((v, coeff) in linExpr.terms) {
            add(v, coeff * multiplier)
        }

    }

    fun add(variable: Var?, coeff: Number?) {
        if (variable == null || coeff == null || coeff == 0.0) return

        val res = coeff.toDouble() + (terms[variable] ?: 0.0)
        if (res != 0.0)
            terms[variable] = res
        else
            terms.remove(variable)
    }

    fun add(variable: Var?) {
        if (variable == null) return

        val res = (terms[variable] ?: 0.0) + 1.0
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

    inline operator fun get(v: Var): Double = terms.getOrDefault(v, 0.0)

    inline operator fun plusAssign(iterable: Iterable<Any?>?) = add(iterable)
    inline operator fun plusAssign(linExpr: LinExpr?) = add(linExpr)
    inline operator fun plusAssign(variable: Var?) = add(variable)
    inline operator fun plusAssign(const: Number?) = add(const)

    inline operator fun minusAssign(iterable: Iterable<Any?>?) = sub(iterable)
    inline operator fun minusAssign(linExpr: LinExpr?) = sub(linExpr)
    inline operator fun minusAssign(variable: Var?) = sub(variable)
    inline operator fun minusAssign(const: Number?) = sub(const)

    inline operator fun timesAssign(const: Number?) = multiply(const)
    inline operator fun divAssign(const: Number?) = divide(const)

    inline operator fun plus(iterable: Iterable<Any?>?) = copy().apply { add(iterable) }
    inline operator fun plus(linExpr: LinExpr?) = copy().apply { add(linExpr) }
    inline operator fun plus(variable: Var?) = copy().apply { add(variable) }
    inline operator fun plus(const: Number?) = copy().apply { add(const) }
    inline operator fun unaryPlus() = this

    inline operator fun minus(iterable: Iterable<Any?>?) = copy().apply { sub(iterable) }
    inline operator fun minus(linExpr: LinExpr?) = copy().apply { sub(linExpr) }
    inline operator fun minus(variable: Var?) = copy().apply { sub(variable) }
    inline operator fun minus(const: Number?) = copy().apply { sub(const) }
    inline operator fun unaryMinus() = copy().apply { multiply(-1) }

    inline operator fun times(const: Number?) = copy().apply { multiply(const) }
    inline operator fun div(const: Number?) = copy().apply { divide(const) }

    // endregion kotlin operators
}

typealias NamedLinExpr = Pair<LinExpr, String>
