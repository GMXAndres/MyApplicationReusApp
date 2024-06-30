package com.andresgmx.myapplicationreusapp.servicetest

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import com.andresgmx.myapplicationreusapp.db.IDbConnection
import com.andresgmx.myapplicationreusapp.db.models.*
import com.andresgmx.myapplicationreusapp.db.dbConnection
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoVia
import org.mockito.Mockito.*
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.Assert.*


class ServiceDireccionTest {

    private lateinit var dbConnection: IDbConnection

    @Before
    fun setUp() {
        // Crear un mock de IDbConnection
        dbConnection = mock(IDbConnection::class.java)
    }

    @After
    fun tearDown() {
        // No es necesario cerrar recursos en este caso
    }

    @Test
    fun testCreateDireccion() {
        val direccion = Direccion(1, 123, TipoVia.CALLE, "Apt 4B", "Centro", "Comuna 1")
        val id = dbConnection.createDireccion(direccion)
        println(id)
        assertNotNull(id)
        verify(dbConnection).createDireccion(direccion)
    }

    @Test
    fun testGetAllDirecciones() {
        // Configurar comportamiento del mock para el método getAllDirecciones
        `when`(dbConnection.getAllDirecciones()).thenReturn(listOf(
            Direccion(0, 123, TipoVia.CALLE, "Apt 4B", "Centro", "Comuna 1"),
            Direccion(1, 456, TipoVia.CARRERA, "House 5", "Norte", "Comuna 2")
        ))

        val direcciones = dbConnection.getAllDirecciones()
        assertEquals(2, direcciones.size)
        println(direcciones)
    }

    @Test
    fun testGetDireccionById() {
        val id = 1L
        val direccion = Direccion(id, 123, TipoVia.CALLE, "Apt 4B", "Centro", "Comuna 1")
        `when`(dbConnection.getDireccionById(anyLong())).thenReturn(direccion)

        val retrievedDireccion = dbConnection.getDireccionById(id)
        assertNotNull(retrievedDireccion)
        assertEquals(direccion.numeroVia, retrievedDireccion?.numeroVia)
        verify(dbConnection).getDireccionById(id)
    }

    @Test
    fun testUpdateDireccion() {
        val direccion = Direccion(1, 123, TipoVia.CALLE, "Apt 4B", "Centro", "Comuna 1")
        `when`(dbConnection.updateDireccion(direccion)).thenReturn(1)
        println(direccion)
        direccion.numeroVia = 456
        val rowsUpdated = dbConnection.updateDireccion(direccion)
        println(direccion)
        assertEquals(1, rowsUpdated)
        verify(dbConnection).updateDireccion(direccion)
        assertEquals(456, direccion.numeroVia)
    }

    @Test
    fun testDeleteDireccion() {
        val id = 1L
        `when`(dbConnection.deleteDireccion(id)).thenReturn(1)

        val rowsDeleted = dbConnection.deleteDireccion(id)
        assertEquals(1, rowsDeleted)
        verify(dbConnection).deleteDireccion(id)

        `when`(dbConnection.getDireccionById(id)).thenReturn(null)
        val deletedDireccion = dbConnection.getDireccionById(id)
        assertNull(deletedDireccion)
    }

}
