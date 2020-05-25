@file:Suppress("NOTHING_TO_INLINE")

package mip

class Constr internal constructor(val model: Model, var idx: Int) : Comparable<Constr> {

    // region properties

    var expr: LinExpr
        get() = model.solver.getConstrExpr(idx)
        set(value) = model.solver.setConstrExpr(idx, value)

    var name: String
        get() = model.solver.getConstrName(idx)
        set(value) = model.solver.setConstrName(idx, value)

    val pi get() = model.solver.getConstrPi(idx)

    var rhs: Number
        get() = model.solver.getConstrRHS(idx)
        set(value) = model.solver.setConstrRHS(idx, value.toDouble())

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
        fun le(lhs: Iterable<Any?>, rhs: Iterable<Any?>) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun le(lhs: Iterable<Any?>, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun le(lhs: Iterable<Any?>, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun le(lhs: Iterable<Any?>, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun le(lhs: LinExpr?, rhs: Iterable<Any?>) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun le(lhs: LinExpr?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun le(lhs: LinExpr?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun le(lhs: LinExpr?, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun le(lhs: Var?, rhs: Iterable<Any?>) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun le(lhs: Var?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun le(lhs: Var?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun le(lhs: Var?, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun le(lhs: Number, rhs: Iterable<Any?>) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun le(lhs: Number, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun le(lhs: Number, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        // endregion leq

        // region geq

        @JvmStatic
        fun ge(lhs: Iterable<Any?>, rhs: Iterable<Any?>) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun ge(lhs: Iterable<Any?>, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun ge(lhs: Iterable<Any?>, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun ge(lhs: Iterable<Any?>, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun ge(lhs: LinExpr?, rhs: Iterable<Any?>) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun ge(lhs: LinExpr?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun ge(lhs: LinExpr?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun ge(lhs: LinExpr?, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun ge(lhs: Var?, rhs: Iterable<Any?>) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun ge(lhs: Var?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun ge(lhs: Var?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun ge(lhs: Var?, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun ge(lhs: Number, rhs: Iterable<Any?>) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun ge(lhs: Number, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun ge(lhs: Number, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        // endregion geq

        // region eq

        @JvmStatic
        fun eq(lhs: Iterable<Any?>, rhs: Iterable<Any?>) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Iterable<Any?>, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Iterable<Any?>, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Iterable<Any?>, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: LinExpr?, rhs: Iterable<Any?>) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: LinExpr?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: LinExpr?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: LinExpr?, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Var?, rhs: Iterable<Any?>) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Var?, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Var?, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Var?, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Number, rhs: Iterable<Any?>) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Number, rhs: LinExpr?) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Number, rhs: Var?) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        // endregion eq
    }
}

// region aliases (inline functions)

inline fun le(lhs: Iterable<Any?>, rhs: Iterable<Any?>) = Constr.le(lhs, rhs)
inline fun le(lhs: Iterable<Any?>, rhs: LinExpr?) = Constr.le(lhs, rhs)
inline fun le(lhs: Iterable<Any?>, rhs: Var?) = Constr.le(lhs, rhs)
inline fun le(lhs: Iterable<Any?>, rhs: Number) = Constr.le(lhs, rhs)
inline fun le(lhs: LinExpr?, rhs: Iterable<Any?>) = Constr.le(lhs, rhs)
inline fun le(lhs: LinExpr?, rhs: LinExpr?) = Constr.le(lhs, rhs)
inline fun le(lhs: LinExpr?, rhs: Var?) = Constr.le(lhs, rhs)
inline fun le(lhs: LinExpr?, rhs: Number) = Constr.le(lhs, rhs)
inline fun le(lhs: Var?, rhs: Iterable<Any?>) = Constr.le(lhs, rhs)
inline fun le(lhs: Var?, rhs: LinExpr?) = Constr.le(lhs, rhs)
inline fun le(lhs: Var?, rhs: Var?) = Constr.le(lhs, rhs)
inline fun le(lhs: Var?, rhs: Number) = Constr.le(lhs, rhs)
inline fun le(lhs: Number, rhs: Iterable<Any?>) = Constr.le(lhs, rhs)
inline fun le(lhs: Number, rhs: LinExpr?) = Constr.le(lhs, rhs)
inline fun le(lhs: Number, rhs: Var?) = Constr.le(lhs, rhs)
inline fun ge(lhs: Iterable<Any?>, rhs: Iterable<Any>) = Constr.ge(lhs, rhs)
inline fun ge(lhs: Iterable<Any?>, rhs: LinExpr) = Constr.ge(lhs, rhs)
inline fun ge(lhs: Iterable<Any?>, rhs: Var) = Constr.ge(lhs, rhs)
inline fun ge(lhs: Iterable<Any?>, rhs: Number) = Constr.ge(lhs, rhs)
inline fun ge(lhs: LinExpr?, rhs: Iterable<Any?>) = Constr.ge(lhs, rhs)
inline fun ge(lhs: LinExpr?, rhs: LinExpr?) = Constr.ge(lhs, rhs)
inline fun ge(lhs: LinExpr?, rhs: Var?) = Constr.ge(lhs, rhs)
inline fun ge(lhs: LinExpr?, rhs: Number) = Constr.ge(lhs, rhs)
inline fun ge(lhs: Var?, rhs: Iterable<Any?>) = Constr.ge(lhs, rhs)
inline fun ge(lhs: Var?, rhs: LinExpr?) = Constr.ge(lhs, rhs)
inline fun ge(lhs: Var?, rhs: Var?) = Constr.ge(lhs, rhs)
inline fun ge(lhs: Var?, rhs: Number) = Constr.ge(lhs, rhs)
inline fun ge(lhs: Number, rhs: Iterable<Any?>) = Constr.ge(lhs, rhs)
inline fun ge(lhs: Number, rhs: LinExpr?) = Constr.ge(lhs, rhs)
inline fun ge(lhs: Number, rhs: Var?) = Constr.ge(lhs, rhs)
inline fun eq(lhs: Iterable<Any?>, rhs: Iterable<Any?>) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Iterable<Any?>, rhs: LinExpr?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Iterable<Any?>, rhs: Var?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Iterable<Any?>, rhs: Number) = Constr.eq(lhs, rhs)
inline fun eq(lhs: LinExpr?, rhs: Iterable<Any?>) = Constr.eq(lhs, rhs)
inline fun eq(lhs: LinExpr?, rhs: LinExpr?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: LinExpr?, rhs: Var?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: LinExpr?, rhs: Number) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Var?, rhs: Iterable<Any?>) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Var?, rhs: LinExpr?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Var?, rhs: Var?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Var?, rhs: Number) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Number, rhs: Iterable<Any?>) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Number, rhs: LinExpr?) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Number, rhs: Var?) = Constr.eq(lhs, rhs)

// endregion aliases (inline functions)
