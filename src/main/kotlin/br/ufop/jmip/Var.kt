@file:Suppress("NOTHING_TO_INLINE")

package br.ufop.jmip

class Var internal constructor(val model: Model, var idx: Int) {

    // region properties

    var column: Column
        get() = model.solver.getVarColumn(idx)
        set(value) = model.solver.setVarColumn(idx, value)

    var lb: Number
        get() = model.solver.getVarLB(idx)
        set(value) = model.solver.setVarLB(idx, value.toDouble())

    var name: String
        get() = model.solver.getVarName(idx)
        set(value) = model.solver.setVarName(idx, value)

    var obj: Number
        get() = model.solver.getVarObj(idx)
        set(value) = model.solver.setVarObj(idx, value.toDouble())

    val rc get() = model.solver.getVarRC(idx)

    var type: VarType
        get() = model.solver.getVarType(idx)
        set(value) = model.solver.setVarType(idx, value)

    var ub: Number
        get() = model.solver.getVarUB(idx)
        set(value) = model.solver.setVarUB(idx, value.toDouble())

    val x get() = model.solver.getVarX(idx)

    // endregion properties

    @JvmOverloads
    fun toLinExpr(coeff: Number = 1.0) = LinExpr(this, coeff)

    fun xi(i: Int) = model.solver.getVarXi(idx, i)

    operator fun compareTo(other: Var) = idx.compareTo(other.idx)
    override fun hashCode() = idx
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Var
        if (model != other.model) return false
        if (idx != other.idx) return false

        return true
    }
    override fun toString() = name

    inline infix fun leq(other: LinExpr) = Constr.leq(this, other)
    inline infix fun leq(other: Var) = Constr.leq(this, other)
    inline infix fun leq(other: Number) = Constr.leq(this, other)
    inline infix fun geq(other: LinExpr) = Constr.geq(this, other)
    inline infix fun geq(other: Var) = Constr.geq(this, other)
    inline infix fun geq(other: Number) = Constr.geq(this, other)
    inline infix fun eq(other: LinExpr) = Constr.eq(this, other)
    inline infix fun eq(other: Var) = Constr.eq(this, other)
    inline infix fun eq(other: Number) = Constr.eq(this, other)

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

    operator fun times(coeff: Number) = LinExpr(this, coeff)
    operator fun div(coeff: Number) = LinExpr(this, 1.0 / coeff.toDouble())

    // endregion kotlin operators
}
