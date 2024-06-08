package com.andresgmx.myapplicationreusapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InicioActivity : AppCompatActivity() {
    private lateinit var btnHowIRecycle: Button
    private lateinit var btnqueries: Button
    private lateinit var btnExit: Button
    private lateinit var btnrewards: Button
    private lateinit var btnpoints: Button
    private lateinit var btnprofile: Button
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)
        initcomponents()
        initListeners()
        displayUserName()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun displayUserName() {
        val result = findViewById<TextView>(R.id.tvShowName)
        val name: String = intent.extras?.getString(EXTRA_NAME).orEmpty()
        result.text = name
    }

    private fun initListeners() {
        btnHowIRecycle.setOnClickListener { navigateTo(HowirecycleActivity::class.java) }
        btnqueries.setOnClickListener { navigateTo(ConsultasActivity::class.java) }
        btnrewards.setOnClickListener { navigateTo(RewardsActivity::class.java) }
        btnpoints.setOnClickListener { navigateTo(PointsActivity::class.java) }
        btnprofile.setOnClickListener { navigateTo(ProfileActivity::class.java) }
        btnExit.setOnClickListener { finishAffinity() }
    }

    private fun navigateTo(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    private fun initcomponents() {
        btnHowIRecycle = findViewById(R.id.btnHowIRecycle)
        btnqueries = findViewById(R.id.btnqueries)
        btnExit = findViewById(R.id.btnExit)
        btnrewards = findViewById(R.id.btnrewards)
        btnpoints = findViewById(R.id.btnpoints)
        btnprofile = findViewById(R.id.btnprofile)
    }



    companion object {
        const val EXTRA_NAME = "Extra_Name"
    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finishAffinity() // Cierra todas las actividades y cierra la aplicación
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000) // Restablece la variable después de 2 segundos
    }
}