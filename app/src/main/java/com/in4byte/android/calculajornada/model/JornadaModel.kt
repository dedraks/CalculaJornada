package com.in4byte.android.calculajornada.model

import com.in4byte.android.calculajornada.Relogio


data class JornadaModel(
        var id: Int? = null,
        var data: String,
        var entrada_1: String,
        var saida_1: String,
        var entrada_2: String,
        var saida_2: String,
        var entrada_3: String,
        var saida_3: String
) {

    fun calcJornada1() = (Relogio(saida_1) - Relogio(entrada_1))

    fun calcJornada2() = (Relogio(saida_2) - Relogio(entrada_2))

    fun calcJornada3() = (Relogio(saida_3) - Relogio(entrada_3))

    fun calcIntervalo1() = (Relogio(entrada_2) - Relogio(saida_1))

    fun calcIntervalo2() = (Relogio(entrada_3) - Relogio(saida_2))

    fun calcJornadaTotal(): String {
        return (calcJornada1() + calcJornada2()).toString()
    }
}
