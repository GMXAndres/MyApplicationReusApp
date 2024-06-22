package com.andresgmx.myapplicationreusapp
import java.security.MessageDigest
import org.junit.Test
import org.junit.Assert.*
import com.andresgmx.myapplicationreusapp.db.models.Cuenta
import com.andresgmx.myapplicationreusapp.db.models.Cuenta.Companion.hashPassword

class CuentaTest {

    @Test
    fun testHashPassword() {
        val password = "mypassword"
        val expectedHashedPassword = hashPassword("mypassword")
        println(expectedHashedPassword)
        assertEquals(expectedHashedPassword, Cuenta.hashPassword(password))
    }

    @Test
    fun testVerificarSeguridadContrasena() {
        val passwordValida = "A1@bcdef"
        val passwordCorta = "Ab1@"
        val passwordSinCaracterEspecial = "Abcdefg1"
        val passwordSinNumero = "Abcdef@"
        val passwordSinMayuscula = "abcde1@"
        val passwordSinMinuscula = "ABCDE1@"

        assertTrue(Cuenta.verificarSeguridadContrasena(passwordValida))
        assertFalse(Cuenta.verificarSeguridadContrasena(passwordCorta))
        assertFalse(Cuenta.verificarSeguridadContrasena(passwordSinCaracterEspecial))
        assertFalse(Cuenta.verificarSeguridadContrasena(passwordSinNumero))
        assertFalse(Cuenta.verificarSeguridadContrasena(passwordSinMayuscula))
        assertFalse(Cuenta.verificarSeguridadContrasena(passwordSinMinuscula))
    }

    @Test
    fun testUpdatePassword() {
        val newpassword = "mynewpassword"
        //val hashedPassword = Cuenta.hashPassword(newpassword)
        assertFalse(Cuenta.verificarSeguridadContrasena(newpassword))
        val newvalidpassword = "A2@bcdef"
        assertTrue(Cuenta.verificarSeguridadContrasena(newvalidpassword))
    }

}