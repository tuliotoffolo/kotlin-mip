@file:Suppress("NOTHING_TO_INLINE")

package mip

class Constr internal constructor(val model: Model, var idx: Int) : Comparable<Constr> {

    // region properties

    /**
     * A linear expression representing this constraint. Important: note that the getter of [expr]
     * returns a copy of the constraint's actual linear expression.
     */
    var expr: LinExpr
        get() = model.solver.getConstrExpr(idx)
        set(value) = model.solver.setConstrExpr(idx, value)

    /**
     * Constraint's name
     */
    var name: String
        get() = model.solver.getConstrName(idx)
        set(value) = model.solver.setConstrName(idx, value)

    /**
     * Returns the value of the dual variable associated with the constraint. Note that a solution
     * must be available or otherwise the [pi] getter will raise an exception.
     */
    val pi get() = model.solver.getConstrPi(idx)

    /**
     * Constant associated with the constraint's linear expression.
     */
    var rhs: Double
        get() = model.solver.getConstrRHS(idx)
        set(value) = model.solver.setConstrRHS(idx, value.toDouble())

    /**
     * Returns the constraint's slack value. Note that a solution must be available or otherwise
     * the [slack] getter will raise an exception.
     */
    val slack get() = model.solver.getConstrSlack(idx)

    // endregion properties

    override fun compareTo(other: Constr): Int = idx.compareTo(other.idx)

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

    // override fun toString(): String {
    //     // collecting data from solver
    //     val name = this.name
    //     val expr = this.expr
    //
    //     if (name.isNotBlank())
    //         return "$name: $expr"
    //     return "constr($idx): $expr"
    // }

    // endregion override functions

