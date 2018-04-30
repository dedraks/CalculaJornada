package com.in4byte.android.calculajornada.model

import android.util.Log
import com.in4byte.android.calculajornada.Relogio
import java.text.SimpleDateFormat


data class JornadaModel(var entrada1: String, var saida1: String, var entrada2: String, var saida2: String, var entrada3: String, var saida3: String) {

    fun calcJornada(dataInicio: String, dataFim: String): String {

        if (dataInicio == "" || dataFim == "")
            return ""

        val simpleDateFormat = SimpleDateFormat("HH:mm")
        val startDate = simpleDateFormat.parse(dataInicio)
        val endDate = simpleDateFormat.parse(dataFim)

        var difference = endDate.time - startDate.time
        if (difference < 0) {
            val dateMax = simpleDateFormat.parse("24:00")
            val dateMin = simpleDateFormat.parse("00:00")
            difference = dateMax.time - startDate.time + (endDate.time - dateMin.time)
        }
        val days = (difference / (1000 * 60 * 60 * 24)).toInt()
        val hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()
        val min = (difference - (1000 * 60 * 60 * 24 * days).toLong() - (1000 * 60 * 60 * hours).toLong()).toInt() / (1000 * 60)

        val txtHora = if (hours < 10) "0$hours" else "$hours"
        val txtMinuto = if (min < 10) "0$min" else "$min"

        val TAG = "JORNADA"
        Log.i(TAG, "ENTRADA 1: $entrada1")
        Log.i(TAG, "SAIDA 1: $saida1")
        val e1 = Relogio(entrada1)
        val s1 = Relogio(saida1)
        val e2 = Relogio(entrada2)
        val s2 = Relogio(saida2)


        Log.i(TAG, (s1 - e1).toString())
        Log.i(TAG, (e2 - s1).toString())
        Log.i(TAG, (s2 - e2).toString())
        Log.i(TAG, ((s1 - e1)+(s2 - e2)).toString())

        return "$txtHora:$txtMinuto"
    }

    fun calcJornada1() = (Relogio(saida1) - Relogio(entrada1))

    fun calcJornada2() = (Relogio(saida2) - Relogio(entrada2))

    fun calcJornada3() = (Relogio(saida3) - Relogio(entrada3))

    fun calcIntervalo1() = (Relogio(entrada2) - Relogio(saida1))

    fun calcIntervalo2() = (Relogio(entrada3) - Relogio(saida2))

    fun calcJornadaTotal(): String {
        return ((calcJornada1()) + (calcJornada2())).toString()
    }
}
