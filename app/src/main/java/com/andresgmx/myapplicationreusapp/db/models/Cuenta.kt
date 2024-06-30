package com.andresgmx.myapplicationreusapp.db.models
import java.security.MessageDigest
import java.util.Base64

class Cuenta(
    var id: Long? = null,
    var nombre: String? = null,
    var hashedPassword: String? = null,
    var correo: String? = null,
    var usuario: Usuario,
) {

    /*fun actualizarNombre(nuevoNombre: String) {
        nombre = nuevoNombre
    }*/

    companion object {


        private const val SALT = "vhfdwjvnc"
        fun hashPassword(password: String): String {
            if (verificarSeguridadContrasena(password)) {
                val saltedPassword = "$SALT$password"
                val sha256 = MessageDigest.getInstance("SHA-256")
                val hashBytes = sha256.digest(saltedPassword.toByteArray())
                return Base64.getEncoder().encodeToString(hashBytes)
            }
            else{
                throw IllegalArgumentException("La contraseña no cumple con los requisitos")
            }
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