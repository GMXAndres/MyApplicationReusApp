package com.andresgmx.myapplicationreusapp.servicetest

import com.andresgmx.myapplicationreusapp.db.IDbConnection
import com.andresgmx.myapplicationreusapp.db.Reporte
import com.andresgmx.myapplicationreusapp.db.ReporteFiltrado
import com.andresgmx.myapplicationreusapp.db.models.Reciclaje
import com.andresgmx.myapplicationreusapp.db.models.enums.Periodicidad
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoMaterial

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDate

class ServiceReporteTest {

    private lateinit var dbConnection: IDbConnection

    @Before
    fun setUp() {
        dbConnection = mock(IDbConnection::class.java)
    }

    @Test
    fun testGetReportes() {

        val fechaActual = LocalDate.now()
        val reciclajes = listOf(
            Reciclaje(1, TipoMaterial.AZUL, 5.0, fechaActual.minusDays(1)),
            Reciclaje(2, TipoMaterial.VERDE, 8.0, fechaActual.minusDays(2)),
            Reciclaje(3, TipoMaterial.AMARILLO, 10.0, fechaActual.minusDays(3)),
            Reciclaje(4, TipoMaterial.ROJO, 3.0, fechaActual.minusDays(4)),
            Reciclaje(5, TipoMaterial.NARANJA, 7.0, fechaActual.minusDays(5)),
            Reciclaje(6, TipoMaterial.AZUL, 6.0, fechaActual.minusDays(6)),
        )

        `when`(dbConnection.getAllReciclajes()).thenReturn(reciclajes)

        val reporte = getReportes(fechaActual, Periodicidad.ANUAL)
        println(reporte)
        println(reporte.actividadesFiltradas.size)
        assertEquals(6, reporte.actividadesFiltradas.size)

    }

    private fun getReportes(fecha: LocalDate, periodicidad: Periodicidad): ReporteFiltrado {
        val reciclajes = dbConnection.getAllReciclajes()
        return Reporte.filtrarActividadesReciclaje(reciclajes, fecha, periodicidad)
    }
}