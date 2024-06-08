package com.andresgmx.myapplicationreusapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    private lateinit var btnMyData: Button
    private lateinit var btnPolitics: Button
    private lateinit var btnUpdatePass:Button
    private lateinit var btnlogout:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        setupActionBar()
        initComponents()
        initListeners()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initListeners() {
        btnMyData.setOnClickListener{navigateTo(PersonalInfoActivity::class.java)}
        btnPolitics.setOnClickListener { navigateToUrl("https://www.amb.gov.co/politica-de-tratamiento-de-datos-personales/") }
        btnUpdatePass.setOnClickListener{navigateTo(UpdatePassActivity::class.java)}
        btnlogout.setOnClickListener{returnToMain()}
    }

    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun navigateTo(activityClass:Class<*>) {
        val intent = Intent(this,activityClass)
        startActivity(intent)
    }

    private fun initComponents() {
        btnMyData = findViewById(R.id.btnMyData)
        btnPolitics = findViewById(R.id.btnPolitics)
        btnUpdatePass=findViewById(R.id.btnUpdatePass)
        btnlogout=findViewById(R.id.btnlogout)
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = "Regresar"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    private fun returnToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}