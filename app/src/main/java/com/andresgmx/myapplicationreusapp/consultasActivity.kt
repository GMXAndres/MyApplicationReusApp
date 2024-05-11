package com.andresgmx.myapplicationreusapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class consultasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_consultas)

        val actionBarConsultas = supportActionBar
        actionBarConsultas!!.title = "Regresar"
        actionBarConsultas.setDisplayHomeAsUpEnabled(true)

        val btnHorario=findViewById<Button>(R.id.btnHorario)
        btnHorario.setOnClickListener{
            val urlHorario:String="https://www.amb.gov.co/punto-limpio-metropolitano-de-bucaramanga-y-14-sitios-mas-en-santander-estan-recibiendo-los-residuos-pos-consumo-hoy-y-manana/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlHorario))
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
