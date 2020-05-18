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

        // region LEQ

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: Iterable<Any>, rhs: Iterable<Any>, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: Iterable<Any>, rhs: LinExpr, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: Iterable<Any>, rhs: Var, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: Iterable<Any>, rhs: Number, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: LinExpr, rhs: Iterable<Any>, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: LinExpr, rhs: LinExpr, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: LinExpr, rhs: Var, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: LinExpr, rhs: Number, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: Var, rhs: Iterable<Any>, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: Var, rhs: LinExpr, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: Var, rhs: Var, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: Var, rhs: Number, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: Number, rhs: Iterable<Any>, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: Number, rhs: LinExpr, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        @JvmStatic
        @JvmOverloads
        fun leq(lhs: Number, rhs: Var, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '<' }, name)

        // endregion LEQ

        // region GEQ

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: Iterable<Any>, rhs: Iterable<Any>, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: Iterable<Any>, rhs: LinExpr, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: Iterable<Any>, rhs: Var, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: Iterable<Any>, rhs: Number, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: LinExpr, rhs: Iterable<Any>, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: LinExpr, rhs: LinExpr, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: LinExpr, rhs: Var, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: LinExpr, rhs: Number, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: Var, rhs: Iterable<Any>, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: Var, rhs: LinExpr, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: Var, rhs: Var, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: Var, rhs: Number, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: Number, rhs: Iterable<Any>, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: Number, rhs: LinExpr, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        @JvmStatic
        @JvmOverloads
        fun geq(lhs: Number, rhs: Var, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '>' }, name)

        // endregion LEQ

        // region EQ

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: Iterable<Any>, rhs: Iterable<Any>, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: Iterable<Any>, rhs: LinExpr, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: Iterable<Any>, rhs: Var, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: Iterable<Any>, rhs: Number, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: LinExpr, rhs: Iterable<Any>, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: LinExpr, rhs: LinExpr, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: LinExpr, rhs: Var, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: LinExpr, rhs: Number, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: Var, rhs: Iterable<Any>, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: Var, rhs: LinExpr, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: Var, rhs: Var, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: Var, rhs: Number, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: Number, rhs: Iterable<Any>, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: Number, rhs: LinExpr, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        @JvmStatic
        @JvmOverloads
        fun eq(lhs: Number, rhs: Var, name: String = "") =
            NamedLinExpr(LinExpr(lhs).apply { sub(rhs); sense = '=' }, name)

        // endregion LEQ
    }
}

// region aliases (inline functions)

inline fun leq(lhs: Iterable<Any>, rhs: Iterable<Any>, name: String = "") = Constr.leq(lhs, rhs, name)
inline fun leq(lhs: Iterable<Any>, rhs: LinExpr, name: String = "") = Constr.leq(lhs, rhs, name)
inline fun leq(lhs: Iterable<Any>, rhs: Var, name: String = "") = Constr.leq(lhs, rhs, name)
inline fun leq(lhs: Iterable<Any>, rhs: Number, name: String = "") = Constr.leq(lhs, rhs, name)
inline fun leq(lhs: LinExpr, rhs: Iterable<Any>, name: String = "") = Constr.leq(lhs, rhs, name)
inline fun leq(lhs: LinExpr, rhs: LinExpr, name: String = "") = Constr.leq(lhs, rhs, name)
inline fun leq(lhs: LinExpr, rhs: Var, name: String = "") = Constr.leq(lhs, rhs, name)
inline fun leq(lhs: LinExpr, rhs: Number, name: String = "") = Constr.leq(lhs, rhs, name)
inline fun leq(lhs: Var, rhs: Iterable<Any>, name: String = "") = Constr.leq(lhs, rhs, name)
inline fun leq(lhs: Var, rhs: LinExpr, name: String = "") = Constr.leq(lhs, rhs, name)
inline fun leq(lhs: Var, rhs: Var, name: String = "") = Constr.leq(lhs, rhs, name)
inline fun leq(lhs: Var, rhs: Number, name: String = "") = Constr.leq(lhs, rhs, name)
inline fun leq(lhs: Number, rhs: Iterable<Any>, name: String = "") = Constr.leq(lhs, rhs, name)
inline fun leq(lhs: Number, rhs: LinExpr, name: String = "") = Constr.leq(lhs, rhs, name)
inline fun leq(lhs: Number, rhs: Var, name: String = "") = Constr.leq(lhs, rhs, name)

