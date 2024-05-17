package com.andresgmx.myapplicationreusapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged

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
        etLastName.doAfterTextChanged { text ->
            if (text.isNullOrEmpty()) {
                etLastName.setBackgroundColor(getColor(R.color.etEmpty)) // Cambia a rojo si el texto está vacío o nulo
            } else {
                etLastName.setBackgroundColor(getColor(R.color.etok)) // Cambia a verde si hay texto en el EditText
            }
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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

            if (areFieldsValid(name, lastName, identification, email, address, password)) {
                navigateToInicioActivity(name)
            } else {
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
}