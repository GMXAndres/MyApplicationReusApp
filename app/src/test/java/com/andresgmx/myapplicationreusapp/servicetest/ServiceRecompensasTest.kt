package com.andresgmx.myapplicationreusapp.dbtest

import androidx.test.core.app.ApplicationProvider

import com.andresgmx.myapplicationreusapp.db.IDbConnection
import com.andresgmx.myapplicationreusapp.db.dbConnection
import com.andresgmx.myapplicationreusapp.db.models.Direccion
import com.andresgmx.myapplicationreusapp.db.models.Recompensas
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoVia
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*

class ServiceRecompensasTest {

    private lateinit var dbConnection: IDbConnection

    @Before
    fun setUp() {
        dbConnection = mock(IDbConnection::class.java)
    }

    @Test
    fun testCreateRecompensas() {
        val recompensas = Recompensas(1, "Recompensa 1", 50, "Descripción 1")
        `when`(dbConnection.createRecompensa(recompensas)).thenReturn(1L)

        val id = dbConnection.createRecompensa(recompensas)
        assertNotNull(id)
        assertEquals(1L, id)
        verify(dbConnection).createRecompensa(recompensas)
    }

    @Test
    fun testGetAllRecompensas() {
        val recompensa1 = Recompensas(1, "Recompensa 1", 50, "Descripción 1")
        val recompensa2 = Recompensas(2, "Recompensa 2", 100, "Descripción 2")

        `when`(dbConnection.getAllRecompensas()).thenReturn(listOf(recompensa1, recompensa2))

        val recompensasList = dbConnection.getAllRecompensas()
        assertEquals(2, recompensasList.size)
        assertEquals(recompensa1, recompensasList[0])
        assertEquals(recompensa2, recompensasList[1])
        verify(dbConnection).getAllRecompensas()
    }

    @Test
    fun testGetRecompensasById() {
        val id = 1L
        val recompensas = Recompensas(id, "Recompensa 1", 50, "Descripción 1")

        `when`(dbConnection.getRecompensaById(id)).thenReturn(recompensas)

        val retrievedRecompensas = dbConnection.getRecompensaById(id)
        assertNotNull(retrievedRecompensas)
        assertEquals(recompensas.nombre, retrievedRecompensas?.nombre)
        verify(dbConnection).getRecompensaById(id)
    }

    @Test
    fun testUpdateRecompensas() {
        val recompensas = Recompensas(1, "Recompensa 1", 50, "Descripción 1")

        `when`(dbConnection.updateRecompensa(recompensas)).thenReturn(1)

        recompensas.minPuntos = 60

        val rowsUpdated = dbConnection.updateRecompensa(recompensas)

        assertEquals(1, rowsUpdated)
        verify(dbConnection).updateRecompensa(recompensas)
        assertEquals(60, recompensas.minPuntos)
    }

    @Test
    fun testDeleteRecompensas() {
        val id = 1L

        `when`(dbConnection.deleteRecompensa(id)).thenReturn(1)
        `when`(dbConnection.getRecompensaById(id)).thenReturn(null)

        val rowsDeleted = dbConnection.deleteRecompensa(id)

        assertEquals(1, rowsDeleted)
        verify(dbConnection).deleteRecompensa(id)

        val deletedRecompensas = dbConnection.getRecompensaById(id)

        assertNull(deletedRecompensas)
    }
}
