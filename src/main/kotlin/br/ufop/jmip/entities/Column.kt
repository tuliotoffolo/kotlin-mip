package br.ufop.jmip.entities

data class Column(val constrs: List<Constr> = ArrayList(), val coeffs: List<Number> = ArrayList()) {
    companion object {
        @JvmField
        val EMPTY = Column(emptyList(), emptyList())
    }

    fun isEmpty() = constrs.isEmpty()
}
