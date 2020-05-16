package br.ufop.jmip.entities

abstract class Solver(val model: Model, var name: String, sense: String = MINIMIZE) {
    var sense = sense
        set(value) {
            if (value != MINIMIZE && value != MAXIMIZE)
                throw IllegalArgumentException()
            field = value
        }

    abstract fun addConstr(): Constr
    abstract fun addVar(): Var

    abstract fun get(param: String): Any?
    abstract fun set(param: String, value: Any?): Unit

    // region variable getters and setters

    abstract fun getVarColumn(idx: Int): Column
    abstract fun setVarColumn(idx: Int, value: Column)

    abstract fun getVarIdx(name: String): Int

    abstract fun getVarLB(idx: Int): Double
    abstract fun setVarLB(idx: Int, value: Double)

    abstract fun getVarName(idx: Int): String
    abstract fun setVarName(idx: Int, value: String)

    abstract fun getVarObj(idx: Int): Double
    abstract fun setVarObj(idx: Int, value: Double)

    abstract fun getVarRC(idx: Int): Double

    abstract fun getVarType(idx: Int): VarType
    abstract fun setVarType(idx: Int, value: VarType)

    abstract fun getVarUB(idx: Int): Double
    abstract fun setVarUB(idx: Int, value: Double)

    abstract fun getVarX(idx: Int): Double

    abstract fun getVarXi(idx: Int, i: Int): Double

    // endregion variable getters and setters

    // region constraints getters and setters

    abstract fun getConstrExpr(idx: Int): LinExpr
    abstract fun setConstrExpr(idx: Int, value: LinExpr)

    abstract fun getConstrIdx(name: String): Int

    abstract fun getConstrName(idx: Int): String
    abstract fun setConstrName(idx: Int, value: String)

    abstract fun getConstrPi(idx: Int): Double

    abstract fun getConstrRHS(idx: Int): Double
    abstract fun setConstrRHS(idx: Int, value: Double)

    abstract fun getConstrSlack(idx: Int): Double

    // endregion constraints getters and setters
}