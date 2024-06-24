package com.andresgmx.myapplicationreusapp

import com.andresgmx.myapplicationreusapp.db.Reporte
import com.andresgmx.myapplicationreusapp.db.models.Reciclaje
import com.andresgmx.myapplicationreusapp.db.models.Usuario
import com.andresgmx.myapplicationreusapp.db.models.enums.Periodicidad
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoMaterial
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class ReporteTest {


    // Generador de fechas aleatorias entre dos fechas dadas
    private fun randomDate(): LocalDate {
        val start = LocalDate.of(2024, 1, 1).toEpochDay()
        val end = LocalDate.of(2024, 6, 24).toEpochDay()
        val randomDay = LocalDate.ofEpochDay(Random.nextLong(start, end))
        return randomDay
    }

    @Test
    fun testFiltrarActividadesReciclaje() {
        val usuario = Usuario(
            nombre = "John",
            apellido = "Doe",
            cedula = "1234567890",
            telefono = "5555555555",
            fechaNacimiento = LocalDate.of(1990, 1, 1)
        )

        val reciclaje1 = Reciclaje(TipoMaterial.VERDE, 15.0, randomDate(), usuario)
        val reciclaje2 = Reciclaje(TipoMaterial.AZUL, 5.0, randomDate(), usuario)
        val reciclaje3 = Reciclaje(TipoMaterial.ROJO, 5.0, randomDate(), usuario)
        val reciclaje4 = Reciclaje(TipoMaterial.NARANJA, 10.0, randomDate(), usuario)
        val reciclaje5 = Reciclaje(TipoMaterial.AZUL, 20.0, randomDate(), usuario)
        val reciclaje6 = Reciclaje(TipoMaterial.GRIS, 8.0, randomDate(), usuario)
        val reciclaje7 = Reciclaje(TipoMaterial.VERDE, 12.0, randomDate(), usuario)
        val reciclaje8 = Reciclaje(TipoMaterial.AZUL, 18.0, randomDate(), usuario)
        val reciclaje9 = Reciclaje(TipoMaterial.AMARILLO, 6.0, randomDate(), usuario)
        val reciclaje10 = Reciclaje(TipoMaterial.VERDE, 8.0, randomDate(), usuario)

        val reciclajes = listOf(reciclaje1, reciclaje2, reciclaje3, reciclaje4, reciclaje5,
            reciclaje6, reciclaje7, reciclaje8, reciclaje9, reciclaje10)

        val reporteDiario = Reporte.filtrarActividadesReciclaje(reciclajes, LocalDate.now(), Periodicidad.DIARIO)
        println("Reporte Diario:" + reporteDiario)
        val reporteSemanal = Reporte.filtrarActividadesReciclaje(reciclajes, LocalDate.now(), Periodicidad.SEMANAL)
        println("Reporte Semanal:" + reporteSemanal)
        val reporteMensual = Reporte.filtrarActividadesReciclaje(reciclajes, LocalDate.now(), Periodicidad.MENSUAL)
        println("Reporte Mensual:" + reporteMensual)
        val reporteAnual = Reporte.filtrarActividadesReciclaje(reciclajes, LocalDate.now(), Periodicidad.ANUAL)
        println("Reporte Anual:" + reporteAnual)


    }
}