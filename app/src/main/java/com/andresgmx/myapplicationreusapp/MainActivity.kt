package com.andresgmx.myapplicationreusapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var btncreateAccount: Button
    private lateinit var btnLogin: Button
    private lateinit var btnPrueba: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initcomponents()
        initListener()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initListener() {
        btncreateAccount.setOnClickListener {
            navigateTo(CreaCuentaActivity::class.java)
            vibratePhone()
        }
        btnLogin.setOnClickListener {
            navigateTo(LoginActivity::class.java)
            vibratePhone()
        }
        btnPrueba.setOnClickListener{navigateTo(InicioActivity::class.java)
            vibratePhone()}

    }

    private fun navigateTo(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    private fun initcomponents() {
        btncreateAccount = findViewById(R.id.btncreateAccount)
        btnLogin = findViewById(R.id.btnLogin)
        btnPrueba = findViewById(R.id.btnPrueba)
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
}