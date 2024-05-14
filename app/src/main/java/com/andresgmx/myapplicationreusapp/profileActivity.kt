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
        setContentView(R.layout.activity_profile)
        val actionBarHowIRec = supportActionBar
        actionBarHowIRec!!.title = "Regresar"
        actionBarHowIRec.setDisplayHomeAsUpEnabled(true)//btnPolitics

        val btnMyData=findViewById<Button>(R.id.btnMyData)
        btnMyData.setOnClickListener{navigateToPersonalData()}

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

    private fun navigateToPersonalData() {
        val intent=Intent(this,PersonalinfoActivity::class.java)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}