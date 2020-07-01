package mip

/**
 * This class represents a Column, i.e. a set of non-null row coefficients of a variable. This
 * class is particularly useful for creating variables after all constraints were created. A
 * typical example is the implementation of column generation approaches.
 *
 * @author Túlio Toffolo
 */
class Column{

    var obj = 0.0
    val terms = HashMap<Constr, Double>()

    val size: Int get() = terms.size

    constructor(col: Column?) {
        if (col != null) {
            this.obj = col.obj
            this.terms.putAll(col.terms)
        }
    }

    @JvmOverloads
    constructor(constrs: List<Constr?>, coeffs: List<Number>, obj: Number = 0.0) {
        // setting constant and sense
        this.obj = obj.toDouble()

        // adding variables and their coefficients
        assert(constrs.size == coeffs.size)
        for (i in constrs.indices) add(constrs[i], coeffs[i])
    }

    @JvmOverloads
    constructor(constrs: List<Constr?>, coeffs: DoubleArray, obj: Number = 0.0) {
        // setting constant and sense
        this.obj = obj.toDouble()

        // adding variables and their coefficients
        assert(constrs.size == coeffs.size)
        for (i in constrs.indices) add(constrs[i], coeffs[i])
    }

    constructor()

    companion object {
        @JvmField
        val EMPTY = Column(emptyList(), emptyList())
    }

    @JvmOverloads
    fun add(constr: Constr?, coeff: Number = 1.0) {
        if (coeff == 0.0) return

        if (constr != null) {
            val res = coeff.toDouble() + (terms[constr] ?: 0.0)
            if (res != 0.0)
                terms[constr] = res
            else
                terms.remove(constr)
        }
    }

    fun add(obj: Number) {
        this.obj += obj.toDouble()
    }

    fun copy() = Column(this)

    fun divide(const: Number) {
        val divisor = const.toDouble();
        if (divisor == 0.0)
            throw ArithmeticException()
        if (divisor == 1.0)
            return

        this.obj /= divisor;
        for ((v, coeff) in terms) {
            terms[v] = coeff / divisor;
        }
    }

    fun multiply(const: Number) {
        val multiplier = const.toDouble();
        if (multiplier == 0.0) {
            this.obj = 0.0
            terms.clear()
        }
        if (multiplier == 1.0)
            return

        this.obj *= multiplier;
        for ((v, coeff) in terms) {
            terms[v] = coeff * multiplier;
        }
    }

    fun sub(constr: Constr?) = add(constr, -1.0)
    fun sub(const: Number) = add(const.toDouble() * -1.0)

    // region kotlin operators

    operator fun plusAssign(constr: Constr?) = add(constr)
    operator fun plusAssign(const: Number) = add(const)

    operator fun minusAssign(constr: Constr?) = sub(constr)
    operator fun minusAssign(const: Number) = sub(const)

    operator fun timesAssign(const: Number) = multiply(const)
    operator fun divAssign(const: Number) = divide(const)

    operator fun plus(constr: Constr?) = copy().apply { add(constr) }
    operator fun plus(const: Number) = copy().apply { add(const) }
    operator fun unaryPlus() = this

    operator fun minus(constr: Constr?) = copy().apply { sub(constr) }
    operator fun minus(const: Number) = copy().apply { sub(const) }
    operator fun unaryMinus() = copy().apply { multiply(-1) }

    operator fun times(const: Number) = copy().apply { multiply(const) }
    operator fun div(const: Number) = copy().apply { divide(const) }

    // endregion kotlin operators
}

