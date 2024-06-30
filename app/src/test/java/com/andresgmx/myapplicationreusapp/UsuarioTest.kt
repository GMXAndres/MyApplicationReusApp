package com.andresgmx.myapplicationreusapp
import com.andresgmx.myapplicationreusapp.db.models.Cuenta
import com.andresgmx.myapplicationreusapp.db.models.Reciclaje
import com.andresgmx.myapplicationreusapp.db.models.Usuario
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoMaterial
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate


class UsuarioTest {
    @Test
    fun testObtenerYAgregarReciclajes() {
        // Definir un usuario para la prueba
        val usuario = Usuario(
            "John",
            "Doe",
            "1234567890",
            "5555555555",
            LocalDate.of(1990, 1, 1)
        )

        // Crear algunos reciclajes con diferentes usuarios
        val reciclaje1 = Reciclaje(TipoMaterial.VERDE, 10.0, LocalDate.now(), usuario)
        val reciclaje2 = Reciclaje(TipoMaterial.AZUL, 5.0, LocalDate.now(), usuario)
        val reciclaje3 = Reciclaje(TipoMaterial.AZUL, 5.0, LocalDate.now(), usuario)
        val reciclaje4 = Reciclaje(TipoMaterial.VERDE, 3.0, LocalDate.now(), Usuario("Jane", "Smith", "0987654321", "6666666666", LocalDate.of(1985, 5, 10), LocalDate.now()))

        usuario.agregarReciclaje(reciclaje1)
        usuario.agregarReciclaje(reciclaje2)
        usuario.agregarReciclaje(reciclaje3)




        val reciclajesFiltrados = usuario.obtenerReciclajes()

        assertEquals(3, reciclajesFiltrados.size)
        assertEquals(reciclaje1, reciclajesFiltrados[0])
        assertEquals(reciclaje2, reciclajesFiltrados[1])
        assertEquals(reciclaje3, reciclajesFiltrados[2])
        assertThrows(IllegalArgumentException::class.java){
            usuario.agregarReciclaje(reciclaje4)
        }

    }

    @Test
    fun testCalcularPesoTotalPorMaterial(){
        val usuario = Usuario(
            "John",
            "Doe",
            "1234567890",
            "5555555555",
            LocalDate.of(1990, 1, 1)
        )

        // Crear algunos reciclajes
        val reciclaje1 = Reciclaje(TipoMaterial.VERDE, 15.0, LocalDate.now(),usuario)
        val reciclaje2 = Reciclaje(TipoMaterial.AZUL, 5.0, LocalDate.now(), usuario)
        val reciclaje3 = Reciclaje(TipoMaterial.AZUL, 20.0, LocalDate.now(), usuario)
        val reciclaje4 = Reciclaje(TipoMaterial.ROJO, 5.0, LocalDate.now(), usuario)
        val reciclaje5 = Reciclaje(TipoMaterial.ROJO, 8.0, LocalDate.now(), usuario)
        val reciclaje6 = Reciclaje(TipoMaterial.NARANJA, 1.0, LocalDate.now(), usuario)

        val reciclajes = listOf(reciclaje1, reciclaje2, reciclaje3, reciclaje4, reciclaje5, reciclaje6).toMutableList()
        val (pesoTotal,pesoPorMaterial) = usuario.calcularPesoTotalPorMaterial(reciclajes)


        assertEquals(54.0, pesoTotal, 0.001)
        assertEquals(15.0,pesoPorMaterial[TipoMaterial.VERDE]?: 0.0, 0.001)
        assertEquals(25.0,pesoPorMaterial[TipoMaterial.AZUL]?: 0.0, 0.001)
        assertEquals(13.0,pesoPorMaterial[TipoMaterial.ROJO]?: 0.0, 0.001)
        assertEquals(0.0,pesoPorMaterial[TipoMaterial.GRIS]?: 0.0, 0.001)
        assertEquals(0.0,pesoPorMaterial[TipoMaterial.AMARILLO]?: 0.0, 0.001)
        assertEquals(1.0,pesoPorMaterial[TipoMaterial.NARANJA]?: 0.0, 0.001)
    }

}