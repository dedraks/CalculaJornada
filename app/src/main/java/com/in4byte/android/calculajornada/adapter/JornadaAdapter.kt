package com.in4byte.android.calculajornada.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.in4byte.android.calculajornada.MainActivity
import com.in4byte.android.calculajornada.R
import com.in4byte.android.calculajornada.model.JornadaModel
import kotlinx.android.synthetic.main.jornada_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class JornadaAdapter(private val appContext: MainActivity, private var jornadaList: MutableList<JornadaModel>) :
        RecyclerView.Adapter<JornadaAdapter.JornadaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JornadaViewHolder {
        val view = LayoutInflater.from(appContext).inflate(R.layout.jornada_item, parent, false)
        return JornadaViewHolder(view)
    }

    override fun getItemCount() = jornadaList.size

    override fun onBindViewHolder(holder: JornadaViewHolder, position: Int) {
        holder.bindView(jornadaList[position])
    }


    inner class JornadaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewEntrada1 = itemView.textViewEntrada1
        val textViewEntrada2 = itemView.textViewEntrada2
        val textViewEntrada3 = itemView.textViewEntrada3
        val textViewSaida1 = itemView.textViewSaida1
        val textViewSaida2 = itemView.textViewSaida2
        val textViewSaida3 = itemView.textViewSaida3
        val textViewJornada1 = itemView.textViewJornada1
        val textViewJornada2 = itemView.textViewJornada2
        val textViewJornadaTotal = itemView.textViewJornadaTotal
        val textViewIntervalo1 = itemView.textViewIntervalo1
        val linearLayoutEntrada3 = itemView.linearLayoutEntrada3
        val linearLayoutSaida3 = itemView.linearLayoutSaida3

        fun bindView(jornada: JornadaModel) {
            textViewEntrada1.text = jornada.entrada1
            textViewEntrada2.text = jornada.entrada2

            if (jornada.entrada3 == "") {
                linearLayoutSaida3.visibility = View.GONE
                linearLayoutEntrada3.visibility = View.GONE
            } else {
                textViewEntrada3.text = jornada.entrada3
                textViewSaida3.text = jornada.saida3
            }

            textViewSaida1.text = jornada.saida1
            textViewSaida2.text = jornada.saida2
            textViewJornada1.text = jornada.calcJornada1()
            textViewIntervalo1.text = jornada.calcIntervalo1()
            textViewJornada2.text = jornada.calcJornada2()
            textViewJornadaTotal.text = jornada.calcJornadaTotal()

            itemView.onClick {
                appContext.createCreateJornadaDialog(jornada)
            }
        }
    }
}