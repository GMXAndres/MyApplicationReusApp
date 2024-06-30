package com.andresgmx.myapplicationreusapp

import com.andresgmx.myapplicationreusapp.db.models.Puntos
import com.andresgmx.myapplicationreusapp.db.models.PuntosRecompensas
import com.andresgmx.myapplicationreusapp.db.models.Reciclaje
import com.andresgmx.myapplicationreusapp.db.models.Recompensas
import com.andresgmx.myapplicationreusapp.db.models.Usuario
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoMaterial
import org.junit.Test
import org.junit.Assert.*
import java.time.LocalDate


class PuntosTest {
    @Test
    fun testAsignarYRestarPuntos() {
        val usuario = Usuario(
            id = 1,
            nombre = "John",
            apellido = "Doe",
            "1234567890",
            "5555555555",
            LocalDate.of(1990, 1, 1),
        )

        val reciclaje1 = Reciclaje(1,TipoMaterial.VERDE, 15.0, LocalDate.now(), null)
        val reciclaje2 = Reciclaje(2,TipoMaterial.AZUL, 5.0, LocalDate.now(), null)
        val reciclaje3 = Reciclaje(3,TipoMaterial.ROJO, 5.0, LocalDate.now(), null)

        var usuarios = mutableListOf(usuario)

        reciclaje1.ingresarUsuarioPorCedula("1234567890", usuarios)
        reciclaje2.ingresarUsuarioPorCedula("1234567890", usuarios)
        reciclaje3.ingresarUsuarioPorCedula("1234567890", usuarios)


        val reciclajes = listOf(reciclaje1, reciclaje2, reciclaje3)
        usuario.reciclajes = reciclajes.toMutableList()

        val puntos1 = Puntos(
            id = 1,
            cantidad = 0, // Asigna el valor necesario para cantidad
            usuario = usuario,
        )

        val recompensa1 = Recompensas(
            id = 1,
            nombre = "Recompensa 1",
            descripcion = "Descripción de la recompensa 1",
            minPuntos = 200
        )

        val recompensa2 = Recompensas(
            id = 2,
            nombre = "Recompensa 2",
            descripcion = "Descripción de la recompensa 2",
            minPuntos = 501
        )

        val puntosRecompensas1 = PuntosRecompensas(
            id = 1,
            punto = puntos1,
            recompensa = recompensa1,
        )


        val puntosRecompensas2 = PuntosRecompensas(
            id = 2,
            punto = puntos1,
            recompensa = recompensa2,
        )

        puntos1.codigo = puntos1.randomCode()
        puntos1.recompensas = mutableListOf(puntosRecompensas1, puntosRecompensas2)
        puntos1.cantidad=puntos1.asignarPuntos()
        assertEquals(700, puntos1.cantidad)
        puntos1.aplicarRecompensa(recompensa1)
        assertThrows(IllegalArgumentException::class.java){
            puntos1.aplicarRecompensa(recompensa2)
        }

        val usuario2 = Usuario(
            id = 2,
            nombre = "ANDRES",
            apellido = "MEJIA",
            "1006016709",
            "5555555555",
            LocalDate.of(2002, 3, 5),
        )

        val reciclaje21 = Reciclaje(4,TipoMaterial.VERDE, 15.0, LocalDate.now(),null)
        val reciclaje22 = Reciclaje(5,TipoMaterial.AZUL, 5.0, LocalDate.now(), null)
        val reciclaje23 = Reciclaje(6,TipoMaterial.ROJO, 1.0, LocalDate.now(), null)
        
        usuarios.add(usuario2)

        reciclaje21.ingresarUsuarioPorCedula("1006016709", usuarios)
        reciclaje22.ingresarUsuarioPorCedula("1006016709", usuarios)
        reciclaje23.ingresarUsuarioPorCedula("1006016709", usuarios)


        val reciclajes2 = listOf(reciclaje21, reciclaje22, reciclaje23)
        usuario2.reciclajes = reciclajes2.toMutableList()

        val puntos2 = Puntos(
            id = 2,
            cantidad = 0, // Asigna el valor necesario para cantidad
            usuario = usuario2,
        )

        val puntosRecompensas3 =PuntosRecompensas(
            id = 3,
            punto = puntos2,
            recompensa = recompensa1,
        )

        val puntosRecompensas4 =PuntosRecompensas(
            id = 4,
            punto = puntos2,
            recompensa = recompensa2,
        )

        puntos2.codigo = puntos2.randomCode()
        puntos2.recompensas = mutableListOf(puntosRecompensas3, puntosRecompensas4)
        puntos2.cantidad=puntos2.asignarPuntos()
        assertEquals(588, puntos2.cantidad)
        puntos2.aplicarRecompensa(recompensa1)
        assertThrows(IllegalArgumentException::class.java){
            puntos2.aplicarRecompensa(recompensa2)
        }

    }
}