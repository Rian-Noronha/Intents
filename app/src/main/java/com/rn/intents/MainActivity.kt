package com.rn.intents

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.Settings
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listView = ListView(this)
        setContentView(listView)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.intent_actions)
        )
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            openIntentAtPosition(position)
        }
    }

    private fun openIntentAtPosition(position: Int) {
        val uri: Uri?
        val intent: Intent?
        when (position) {
            0 -> {//Abrir URL.
                uri = Uri.parse("https://www.google.com.br/")
                intent = Intent(Intent.ACTION_VIEW, uri)
                openIntent(intent)
            }

            1 -> { //Realizar chamada.
                uri = Uri.parse("tel:87981670359")
                intent = Intent(Intent.ACTION_DIAL, uri)
                openIntent(intent)
            }

            2 -> {//Pesquisar uma posição do mapa. *Emulador ou aparelho com Google Maps.
                uri = Uri.parse("geo:0,0?q=Rua+Amelia,Recife")
                intent = Intent(Intent.ACTION_VIEW, uri)
                openIntent(intent)
            }

            3 -> { //Abrir editor de SMS.
                uri = Uri.parse("sms:12345")
                intent = Intent(Intent.ACTION_VIEW, uri)
                openIntent(intent)
            }

            4 -> { //Compartilhar
                intent = Intent()
                    .setAction(Intent.ACTION_SEND)
                    .putExtra(
                        Intent.EXTRA_TEXT,
                        "Compartilhando via Intent."
                    )
                    .setType("text/plain")
                openIntent(intent)
            }

            5 -> { //Alarme.
                intent =
                    Intent(AlarmClock.ACTION_SET_ALARM).putExtra(
                        AlarmClock.EXTRA_MESSAGE,
                        "Estudar Android"
                    )
                        .putExtra(AlarmClock.EXTRA_HOUR, 19)
                        .putExtra(AlarmClock.EXTRA_MINUTES, 0)
                        .putExtra(AlarmClock.EXTRA_SKIP_UI, true)
                        .putExtra(
                            AlarmClock.EXTRA_DAYS,
                            arrayListOf(
                                Calendar.MONDAY,
                                Calendar.WEDNESDAY,
                                Calendar.FRIDAY
                            )
                        )
                openIntent(intent)
            }

            6 -> { //Buscar na web.
                intent = Intent(Intent.ACTION_SEARCH)
                    .putExtra(SearchManager.QUERY, "Amazon")
                openIntent(intent)
            }

            7 -> { //Configurar aparelho.
                intent = Intent(Settings.ACTION_SETTINGS)
                openIntent(intent)
            }

            8 -> { //Ação customizada um.

                intent =
                    Intent("com.rn.CUSTOM_ACTION")
                openIntent(intent)

            }

            9 -> {//Ação customizada dois.
                uri = Uri.parse("produto://Notebook/Slim")
                intent = Intent(Intent.ACTION_VIEW, uri)
                openIntent(intent)
            }

            else -> finish()
        }
    }


    private fun openIntent(intent: Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, R.string.error_intent, Toast.LENGTH_SHORT).show()
        }
    }
}