package com.andresgmx.myapplicationreusapp
import java.security.MessageDigest
import org.junit.Test
import org.junit.Assert.*
import com.andresgmx.myapplicationreusapp.db.models.Cuenta
import com.andresgmx.myapplicationreusapp.db.models.Usuario
import com.andresgmx.myapplicationreusapp.db.models.Cuenta.Companion.hashPassword
import com.andresgmx.myapplicationreusapp.db.models.Direccion
import com.andresgmx.myapplicationreusapp.db.models.Puntos
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoVia
import java.time.LocalDate

class CuentaTest {

    @Test
    fun testHashPassword() {
        val cuenta = Cuenta(1,"johnmickley", "mypassword", "thomas.todd@example.com", Usuario(1,"John", "Mckinley","1234567890","3333333333", LocalDate.of(1990, 1, 1), LocalDate.now()))
        val invalidPassword = "mypassword"
        val validPassword = "A1@bcdef"
        cuenta.hashedPassword = Cuenta.hashPassword(validPassword)
        val expectedHashedPassword = hashPassword(validPassword)
        assertEquals(expectedHashedPassword, hashPassword(validPassword))
        assertThrows(IllegalArgumentException::class.java){
            hashPassword(invalidPassword)
        }

        println("Original password: $validPassword")
        println("Hashed password: $expectedHashedPassword")
        assertTrue(cuenta.checkPassword(validPassword))
        assertFalse(cuenta.checkPassword("A2@bcdef"))




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