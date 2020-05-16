package br.ufop.jmip.util

data class Property(var value: Any?, val readOnly: Boolean, val category: Category) {

    enum class Category {
        RESULT, SETTINGS
    }
}