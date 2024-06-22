package com.andresgmx.myapplicationreusapp
import com.andresgmx.myapplicationreusapp.db.models.Direccion
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoVia
import org.junit.Test;
import org.junit.Assert.*

class DireccionTest {

    @Test
    fun testDireccionConstructor() {
        val direccion = Direccion(
            numeroVia = 123,
            tipoVia = TipoVia.CALLE,
            complemento = "Apt 4",
            barrio = "Centro",
            comuna = "Comuna 1",
            codigoPostal = "12345"
        )
        assertEquals(123, direccion.numeroVia)
        assertEquals(TipoVia.CALLE, direccion.tipoVia)
        assertEquals("Apt 4", direccion.complemento)
        assertEquals("Centro", direccion.barrio)
        assertEquals("Comuna 1", direccion.comuna)
        assertEquals("12345", direccion.codigoPostal)

    }
    @Test
    fun testDireccionSetters() {
        val direccion = Direccion(
            numeroVia = 123,
            tipoVia = TipoVia.DIAGONAL,
            complemento = "Apt 4",
            barrio = "Centro",
            comuna = "Comuna 1",
            codigoPostal = "12345"
        )

        direccion.numeroVia = 456
        direccion.tipoVia = TipoVia.AVENIDA
        direccion.complemento = "Apt 8"
        direccion.barrio = "Norte"
        direccion.comuna = "Comuna 2"
        direccion.codigoPostal = "54321"

        assertEquals(456, direccion.numeroVia)
        assertEquals(TipoVia.AVENIDA, direccion.tipoVia)
        assertEquals("Apt 8", direccion.complemento)
        assertEquals("Norte", direccion.barrio)
        assertEquals("Comuna 2", direccion.comuna)
        assertEquals("54321", direccion.codigoPostal)
    }
}