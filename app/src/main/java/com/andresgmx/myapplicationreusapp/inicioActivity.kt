package com.andresgmx.myapplicationreusapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class inicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)

        val btnHowIRecycle = findViewById<Button>(R.id.btnHowIRecycle)
        btnHowIRecycle.setOnClickListener { navigateToHowIRecycle() }

        val btnqueries = findViewById<Button>(R.id.btnqueries)
        btnqueries.setOnClickListener { navigateToqueries() }

        val btnlogout = findViewById<Button>(R.id.btnlogout)
        btnlogout.setOnClickListener { returnToMain() }

        val btnrewards=findViewById<Button>(R.id.btnrewards)
        btnrewards.setOnClickListener{navigateToRewards()}

        val btnpoints = findViewById<Button>(R.id.btnpoints)
        btnpoints.setOnClickListener { navigateToPoints() }

        val btnprofile=findViewById<Button>(R.id.btnprofile)
        btnprofile.setOnClickListener{navigateToProfile()}

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun navigateToProfile() {
        val intent=Intent(this,profileActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToRewards() {
        val intent=Intent(this,rewardsActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToPoints() {
        val intent = Intent(this, pointsActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToqueries() {
        val intent = Intent(this, consultasActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToHowIRecycle() {
        val intent = Intent(this, howirecycleActivity::class.java)
        startActivity(intent)
    }

    private fun returnToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}