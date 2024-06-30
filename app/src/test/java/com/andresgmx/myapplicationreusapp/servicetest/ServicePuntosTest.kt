package com.andresgmx.myapplicationreusapp.servicetest

import com.andresgmx.myapplicationreusapp.db.IDbConnection
import com.andresgmx.myapplicationreusapp.db.models.Puntos
import com.andresgmx.myapplicationreusapp.db.models.PuntosRecompensas
import com.andresgmx.myapplicationreusapp.db.models.Reciclaje
import com.andresgmx.myapplicationreusapp.db.models.Recompensas
import com.andresgmx.myapplicationreusapp.db.models.Usuario
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoMaterial
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*
import java.time.LocalDate


class ServicePuntosTest {

    private lateinit var dbConnection: IDbConnection

    @Before
    fun setUp() {
        dbConnection = mock(IDbConnection::class.java)
    }

    @After
    fun tearDown() {
        // Aquí puedes realizar acciones de limpieza si es necesario
    }

    @Test
    fun testCreatePuntos() {



        val puntos = Puntos(1, 100, "ABCD1234", Usuario(1))
        `when`(dbConnection.createPuntos(puntos)).thenReturn(1L)
        val id = dbConnection.createPuntos(puntos)
        assertNotEquals(0, id)
    }

    @Test
    fun testGetAllPuntos() {
        // Configurar comportamiento del mock para el método getAllPuntos
        `when`(dbConnection.getAllPuntos()).thenReturn(
            listOf(
                Puntos(1, 100, "ABCD1234", Usuario(1)),

                Puntos(2, 150, "EFGH5678", Usuario(2))

            )
        )

        val puntosList = dbConnection.getAllPuntos()
        assertEquals(2, puntosList.size)
    }

    @Test
    fun testGetPuntosById() {
        // Configurar comportamiento del mock para el método getPuntosById
        val puntos = Puntos(1, 100, "ABCD1234", Usuario(11))
        val id = 1L
        `when`(dbConnection.getPuntosById(1)).thenReturn(puntos)

        val retrievedPuntos = dbConnection.getPuntosById(1)
        assertNotNull(retrievedPuntos)
        assertEquals(puntos.id, retrievedPuntos?.id)
    }

    @Test
    fun testUpdatePuntos() {
        // Configurar comportamiento del mock para el método updatePuntos
        val puntos = Puntos(1, 100, "ABCD1234", Usuario(1))
        `when`(dbConnection.updatePuntos(puntos)).thenReturn(1)

        val rowsUpdated = dbConnection.updatePuntos(puntos)
        assertEquals(1, rowsUpdated)
        verify(dbConnection).updatePuntos(puntos)
    }

    @Test
    fun testAsignarPuntos() {
        val usuario = Usuario(1, "Usuario 1", "Apellido 1", "1234567890")
        val reciclaje = Reciclaje(1, TipoMaterial.AZUL, 5.0,LocalDate.now(),usuario)
        usuario.reciclajes = listOf(reciclaje).toMutableList()
        val puntos = Puntos(1, 0, "", usuario)
        puntos.codigo = puntos.randomCode()

        `when`(dbConnection.asignarPuntos(puntos)).thenAnswer { invocation ->
            val puntosArgument = invocation.arguments[0] as Puntos
            puntosArgument.asignarPuntos()
        }


        val cantidadAsignada = dbConnection.asignarPuntos(puntos)
        println(cantidadAsignada)
        assertEquals(50, cantidadAsignada)
        assertEquals(1, usuario.reciclajes.size)
        assertEquals(puntos.id, usuario.reciclajes[0].id)
    }

    @Test
    fun testAplicarRecompensa() {
        // Crear un usuario con un reciclaje
        val usuario = Usuario(1, "Usuario 1", "Apellido 1", "1234567890")
        val reciclaje = Reciclaje(1, TipoMaterial.AZUL, 5.0, LocalDate.now(), usuario)
        usuario.reciclajes = mutableListOf(reciclaje)

        // Crear objeto Puntos y aplicar recompensa
        val puntos = Puntos(1, 0, "ABCD1234", usuario)
        puntos.codigo = puntos.randomCode()
        val recompensa = Recompensas(1, "Recompensa Test", 20,"Descripción de la recompensa")

        `when`(dbConnection.asignarPuntos(puntos)).thenAnswer { invocation ->
            val puntosArgument = invocation.arguments[0] as Puntos
            puntosArgument.asignarPuntos()
        }
        val cantidadAsignada = dbConnection.asignarPuntos(puntos)
        puntos.cantidad = cantidadAsignada
        val PuntosRecompensas = PuntosRecompensas(1, puntos, recompensa)
        puntos.recompensas.add(PuntosRecompensas)
        println(puntos.recompensas)
        puntos.aplicarRecompensa(recompensa)
        println(puntos.cantidad)
        dbConnection.updatePuntos(puntos)
        assertEquals(30, puntos.cantidad) // Verificar que los puntos se hayan reducido según la recompensa aplicada
        verify(dbConnection).updatePuntos(puntos)
    }

    @Test
    fun testDeletePuntos() {

        `when`(dbConnection.deletePuntos(eq(1L))).thenReturn(1)

        val rowsDeleted = dbConnection.deletePuntos(1)
        assertEquals(1, rowsDeleted)
        verify(dbConnection).deletePuntos(1)
    }

}





