package com.andresgmx.myapplicationreusapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmailLogin: AppCompatEditText
    private lateinit var etPassLogin: AppCompatEditText
    private lateinit var btnLogin2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        setupActionBar()
        initComponents()
        initListeners()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                if (isValidEmail(email)) {
                    etEmailLogin.error = null
                } else {
                    etEmailLogin.error = "Dirección de correo invalida"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initListeners() {
        btnLogin2.setOnClickListener {
            vibratePhone()
            val email = etEmailLogin.text.toString()
            val pass = etPassLogin.text.toString()
            if (areFieldsValid(email, pass) && etEmailLogin.error == null) {
                navigateTo(InicioActivity::class.java)
            }else if (areFieldsValid(email, pass)){
                showToast(getString(R.string.emailInvalid))
            } else {
                showToast(getString(R.string.necessary_data))
            }
        }
    }

    private fun navigateTo(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    private fun areFieldsValid(vararg fields: String): Boolean {
        return fields.all { it.isNotEmpty() }
    }

    private fun initComponents() {
        etEmailLogin = findViewById(R.id.etEmailLogin)
        etPassLogin = findViewById(R.id.etPassLogin)
        btnLogin2 = findViewById(R.id.btnLogin2)
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = "Pagina Principal"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun vibratePhone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Para API 31 y superior
            val vibratorManager =
                getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrator = vibratorManager.defaultVibrator
            val vibrationEffect =
                VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Para API 26 a 30
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val vibrationEffect =
                VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        }
    }
    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}