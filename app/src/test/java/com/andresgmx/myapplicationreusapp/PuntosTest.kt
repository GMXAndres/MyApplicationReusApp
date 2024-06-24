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
            nombre = "John",
            apellido = "Doe",
            "1234567890",
            "5555555555",
            LocalDate.of(1990, 1, 1),
        )

        val reciclaje1 = Reciclaje(TipoMaterial.VERDE, 15.0, LocalDate.now(),usuario)
        val reciclaje2 = Reciclaje(TipoMaterial.AZUL, 5.0, LocalDate.now(), usuario)
        val reciclaje3 = Reciclaje(TipoMaterial.ROJO, 5.0, LocalDate.now(), usuario)


        val reciclajes = listOf(reciclaje1, reciclaje2, reciclaje3)
        usuario.reciclajes = reciclajes.toMutableList()

        val puntos1 = Puntos(
            cantidad = 0, // Asigna el valor necesario para cantidad
            usuario = usuario,
        )

        val recompensa1 = Recompensas(
            nombre = "Recompensa 1",
            descripcion = "Descripción de la recompensa 1",
            minPuntos = 200
        )

        val recompensa2 = Recompensas(
            nombre = "Recompensa 2",
            descripcion = "Descripción de la recompensa 2",
            minPuntos = 501
        )

        val puntosRecompensas1 = PuntosRecompensas(
            punto = puntos1,
            recompensa = recompensa1,
        )

        val puntosRecompensas2 = PuntosRecompensas(
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
            nombre = "ANDRES",
            apellido = "MEJIA",
            "1234567890",
            "5555555555",
            LocalDate.of(2002, 3, 5),
        )

        val reciclaje21 = Reciclaje(TipoMaterial.VERDE, 15.0, LocalDate.now(),usuario2)
        val reciclaje22 = Reciclaje(TipoMaterial.AZUL, 5.0, LocalDate.now(), usuario2)
        val reciclaje23 = Reciclaje(TipoMaterial.ROJO, 1.0, LocalDate.now(), usuario2)


        val reciclajes2 = listOf(reciclaje21, reciclaje22, reciclaje23)
        usuario2.reciclajes = reciclajes2.toMutableList()

        val puntos2 = Puntos(
            cantidad = 0, // Asigna el valor necesario para cantidad
            usuario = usuario2,
        )

        val puntosRecompensas3 =PuntosRecompensas(
            punto = puntos2,
            recompensa = recompensa1,
        )

        val puntosRecompensas4 =PuntosRecompensas(
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