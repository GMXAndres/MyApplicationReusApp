package com.andresgmx.myapplicationreusapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InicioActivity : AppCompatActivity() {
    private lateinit var btnHowIRecycle: Button
    private lateinit var btnqueries: Button
    private lateinit var btnlogout: Button
    private lateinit var btnrewards: Button
    private lateinit var btnpoints: Button
    private lateinit var btnprofile: Button
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
        btnlogout.setOnClickListener { returnToMain() }
    }

    private fun navigateTo(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    private fun initcomponents() {
        btnHowIRecycle = findViewById(R.id.btnHowIRecycle)
        btnqueries = findViewById(R.id.btnqueries)
        btnlogout = findViewById(R.id.btnlogout)
        btnrewards = findViewById(R.id.btnrewards)
        btnpoints = findViewById(R.id.btnpoints)
        btnprofile = findViewById(R.id.btnprofile)
    }

    private fun returnToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    companion object {
        const val EXTRA_NAME = "Extra_Name"
    }
}