package com.andresgmx.myapplicationreusapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class profileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val actionBarHowIRec = supportActionBar
        actionBarHowIRec!!.title = "Regresar"
        actionBarHowIRec.setDisplayHomeAsUpEnabled(true)//btnPolitics

        setContentView(R.layout.activity_profile)
        val btnPolitics=findViewById<Button>(R.id.btnPolitics)
        btnPolitics.setOnClickListener{
            val url4="https://www.amb.gov.co/politica-de-tratamiento-de-datos-personales/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url4))
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