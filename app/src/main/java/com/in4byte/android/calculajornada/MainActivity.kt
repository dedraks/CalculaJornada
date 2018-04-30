package com.in4byte.android.calculajornada

import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import com.in4byte.android.calculajornada.adapter.JornadaAdapter
import com.in4byte.android.calculajornada.model.JornadaModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*
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

        jornadaAdapter = JornadaAdapter(this, jornadaList)
        recyclerViewJornada.adapter = jornadaAdapter
        recyclerViewJornada.layoutManager = LinearLayoutManager(this)
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

    fun createCreateJornadaDialog(jornada: JornadaModel? = null): JornadaModel {

        val tJornadaModel = JornadaModel(Relogio.Hoje, "", "", "", "", "", "")

        lateinit var tEntrada1: TextView
        lateinit var tSaida1: TextView
        lateinit var tEntrada2: TextView
        lateinit var tSaida2: TextView
        lateinit var dialog: DialogInterface

        dialog = alert {
            title = "Jornada"
            isCancelable = false

            customView {
                verticalLayout {
                    tEntrada1 = editText {
                        hint = "Entrada 1"
                        inputType = android.text.InputType.TYPE_CLASS_DATETIME
                        setText(jornada?.entrada1 ?: "")
                        isFocusable = false
                        isClickable = true

                        onClick {
                            showTimePicker(this@editText)
                        }
                    }
                    tSaida1 = editText {
                        hint = "Saída 1"
                        inputType = android.text.InputType.TYPE_CLASS_DATETIME
                        setText(jornada?.saida1 ?: "")

                        isFocusable = false
                        isClickable = true

                        onClick {
                            showTimePicker(this@editText)
                        }
                    }
                    tEntrada2 = editText {
                        hint = "Entrada 2"
                        inputType = android.text.InputType.TYPE_CLASS_DATETIME
                        setText(jornada?.entrada2 ?: "")

                        isFocusable = false
                        isClickable = true

                        onClick {
                            showTimePicker(this@editText)
                        }
                    }
                    tSaida2 = editText {
                        hint = "Saída 2"
                        inputType = android.text.InputType.TYPE_CLASS_DATETIME
                        setText(jornada?.saida2 ?: "")

                        isFocusable = false
                        isClickable = true

                        onClick {
                            showTimePicker(this@editText)
                        }
                    }
                }
            }

            okButton {
                tJornadaModel.entrada1 = tEntrada1.text.toString()
                tJornadaModel.entrada2 = tEntrada2.text.toString()
                tJornadaModel.saida1 = tSaida1.text.toString()
                tJornadaModel.saida2 = tSaida2.text.toString()

                if (jornada == null) {
                    jornadaList.add(tJornadaModel)
                } else {
                    jornadaList.set(jornadaList.indexOf(jornada), tJornadaModel)
                }
                jornadaAdapter.notifyDataSetChanged()
            }
            cancelButton { }
        }.show()

        return tJornadaModel
    }
}
