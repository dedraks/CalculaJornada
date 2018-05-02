package com.in4byte.android.calculajornada

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import com.in4byte.android.calculajornada.adapter.JornadaAdapter
import com.in4byte.android.calculajornada.model.JornadaModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.db.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class MainActivity : AppCompatActivity() {

    var jornadaList: MutableList<JornadaModel> = mutableListOf()

    lateinit var jornadaAdapter: JornadaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            createCreateJornadaDialog(null)
        }

        database.use {

            select("jornada").exec {
                val l = parseList(classParser<JornadaModel>())

                Log.i("DATABASE", l.toString())

                jornadaList = l.toMutableList()
            }

        }


        jornadaAdapter = JornadaAdapter(this, jornadaList)
        recyclerViewJornada.adapter = jornadaAdapter
        val mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        recyclerViewJornada.layoutManager = mLayoutManager

        initSwipe()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity<SettingsActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showTimePicker(editText: EditText) {
        val hour = Relogio.Agora.hora
        val minute = Relogio.Agora.minuto
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(this@MainActivity, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute -> editText.setText((if (selectedHour < 10) "0$selectedHour" else "$selectedHour") + ":" + (if (selectedMinute < 10) "0$selectedMinute" else "$selectedMinute")) }, hour, minute, true)//Yes 24 hour time
        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
    }

    fun showDatePicker(editText: EditText) {
        val hoje = Relogio.Hoje
        val datePickerDialog: DatePickerDialog
        datePickerDialog = DatePickerDialog(this@MainActivity, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth -> editText.setText( "${if(dayOfMonth<10) "0${dayOfMonth}" else "$dayOfMonth"}/${if(month+1<10)"0${month+1}" else "${month+1}"}/$year" ) }, hoje.substring(6..9).toInt(), hoje.substring(3..4).toInt()-1, hoje.substring(0..1).toInt())
        datePickerDialog.show()
    }

    fun createCreateJornadaDialog(jornada: JornadaModel? = null): JornadaModel {

        val tJornadaModel = JornadaModel(null, Relogio.Hoje, "", "", "", "", "", "")

        lateinit var tData: EditText
        lateinit var tEntrada1: EditText
        lateinit var tSaida1: EditText
        lateinit var tEntrada2: EditText
        lateinit var tSaida2: EditText
        lateinit var tEntrada3: EditText
        lateinit var tSaida3: EditText
        lateinit var dialog: DialogInterface

        dialog = alert {
            title = "Jornada"
            isCancelable = false

            customView {
                verticalLayout {
                    tData = editText{
                        setText(jornada?.data ?: Relogio.Hoje)
                        inputType = android.text.InputType.TYPE_CLASS_DATETIME
                        isFocusable = false
                        isClickable = true
                        onClick {
                            showDatePicker(this@editText)
                        }
                    }
                    tEntrada1 = editText {
                        hint = "Entrada 1"
                        inputType = android.text.InputType.TYPE_CLASS_DATETIME
                        setText(jornada?.entrada_1 ?: "")
                        isFocusable = false
                        isClickable = true

                        onClick {
                            showTimePicker(this@editText)
                        }
                    }
                    tSaida1 = editText {
                        hint = "Saída 1"
                        inputType = android.text.InputType.TYPE_CLASS_DATETIME
                        setText(jornada?.saida_1 ?: "")

                        isFocusable = false
                        isClickable = true

                        onClick {
                            showTimePicker(this@editText)
                        }
                    }
                    tEntrada2 = editText {
                        hint = "Entrada 2"
                        inputType = android.text.InputType.TYPE_CLASS_DATETIME
                        setText(jornada?.entrada_2 ?: "")

                        isFocusable = false
                        isClickable = true

                        onClick {
                            showTimePicker(this@editText)
                        }
                    }
                    tSaida2 = editText {
                        hint = "Saída 2"
                        inputType = android.text.InputType.TYPE_CLASS_DATETIME
                        setText(jornada?.saida_2 ?: "")

                        isFocusable = false
                        isClickable = true

                        onClick {
                            showTimePicker(this@editText)
                        }
                    }/*
                    tEntrada3 = editText {
                        hint = "Entrada 2"
                        inputType = android.text.InputType.TYPE_CLASS_DATETIME
                        setText(jornada?.entrada3 ?: "")

                        isFocusable = false
                        isClickable = true

                        onClick {
                            showTimePicker(this@editText)
                        }
                    }
                    tSaida3 = editText {
                        hint = "Saída 2"
                        inputType = android.text.InputType.TYPE_CLASS_DATETIME
                        setText(jornada?.saida3 ?: "")

                        isFocusable = false
                        isClickable = true

                        onClick {
                            showTimePicker(this@editText)
                        }
                    }*/
                }
            }

            okButton {
                tJornadaModel.data = tData.text.toString()
                tJornadaModel.entrada_1 = tEntrada1.text.toString()
                tJornadaModel.entrada_2 = tEntrada2.text.toString()
                tJornadaModel.saida_1 = tSaida1.text.toString()
                tJornadaModel.saida_2 = tSaida2.text.toString()

                if (jornada == null) {
                    database.use {
                        val res = insert("jornada",
                                "id" to null,
                                "data" to tJornadaModel.data,
                                "entrada_1" to tJornadaModel.entrada_1,
                                "saida_1" to tJornadaModel.saida_1,
                                "entrada_2" to tJornadaModel.entrada_2,
                                "saida_2" to tJornadaModel.saida_2,
                                "entrada_3" to tJornadaModel.entrada_3,
                                "saida_3" to tJornadaModel.saida_3
                        )
                        Log.i("DATABASE", "id just inserted: $res")
                        tJornadaModel.id = res
                    }
                    jornadaList.add(tJornadaModel)
                } else {
                    tJornadaModel.id = jornada.id
                    database.use {
                        update("jornada",
                                "data" to tJornadaModel.data,
                                "entrada_1" to tJornadaModel.entrada_1,
                                "saida_1" to tJornadaModel.saida_1,
                                "entrada_2" to tJornadaModel.entrada_2,
                                "saida_2" to tJornadaModel.saida_2,
                                "entrada_3" to tJornadaModel.entrada_3,
                                "saida_3" to tJornadaModel.saida_3
                        )
                                .whereArgs("id = {id}", "id" to jornada.id!!)
                                .exec()
                    }
                    jornadaList.set(jornadaList.indexOf(jornada), tJornadaModel)
                }
                jornadaAdapter.notifyDataSetChanged()
            }
            cancelButton { }
        }.show()

        return tJornadaModel
    }

    fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                val position = viewHolder!!.adapterPosition

                alert("Tem certeza?", "Excluir") {
                    isCancelable = false
                    yesButton {

                        database.use {
                            delete("jornada", "id = {id}", "id" to (jornadaList[position]?.id ?: -1))
                        }

                        jornadaList.removeAt(position)
                        jornadaAdapter.notifyDataSetChanged()
                    }
                    noButton {
                        jornadaAdapter.notifyDataSetChanged()
                    }
                }.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewJornada)
    }

    fun carregarDados() = doAsync {

    }

    fun atualizarUI() {

    }
}