inline fun geq(lhs: Iterable<Any>, rhs: Iterable<Any>, name: String = "") = Constr.geq(lhs, rhs, name)
inline fun geq(lhs: Iterable<Any>, rhs: LinExpr, name: String = "") = Constr.geq(lhs, rhs, name)
inline fun geq(lhs: Iterable<Any>, rhs: Var, name: String = "") = Constr.geq(lhs, rhs, name)
inline fun geq(lhs: Iterable<Any>, rhs: Number, name: String = "") = Constr.geq(lhs, rhs, name)
inline fun geq(lhs: LinExpr, rhs: Iterable<Any>, name: String = "") = Constr.geq(lhs, rhs, name)
inline fun geq(lhs: LinExpr, rhs: LinExpr, name: String = "") = Constr.geq(lhs, rhs, name)
inline fun geq(lhs: LinExpr, rhs: Var, name: String = "") = Constr.geq(lhs, rhs, name)
inline fun geq(lhs: LinExpr, rhs: Number, name: String = "") = Constr.geq(lhs, rhs, name)
inline fun geq(lhs: Var, rhs: Iterable<Any>, name: String = "") = Constr.geq(lhs, rhs, name)
inline fun geq(lhs: Var, rhs: LinExpr, name: String = "") = Constr.geq(lhs, rhs, name)
inline fun geq(lhs: Var, rhs: Var, name: String = "") = Constr.geq(lhs, rhs, name)
inline fun geq(lhs: Var, rhs: Number, name: String = "") = Constr.geq(lhs, rhs, name)
inline fun geq(lhs: Number, rhs: Iterable<Any>, name: String = "") = Constr.geq(lhs, rhs, name)
inline fun geq(lhs: Number, rhs: LinExpr, name: String = "") = Constr.geq(lhs, rhs, name)
inline fun geq(lhs: Number, rhs: Var, name: String = "") = Constr.geq(lhs, rhs, name)

inline fun eq(lhs: Iterable<Any>, rhs: Iterable<Any>, name: String = "") = Constr.eq(lhs, rhs, name)
inline fun eq(lhs: Iterable<Any>, rhs: LinExpr, name: String = "") = Constr.eq(lhs, rhs, name)
inline fun eq(lhs: Iterable<Any>, rhs: Var, name: String = "") = Constr.eq(lhs, rhs, name)
inline fun eq(lhs: Iterable<Any>, rhs: Number, name: String = "") = Constr.eq(lhs, rhs, name)
inline fun eq(lhs: LinExpr, rhs: Iterable<Any>, name: String = "") = Constr.eq(lhs, rhs, name)
inline fun eq(lhs: LinExpr, rhs: LinExpr, name: String = "") = Constr.eq(lhs, rhs, name)
inline fun eq(lhs: LinExpr, rhs: Var, name: String = "") = Constr.eq(lhs, rhs, name)
inline fun eq(lhs: LinExpr, rhs: Number, name: String = "") = Constr.eq(lhs, rhs, name)
inline fun eq(lhs: Var, rhs: Iterable<Any>, name: String = "") = Constr.eq(lhs, rhs, name)
inline fun eq(lhs: Var, rhs: LinExpr, name: String = "") = Constr.eq(lhs, rhs, name)
inline fun eq(lhs: Var, rhs: Var, name: String = "") = Constr.eq(lhs, rhs, name)
inline fun eq(lhs: Var, rhs: Number, name: String = "") = Constr.eq(lhs, rhs, name)
inline fun eq(lhs: Number, rhs: Iterable<Any>, name: String = "") = Constr.eq(lhs, rhs, name)
inline fun eq(lhs: Number, rhs: LinExpr, name: String = "") = Constr.eq(lhs, rhs, name)
inline fun eq(lhs: Number, rhs: Var, name: String = "") = Constr.eq(lhs, rhs, name)

// endregion aliases (inline functions)
