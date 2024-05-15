package com.andresgmx.myapplicationreusapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ConsultasActivity : AppCompatActivity() {
    private lateinit var btnHorario: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_consultas)

        setupActionBar()
        initcomponents()
        initListeners()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initcomponents() {
        btnHorario = findViewById(R.id.btnHorario)
    }

    private fun initListeners() {
        btnHorario.setOnClickListener {
            val urlHorario =
                "https://www.amb.gov.co/punto-limpio-metropolitano-de-bucaramanga-y-14-sitios-mas-en-santander-estan-recibiendo-los-residuos-pos-consumo-hoy-y-manana/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlHorario))
            startActivity(intent)
        }
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = "Regresar"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
