package com.andresgmx.myapplicationreusapp.db.models
import java.security.MessageDigest
import java.util.Base64

class Cuenta(
    var nombre: String,
    private var hashedPassword: String,
    var correo: String,
    var usuario: Usuario,
) {

    /*fun actualizarNombre(nuevoNombre: String) {
        nombre = nuevoNombre
    }*/

    companion object {


        private const val SALT = "vhfdwjvnc"
        fun hashPassword(password: String): String {
            val saltedPassword = "$SALT$password"
            val sha256 = MessageDigest.getInstance("SHA-256")
            val hashBytes = sha256.digest(saltedPassword.toByteArray())
            return Base64.getEncoder().encodeToString(hashBytes)
        }
        fun verificarSeguridadContrasena(password: String): Boolean {
            // Verificar longitud mínima
            if (password.length < 8) {
                return false
            }

            // Verificar caracteres especiales
            val caracteresEspeciales = "!@#$%^&*()_-+=<>,.?/\\|{}[]~"
            val contieneCaracterEspecial = password.any { it in caracteresEspeciales }
            if (!contieneCaracterEspecial) {
                return false
            }

            // Verificar números
            val contieneNumero = password.any { it.isDigit() }
            if (!contieneNumero) {
                return false
            }

            // Verificar letras mayúsculas y minúsculas
            val contieneMayuscula = password.any { it.isUpperCase() }
            val contieneMinuscula = password.any { it.isLowerCase() }
            if (!contieneMayuscula || !contieneMinuscula) {
                return false
            }

            //Cumple con los requisitos entonces
            return true
        }


    }

    fun checkPassword(passwordInput: String): Boolean {
        val hashedInput = hashPassword(passwordInput)
        return hashedInput == hashedPassword
    }


    fun updatePassword(newPassword: String): Boolean {
        if (verificarSeguridadContrasena(newPassword)) {
            hashedPassword = hashPassword(newPassword)
            return true
        }
        return false
    }

}