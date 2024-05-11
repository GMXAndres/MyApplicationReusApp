package com.andresgmx.myapplicationreusapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class howirecycleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_howirecycle)
        val btnInfoColors=findViewById<Button>(R.id.btnInfoColors)
        btnInfoColors.setOnClickListener{
            val url1="https://coca-colafemsa.com/noticias/reciclaje-en-colombia-colores-para-separar-residuos-en-casa/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url1))
            startActivity(intent)
        }
        val btnInfoMaterials=findViewById<Button>(R.id.btnInfoMaterials)
        btnInfoMaterials.setOnClickListener{
            val url2="https://www.elcolombiano.com/medio-ambiente/como-aprender-a-reciclar-IC20773469"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url2))
            startActivity(intent)
        }
        val btnVidReciclaje=findViewById<Button>(R.id.btnVidReciclaje)
        btnVidReciclaje.setOnClickListener{
            val url3="https://www.youtube.com/watch?v=auj4bXMHL-8"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url3))
            startActivity(intent)
        }
        val btnRecEnCol=findViewById<Button>(R.id.btnRecEnCol)
        btnRecEnCol.setOnClickListener{
            val url4="https://www.eltiempo.com/vida/medio-ambiente/colombia-avanza-a-paso-lento-en-educacion-e-implementacion-de-medidas-para-el-reciclaje-769399"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url4))
            startActivity(intent)
        }
        val actionBarHowIRec=supportActionBar
        actionBarHowIRec!!.title="Regresar"
        actionBarHowIRec.setDisplayHomeAsUpEnabled(true)

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