package com.in4byte.android.calculajornada

import android.util.Log

class Relogio(horario: String) {
    var hora: Int
    var minuto: Int

    init {
        hora = horario.substring(0..1).toInt()
        minuto = horario.substring(3..4).toInt()
    }

    constructor(h: Int, m: Int): this("00:00") {
        hora = h
        minuto = m
    }

    operator fun plus(outro: Relogio): Relogio {
        var minutosTotais = toMinutes() + outro.toMinutes()
        return fromMinutes(minutosTotais)
    }

    operator fun minus(outro: Relogio): Relogio {
        var minutosTotais = toMinutes() - outro.toMinutes()
        return fromMinutes(minutosTotais)
    }

    fun toMinutes(): Int {
        return hora * 60 + minuto
    }

    fun fromMinutes(minutes: Int): Relogio {
        val h = minutes / 60
        val m = minutes % 60
        return Relogio(h, m)
    }

    override fun toString(): String {
        val tH = if (hora < 10) "0$hora" else "$hora"
        val tM = if (minuto < 10) "0$minuto" else "$minuto"

        Log.i("TO_STRING", "$tH:$tM")

        return "$tH:$tM"
    }
}