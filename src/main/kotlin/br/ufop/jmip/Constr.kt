@file:Suppress("NOTHING_TO_INLINE")

package br.ufop.jmip

class Constr internal constructor(val model: Model, var idx: Int) {

    // region properties

    var expr: LinExpr
        get() = model.solver.getConstrExpr(idx)
        set(value) = model.solver.setConstrExpr(idx, value)

    var name: String
        get() = model.solver.getVarName(idx)
        set(value) = model.solver.setVarName(idx, value)

    val pi get() = model.solver.getConstrPi(idx)

    var rhs: Number
        get() = model.solver.getConstrRHS(idx)
        set(value) = model.solver.setConstrRHS(idx, value.toDouble())

    val slack get() = model.solver.getConstrSlack(idx)

    // endregion properties

    // region override functions

    override fun hashCode() = idx

    override fun toString(): String {
        // collecting data from solver
        val name = this.name
        val expr = this.expr

        if (name.isNotBlank())
            return "$name: $expr"
        return "constr($idx): $expr"
    }

    // endregion override functions

    companion object {

        // region leq

        @JvmStatic
        fun leq(lhs: Iterable<Any>, rhs: Iterable<Any>) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun leq(lhs: Iterable<Any>, rhs: LinExpr) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun leq(lhs: Iterable<Any>, rhs: Var) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun leq(lhs: Iterable<Any>, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun leq(lhs: LinExpr, rhs: Iterable<Any>) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun leq(lhs: LinExpr, rhs: LinExpr) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun leq(lhs: LinExpr, rhs: Var) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun leq(lhs: LinExpr, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun leq(lhs: Var, rhs: Iterable<Any>) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun leq(lhs: Var, rhs: LinExpr) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun leq(lhs: Var, rhs: Var) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun leq(lhs: Var, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun leq(lhs: Number, rhs: Iterable<Any>) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun leq(lhs: Number, rhs: LinExpr) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        @JvmStatic
        fun leq(lhs: Number, rhs: Var) = LinExpr(lhs).apply { sub(rhs); sense = '<' }

        // endregion leq

        // region geq

        @JvmStatic
        fun geq(lhs: Iterable<Any>, rhs: Iterable<Any>) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun geq(lhs: Iterable<Any>, rhs: LinExpr) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun geq(lhs: Iterable<Any>, rhs: Var) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun geq(lhs: Iterable<Any>, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun geq(lhs: LinExpr, rhs: Iterable<Any>) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun geq(lhs: LinExpr, rhs: LinExpr) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun geq(lhs: LinExpr, rhs: Var) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun geq(lhs: LinExpr, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun geq(lhs: Var, rhs: Iterable<Any>) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun geq(lhs: Var, rhs: LinExpr) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun geq(lhs: Var, rhs: Var) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun geq(lhs: Var, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun geq(lhs: Number, rhs: Iterable<Any>) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun geq(lhs: Number, rhs: LinExpr) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        @JvmStatic
        fun geq(lhs: Number, rhs: Var) = LinExpr(lhs).apply { sub(rhs); sense = '>' }

        // endregion geq

        // region eq

        @JvmStatic
        fun eq(lhs: Iterable<Any>, rhs: Iterable<Any>) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Iterable<Any>, rhs: LinExpr) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Iterable<Any>, rhs: Var) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Iterable<Any>, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: LinExpr, rhs: Iterable<Any>) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: LinExpr, rhs: LinExpr) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: LinExpr, rhs: Var) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: LinExpr, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Var, rhs: Iterable<Any>) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Var, rhs: LinExpr) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Var, rhs: Var) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Var, rhs: Number) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Number, rhs: Iterable<Any>) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Number, rhs: LinExpr) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        @JvmStatic
        fun eq(lhs: Number, rhs: Var) = LinExpr(lhs).apply { sub(rhs); sense = '=' }

        // endregion eq
    }
}

// region aliases (inline functions)

inline fun leq(lhs: Iterable<Any>, rhs: Iterable<Any>) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Iterable<Any>, rhs: LinExpr) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Iterable<Any>, rhs: Var) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Iterable<Any>, rhs: Number) = Constr.leq(lhs, rhs)
inline fun leq(lhs: LinExpr, rhs: Iterable<Any>) = Constr.leq(lhs, rhs)
inline fun leq(lhs: LinExpr, rhs: LinExpr) = Constr.leq(lhs, rhs)
inline fun leq(lhs: LinExpr, rhs: Var) = Constr.leq(lhs, rhs)
inline fun leq(lhs: LinExpr, rhs: Number) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Var, rhs: Iterable<Any>) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Var, rhs: LinExpr) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Var, rhs: Var) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Var, rhs: Number) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Number, rhs: Iterable<Any>) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Number, rhs: LinExpr) = Constr.leq(lhs, rhs)
inline fun leq(lhs: Number, rhs: Var) = Constr.leq(lhs, rhs)
inline fun geq(lhs: Iterable<Any>, rhs: Iterable<Any>) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Iterable<Any>, rhs: LinExpr) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Iterable<Any>, rhs: Var) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Iterable<Any>, rhs: Number) = Constr.geq(lhs, rhs)
inline fun geq(lhs: LinExpr, rhs: Iterable<Any>) = Constr.geq(lhs, rhs)
inline fun geq(lhs: LinExpr, rhs: LinExpr) = Constr.geq(lhs, rhs)
inline fun geq(lhs: LinExpr, rhs: Var) = Constr.geq(lhs, rhs)
inline fun geq(lhs: LinExpr, rhs: Number) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Var, rhs: Iterable<Any>) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Var, rhs: LinExpr) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Var, rhs: Var) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Var, rhs: Number) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Number, rhs: Iterable<Any>) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Number, rhs: LinExpr) = Constr.geq(lhs, rhs)
inline fun geq(lhs: Number, rhs: Var) = Constr.geq(lhs, rhs)
inline fun eq(lhs: Iterable<Any>, rhs: Iterable<Any>) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Iterable<Any>, rhs: LinExpr) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Iterable<Any>, rhs: Var) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Iterable<Any>, rhs: Number) = Constr.eq(lhs, rhs)
inline fun eq(lhs: LinExpr, rhs: Iterable<Any>) = Constr.eq(lhs, rhs)
inline fun eq(lhs: LinExpr, rhs: LinExpr) = Constr.eq(lhs, rhs)
inline fun eq(lhs: LinExpr, rhs: Var) = Constr.eq(lhs, rhs)
inline fun eq(lhs: LinExpr, rhs: Number) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Var, rhs: Iterable<Any>) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Var, rhs: LinExpr) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Var, rhs: Var) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Var, rhs: Number) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Number, rhs: Iterable<Any>) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Number, rhs: LinExpr) = Constr.eq(lhs, rhs)
inline fun eq(lhs: Number, rhs: Var) = Constr.eq(lhs, rhs)

// endregion aliases (inline functions)
