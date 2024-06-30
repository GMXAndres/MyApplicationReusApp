package com.andresgmx.myapplicationreusapp

import com.andresgmx.myapplicationreusapp.db.models.Reciclaje
import com.andresgmx.myapplicationreusapp.db.models.Usuario
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoMaterial
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate

class ReciclajeTest {
    @Test
    fun testUsuarioPorCedula() {
        val usuario1 = Usuario(1,"John", "Doe", "1234567890", "5555555555", LocalDate.of(1990, 1, 1))
        val usuario2 = Usuario(2,"Jane", "Doe", "9876543210", "5555555556", LocalDate.of(1992, 2, 2))
        val usuarios = listOf(usuario1, usuario2)

        val reciclaje = Reciclaje(1,TipoMaterial.VERDE, 15.0, LocalDate.now(), null)

        // Test case for existing user
        val usuarioEncontrado = reciclaje.ingresarUsuarioPorCedula("1234567890", usuarios)
        assertNotNull(usuarioEncontrado)
        assertEquals(1, usuarioEncontrado.id)
        Assert.assertThrows(IllegalArgumentException::class.java) {
            reciclaje.ingresarUsuarioPorCedula("00000000000", usuarios)
        }


    }
}