    companion object {

        // region leq

        @JvmStatic
        fun leq(lhs: Iterable<Any?>?, rhs: Iterable<Any?>?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        @JvmStatic
        fun leq(lhs: Iterable<Any?>?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        @JvmStatic
        fun leq(lhs: Iterable<Any?>?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        @JvmStatic
        fun leq(lhs: Iterable<Any?>?, rhs: Number?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        @JvmStatic
        fun leq(lhs: LinExpr?, rhs: Iterable<Any?>?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        @JvmStatic
        fun leq(lhs: LinExpr?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        @JvmStatic
        fun leq(lhs: LinExpr?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        @JvmStatic
        fun leq(lhs: LinExpr?, rhs: Number?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        @JvmStatic
        fun leq(lhs: Var?, rhs: Iterable<Any?>?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        @JvmStatic
        fun leq(lhs: Var?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        @JvmStatic
        fun leq(lhs: Var?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        @JvmStatic
        fun leq(lhs: Var?, rhs: Number?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        @JvmStatic
        fun leq(lhs: Number?, rhs: Iterable<Any?>?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        @JvmStatic
        fun leq(lhs: Number?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        @JvmStatic
        fun leq(lhs: Number?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = "<=" }

        // endregion leq

        // region geq

        @JvmStatic
        fun geq(lhs: Iterable<Any?>?, rhs: Iterable<Any?>?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        @JvmStatic
        fun geq(lhs: Iterable<Any?>?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        @JvmStatic
        fun geq(lhs: Iterable<Any?>?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        @JvmStatic
        fun geq(lhs: Iterable<Any?>?, rhs: Number?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        @JvmStatic
        fun geq(lhs: LinExpr?, rhs: Iterable<Any?>?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        @JvmStatic
        fun geq(lhs: LinExpr?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        @JvmStatic
        fun geq(lhs: LinExpr?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        @JvmStatic
        fun geq(lhs: LinExpr?, rhs: Number?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        @JvmStatic
        fun geq(lhs: Var?, rhs: Iterable<Any?>?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        @JvmStatic
        fun geq(lhs: Var?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        @JvmStatic
        fun geq(lhs: Var?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        @JvmStatic
        fun geq(lhs: Var?, rhs: Number?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        @JvmStatic
        fun geq(lhs: Number?, rhs: Iterable<Any?>?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        @JvmStatic
        fun geq(lhs: Number?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        @JvmStatic
        fun geq(lhs: Number?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = ">=" }

        // endregion geq

        // region eq

        @JvmStatic
        fun eq(lhs: Iterable<Any?>?, rhs: Iterable<Any?>?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        @JvmStatic
        fun eq(lhs: Iterable<Any?>?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        @JvmStatic
        fun eq(lhs: Iterable<Any?>?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        @JvmStatic
        fun eq(lhs: Iterable<Any?>?, rhs: Number?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        @JvmStatic
        fun eq(lhs: LinExpr?, rhs: Iterable<Any?>?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        @JvmStatic
        fun eq(lhs: LinExpr?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        @JvmStatic
        fun eq(lhs: LinExpr?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        @JvmStatic
        fun eq(lhs: LinExpr?, rhs: Number?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        @JvmStatic
        fun eq(lhs: Var?, rhs: Iterable<Any?>?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        @JvmStatic
        fun eq(lhs: Var?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        @JvmStatic
        fun eq(lhs: Var?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        @JvmStatic
        fun eq(lhs: Var?, rhs: Number?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        @JvmStatic
        fun eq(lhs: Number?, rhs: Iterable<Any?>?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        @JvmStatic
        fun eq(lhs: Number?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        @JvmStatic
        fun eq(lhs: Number?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = "==" }

        // endregion eq
    }
}

// region aliases (inline functions)

inline fun leq(lhs: Iterable<Any?>?, rhs: Iterable<Any?>?) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Iterable<Any?>?, rhs: LinExpr?) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Iterable<Any?>?, rhs: Var?) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Iterable<Any?>?, rhs: Number?) = Constr.leq(lhs, rhs)
inline fun leq(lhs: LinExpr?, rhs: Iterable<Any?>?) = Constr.leq(lhs, rhs)
inline fun leq(lhs: LinExpr?, rhs: LinExpr?) = Constr.leq(lhs, rhs)
inline fun leq(lhs: LinExpr?, rhs: Var?) = Constr.leq(lhs, rhs)
inline fun leq(lhs: LinExpr?, rhs: Number?) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Var?, rhs: Iterable<Any?>?) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Var?, rhs: LinExpr?) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Var?, rhs: Var?) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Var?, rhs: Number?) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Number?, rhs: Iterable<Any?>?) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Number?, rhs: LinExpr?) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Number?, rhs: Var?) = Constr.leq(lhs, rhs)

inline fun geq(lhs: Iterable<Any?>?, rhs: Iterable<Any?>?) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Iterable<Any?>?, rhs: LinExpr?) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Iterable<Any?>?, rhs: Var?) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Iterable<Any?>?, rhs: Number?) = Constr.geq(lhs, rhs)
inline fun geq(lhs: LinExpr?, rhs: Iterable<Any?>?) = Constr.geq(lhs, rhs)
inline fun geq(lhs: LinExpr?, rhs: LinExpr?) = Constr.geq(lhs, rhs)
inline fun geq(lhs: LinExpr?, rhs: Var?) = Constr.geq(lhs, rhs)
inline fun geq(lhs: LinExpr?, rhs: Number?) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Var?, rhs: Iterable<Any?>?) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Var?, rhs: LinExpr?) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Var?, rhs: Var?) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Var?, rhs: Number?) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Number?, rhs: Iterable<Any?>?) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Number?, rhs: LinExpr?) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Number?, rhs: Var?) = Constr.geq(lhs, rhs)

inline fun eq(lhs: Iterable<Any?>?, rhs: Iterable<Any?>?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Iterable<Any?>?, rhs: LinExpr?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Iterable<Any?>?, rhs: Var?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Iterable<Any?>?, rhs: Number?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: LinExpr?, rhs: Iterable<Any?>?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: LinExpr?, rhs: LinExpr?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: LinExpr?, rhs: Var?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: LinExpr?, rhs: Number?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Var?, rhs: Iterable<Any?>?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Var?, rhs: LinExpr?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Var?, rhs: Var?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Var?, rhs: Number?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Number?, rhs: Iterable<Any?>?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Number?, rhs: LinExpr?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Number?, rhs: Var?) = Constr.eq(lhs, rhs)

// endregion aliases (inline functions)
