package br.ufop.jmip.entities

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
}

fun leq(lhs: LinExpr, rhs: LinExpr) = (lhs - rhs).apply { sense='<' }
fun leq(lhs: LinExpr, rhs: LinSum) = (lhs - rhs).apply { sense='<' }
fun leq(lhs: LinExpr, rhs: LinTerm) = (lhs - rhs).apply { sense='<' }
fun leq(lhs: LinExpr, rhs: Var) = (lhs - rhs).apply { sense='<' }
fun leq(lhs: LinExpr, rhs: Number) = (lhs - rhs).apply { sense='<' }

fun eq(lhs: LinExpr, rhs: LinExpr) = (lhs - rhs).apply { sense='=' }
fun eq(lhs: LinExpr, rhs: LinSum) = (lhs - rhs).apply { sense='=' }
fun eq(lhs: LinExpr, rhs: LinTerm) = (lhs - rhs).apply { sense='=' }
fun eq(lhs: LinExpr, rhs: Var) = (lhs - rhs).apply { sense='=' }
fun eq(lhs: LinExpr, rhs: Number) = (lhs - rhs).apply { sense='=' }

fun geq(lhs: LinExpr, rhs: LinExpr) = (lhs - rhs).apply { sense='>' }
fun geq(lhs: LinExpr, rhs: LinSum) = (lhs - rhs).apply { sense='>' }
fun geq(lhs: LinExpr, rhs: LinTerm) = (lhs - rhs).apply { sense='>' }
fun geq(lhs: LinExpr, rhs: Var) = (lhs - rhs).apply { sense='>' }
fun geq(lhs: LinExpr, rhs: Number) = (lhs - rhs).apply { sense='>' }
