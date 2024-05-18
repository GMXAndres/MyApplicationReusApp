package com.andresgmx.myapplicationreusapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import androidx.core.widget.doAfterTextChanged
import java.util.regex.Pattern

class CreaCuentaActivity : AppCompatActivity() {
    private lateinit var btncreateAccount2: Button
    private lateinit var etname: AppCompatEditText
    private lateinit var etLastName: AppCompatEditText
    private lateinit var etIdentification: AppCompatEditText
    private lateinit var etEmail: AppCompatEditText
    private lateinit var etAddress: AppCompatEditText
    private lateinit var etPassword: AppCompatEditText

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crea_cuenta)

        initcomponents()
        initListeners()
        setupActionBar()
        changeColorHint(etname)
        changeColorHint(etLastName)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //corrobora el correo-----------------------------------------------------------------------
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                if (isValidEmail(email)) {
                    etEmail.error = null
                } else {
                    etEmail.error = "Dirección de correo invalida"
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        //corrobora la contraseña-------------------------------------------------------------------
        etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (isValidPassword(password)) {
                    etPassword.error = null
                } else {
                    etPassword.error = "La contraseña no cumple los requisitos"
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }

    private fun changeColorHint(etName: AppCompatEditText) {//cambia color de la pista si el espacio esta vacio
        etName.doAfterTextChanged { text ->
            if (text.isNullOrEmpty()) {
                etName.setHintTextColor(getColor(R.color.etEmpty)) // Cambia a rojo si el texto está vacío o nulo
            } else {
                etName.setHintTextColor(getColor(R.color.etok)) // Cambia a verde si hay texto en el EditText
            }
        }
    }

    private fun initListeners() {
        btncreateAccount2.setOnClickListener {
            val name =
                etname.text.toString()//extrae el texto ingresado y lo convierte a cadena String
            val lastName =
                etLastName.text.toString()//extrae el texto ingresado y lo convierte a cadena String
            val identification =
                etIdentification.text.toString()//extrae el texto ingresado y lo convierte a cadena String
            val email =
                etEmail.text.toString()//extrae el texto ingresado y lo convierte a cadena String
            val address =
                etAddress.text.toString()//extrae el texto ingresado y lo convierte a cadena String
            val password =
                etPassword.text.toString()//extrae el texto ingresado y lo convierte a cadena String

            if (areFieldsValid(name, lastName, email, address, identification, password) && etEmail.error == null && etPassword.error == null) {
                navigateToInicioActivity(name)
            }else if (areFieldsValid(name, lastName, email, address, identification, password)&& etPassword.error == null){
                showToast(getString(R.string.emailInvalid))
            }
            else if (areFieldsValid(name, lastName, identification, email, address, password)&& etEmail.error == null){
                showToast(getString(R.string.passInvalid))
            }else {
                showToast(getString(R.string.necessary_data))
            }
        }
    }

    private fun areFieldsValid(vararg fields: String): Boolean {
        return fields.all { it.isNotEmpty() }
    }

    private fun navigateToInicioActivity(name: String) {
        val intent = Intent(this, InicioActivity::class.java).apply {
            putExtra("Extra_Name", name)
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = "Pagina Principal"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initcomponents() {
        btncreateAccount2 = findViewById(R.id.btncreateAccount2)
        etname = findViewById(R.id.etname)
        etLastName = findViewById(R.id.etLastName)
        etIdentification = findViewById(R.id.etIdentification)
        etEmail = findViewById(R.id.etEmail)
        etAddress = findViewById(R.id.etAddress)
        etPassword = findViewById(R.id.etPassword)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
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