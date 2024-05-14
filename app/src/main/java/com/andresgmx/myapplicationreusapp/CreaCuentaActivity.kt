package com.andresgmx.myapplicationreusapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreaCuentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crea_cuenta)
        val actionBarHowIRec = supportActionBar
        actionBarHowIRec!!.title = "Regresar"
        actionBarHowIRec.setDisplayHomeAsUpEnabled(true)

        val btncreateAccount2 = findViewById<AppCompatButton>(R.id.btncreateAccount2)


        val etname = findViewById<AppCompatEditText>(R.id.etname)
        val etLastName = findViewById<AppCompatEditText>(R.id.etLastName)
        val etIdentification = findViewById<AppCompatEditText>(R.id.etIdentification)
        val etEmail = findViewById<AppCompatEditText>(R.id.etEmail)
        val etAddress=findViewById<AppCompatEditText>(R.id.etAddress)
        val etPassword=findViewById<AppCompatEditText>(R.id.etPassword)

        btncreateAccount2.setOnClickListener {
            val name = etname.text.toString()//extrae el texto ingresado y lo convierte a cadena String
            val lastName = etLastName.text.toString()//extrae el texto ingresado y lo convierte a cadena String
            val identification = etIdentification.text.toString()//extrae el texto ingresado y lo convierte a cadena String
            val email = etEmail.text.toString()//extrae el texto ingresado y lo convierte a cadena String
            val address=etAddress.text.toString()//extrae el texto ingresado y lo convierte a cadena String
            val password=etPassword.text.toString()//extrae el texto ingresado y lo convierte a cadena String

            if (name.isNotEmpty() && lastName.isNotEmpty() && identification.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty() && password.isNotEmpty()) {//este if verifica si los espacios de crear usuario estan vacios, si estan vacios no continua
                val intent = Intent(this, inicioActivity::class.java)
                intent.putExtra("Extra_Name", name)// este bloque guarda el nombre de usuario
                startActivity(intent)
            } else Toast.makeText(
                getApplicationContext(),
                "Todos los campos son obligatorios",
                Toast.LENGTH_SHORT
            ).show();
        }//Muestra mensaje de error si falta alguno de los datos del if

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}