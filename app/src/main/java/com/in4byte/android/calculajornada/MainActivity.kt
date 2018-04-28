package com.in4byte.android.calculajornada

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.in4byte.android.calculajornada.adapter.JornadaAdapter
import com.in4byte.android.calculajornada.model.JornadaModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    var jornadaList: MutableList<JornadaModel> = mutableListOf(
            JornadaModel("07:29", "12:14", "13:17", "16:32", "", "")
            //JornadaModel("", "", "", "", "", "")
    )

    lateinit var jornadaAdapter: JornadaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
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
}
