package com.in4byte.android.calculajornada.model

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

        return "$txtHora:$txtMinuto"
    }

    fun calcJornada1() = calcJornada(entrada1, saida1)

    fun calcJornada2() = calcJornada(entrada2, saida2)

    fun calcJornada3() = calcJornada(entrada3, saida3)

    fun calcIntervalo1() = calcJornada(saida1, entrada2)

    fun calcIntervalo2() = calcJornada(saida2, entrada3)

    fun calcJornadaTotal(): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm")

        val j1 = calcJornada1()
        val j2 = calcJornada2()

        if (j1 == "" || j2 == "") return ""

        val jd1 = simpleDateFormat.parse(calcJornada1())
        val jd2 = simpleDateFormat.parse(calcJornada2())


        val total = jd1.time + jd2.time

        val days = (total / (1000 * 60 * 60 * 24)).toInt()
        val hours = ((total - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()
        val min = (total - (1000 * 60 * 60 * 24 * days).toLong() - (1000 * 60 * 60 * hours).toLong()).toInt() / (1000 * 60)

        val txtHora = if (hours < 10) "0$hours" else "$hours"
        val txtMinuto = if (min < 10) "0$min" else "$min"

        return "$txtHora:$txtMinuto"
    }
}
