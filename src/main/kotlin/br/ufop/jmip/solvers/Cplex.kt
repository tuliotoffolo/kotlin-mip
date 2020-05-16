package br.ufop.jmip.solvers

import br.ufop.jmip.entities.*

class Cplex(model: Model, name: String, sense: String) : Solver(model, name, sense){
    override fun addConstr(): Constr {
        TODO("Not yet implemented")
    }

    override fun addVar(): Var {
        TODO("Not yet implemented")
    }

    override fun get(param: String): Any? {
        TODO("Not yet implemented")
    }

    override fun set(param: String, value: Any?) {
        TODO("Not yet implemented")
    }

    override fun getVarColumn(idx: Int): Column {
        TODO("Not yet implemented")
    }

    override fun setVarColumn(idx: Int, value: Column) {
        TODO("Not yet implemented")
    }

    override fun getVarIdx(name: String): Int {
        TODO("Not yet implemented")
    }

    override fun getVarLB(idx: Int): Double {
        TODO("Not yet implemented")
    }

    override fun setVarLB(idx: Int, value: Double) {
        TODO("Not yet implemented")
    }

    override fun getVarName(idx: Int): String {
        TODO("Not yet implemented")
    }

    override fun setVarName(idx: Int, value: String) {
        TODO("Not yet implemented")
    }

    override fun getVarObj(idx: Int): Double {
        TODO("Not yet implemented")
    }

    override fun setVarObj(idx: Int, value: Double) {
        TODO("Not yet implemented")
    }

    override fun getVarRC(idx: Int): Double {
        TODO("Not yet implemented")
    }

    override fun getVarType(idx: Int): VarType {
        TODO("Not yet implemented")
    }

    override fun setVarType(idx: Int, value: VarType) {
        TODO("Not yet implemented")
    }

    override fun getVarUB(idx: Int): Double {
        TODO("Not yet implemented")
    }

    override fun setVarUB(idx: Int, value: Double) {
        TODO("Not yet implemented")
    }

    override fun getVarX(idx: Int): Double {
        TODO("Not yet implemented")
    }

    override fun getVarXi(idx: Int, i: Int): Double {
        TODO("Not yet implemented")
    }

    override fun getConstrExpr(idx: Int): LinExpr {
        TODO("Not yet implemented")
    }

    override fun setConstrExpr(idx: Int, value: LinExpr) {
        TODO("Not yet implemented")
    }

    override fun getConstrIdx(name: String): Int {
        TODO("Not yet implemented")
    }

    override fun getConstrName(idx: Int): String {
        TODO("Not yet implemented")
    }

    override fun setConstrName(idx: Int, value: String) {
        TODO("Not yet implemented")
    }

    override fun getConstrPi(idx: Int): Double {
        TODO("Not yet implemented")
    }

    override fun getConstrRHS(idx: Int): Double {
        TODO("Not yet implemented")
    }

    override fun setConstrRHS(idx: Int, value: Double) {
        TODO("Not yet implemented")
    }

    override fun getConstrSlack(idx: Int): Double {
        TODO("Not yet implemented")
    }
}