package mip

data class Column(val constrs: List<Constr> = ArrayList(), val coeffs: List<Double> = ArrayList()) {
    val size: Int get() = constrs.size

    companion object {
        @JvmField
        val EMPTY = Column(emptyList(), emptyList())
    }

    fun isEmpty() = constrs.isEmpty()
}
