package com.andresgmx.myapplicationreusapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.regex.Pattern

class UpdatePassActivity : AppCompatActivity() {
    private lateinit var etCurrentPass:AppCompatEditText
    private lateinit var etNewPass:AppCompatEditText
    private lateinit var btnUpdatePass:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_pass)
        setupActionBar()
        initComponents()
        initListeners()
        checkPass(etNewPass)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checkPass(editText: AppCompatEditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (isValidPassword(password)) {
                    editText.error = null
                } else {
                    editText.error = "La contraseña no cumple los requisitos"
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initListeners() {
        btnUpdatePass.setOnClickListener{
            val currentPass=etCurrentPass.text.toString()
            val newPass=etNewPass.text.toString()
            if (currentPass.isNotEmpty() && etNewPass.error == null && newPass.isNotEmpty()) {
                showToast("contraseña actualizada")
            }
            else {
                showToast(getString(R.string.necessary_data))
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun initComponents() {
        etCurrentPass=findViewById(R.id.etCurrentPass)
        etNewPass=findViewById(R.id.etNewPass)
        btnUpdatePass=findViewById(R.id.btnUpdatePass)
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = "Regresar"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +          // at least one digit
                    "(?=.*[a-z])" +          // at least one lower case letter
                    "(?=.*[A-Z])" +          // at least one upper case letter
                    "(?=.*[a-zA-Z])" +       // any letter
                    ".{8,}" +                // at least 8 characters
                    "$"                      //fin de la cadena
        )
        return passwordPattern.matcher(password).matches()
    }
}