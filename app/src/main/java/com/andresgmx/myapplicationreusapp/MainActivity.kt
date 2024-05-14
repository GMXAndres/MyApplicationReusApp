package com.andresgmx.myapplicationreusapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btncreateAccount=findViewById<Button>(R.id.btncreateAccount)
        btncreateAccount.setOnClickListener{navigateToCreateAccoun()}

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener { navigateToInicio()}

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun navigateToCreateAccoun() {
        val intent=Intent(this, CreaCuentaActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToInicio(){
        val intent=Intent(this,inicioActivity::class.java)
        startActivity(intent)

    }

}