package com.andresgmx.myapplicationreusapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HowirecycleActivity : AppCompatActivity() {
    private lateinit var btnInfoColors: Button
    private lateinit var btnInfoMaterials: Button
    private lateinit var btnVidReciclaje: Button
    private lateinit var btnRecEnCol: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_howirecycle)

        initComponents()
        initListeners()
        setupActionBar()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = "Regresar"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initListeners() {
        btnInfoColors.setOnClickListener { navigateTo("https://coca-colafemsa.com/noticias/reciclaje-en-colombia-colores-para-separar-residuos-en-casa/") }
        btnInfoMaterials.setOnClickListener { navigateTo("https://www.elcolombiano.com/medio-ambiente/como-aprender-a-reciclar-IC20773469") }
        btnVidReciclaje.setOnClickListener { navigateTo("https://www.youtube.com/watch?v=auj4bXMHL-8") }
        btnRecEnCol.setOnClickListener { navigateTo("https://www.eltiempo.com/vida/medio-ambiente/colombia-avanza-a-paso-lento-en-educacion-e-implementacion-de-medidas-para-el-reciclaje-769399") }
    }

    private fun navigateTo(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun initComponents() {
        btnInfoColors = findViewById(R.id.btnInfoColors)
        btnInfoMaterials = findViewById(R.id.btnInfoMaterials)
        btnVidReciclaje = findViewById(R.id.btnVidReciclaje)
        btnRecEnCol = findViewById(R.id.btnRecEnCol)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}