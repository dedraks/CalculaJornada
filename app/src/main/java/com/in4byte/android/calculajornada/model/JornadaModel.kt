package com.in4byte.android.calculajornada.model

import com.in4byte.android.calculajornada.Relogio


data class JornadaModel(var data: String, var entrada1: String, var saida1: String, var entrada2: String, var saida2: String, var entrada3: String, var saida3: String) {

    fun calcJornada1() = (Relogio(saida1) - Relogio(entrada1))

    fun calcJornada2() = (Relogio(saida2) - Relogio(entrada2))

    fun calcJornada3() = (Relogio(saida3) - Relogio(entrada3))

    fun calcIntervalo1() = (Relogio(entrada2) - Relogio(saida1))

    fun calcIntervalo2() = (Relogio(entrada3) - Relogio(saida2))

    fun calcJornadaTotal(): String {
        return (calcJornada1() + calcJornada2()).toString()
    }
}
