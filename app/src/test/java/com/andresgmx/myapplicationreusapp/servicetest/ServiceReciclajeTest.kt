import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import com.andresgmx.myapplicationreusapp.db.IDbConnection
import com.andresgmx.myapplicationreusapp.db.dbConnection
import com.andresgmx.myapplicationreusapp.db.models.Reciclaje
import com.andresgmx.myapplicationreusapp.db.models.Usuario
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoMaterial
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.time.LocalDate
import org.junit.Assert.*

class ServiceReciclajeTest {

    private lateinit var dbConnection: IDbConnection
    private lateinit var db: SQLiteDatabase

    @Before
    fun setUp() {
        // Crear un mock de IDbConnection
        dbConnection = mock(IDbConnection::class.java)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testCreateReciclaje() {
        val reciclaje = Reciclaje(
            1, TipoMaterial.VERDE, 10.5,
            LocalDate.now(), Usuario(1)
        )

        `when`(dbConnection.createReciclaje(reciclaje)).thenReturn(1L)

        val id = dbConnection.createReciclaje(reciclaje)
        assertNotEquals(-1, id)
        verify(dbConnection).createReciclaje(reciclaje)
    }

    @Test
    fun testGetAllReciclajes() {
        val reciclaje1 = Reciclaje(
            1, TipoMaterial.ROJO, 10.5,
            LocalDate.now(), Usuario(1)
        )
        val reciclaje2 = Reciclaje(
            2, TipoMaterial.GRIS, 5.0,
            LocalDate.now(), Usuario(2)
        )

        dbConnection.createReciclaje(reciclaje1)
        dbConnection.createReciclaje(reciclaje2)

        `when`(dbConnection.getAllReciclajes()).thenReturn(listOf(reciclaje1, reciclaje2))

        val reciclajes = dbConnection.getAllReciclajes()
        assertEquals(2, reciclajes.size)
        verify(dbConnection).getAllReciclajes()
    }

    @Test
    fun testUpdateReciclaje() {
        val reciclaje = Reciclaje(
            1, TipoMaterial.AZUL, 10.5,
            LocalDate.now(), Usuario(1)
        )

        dbConnection.createReciclaje(reciclaje)

        reciclaje.peso = 15.0

        `when`(dbConnection.updateReciclaje(reciclaje)).thenReturn(1)

        val rowsUpdated = dbConnection.updateReciclaje(reciclaje)
        assertEquals(1, rowsUpdated)
        verify(dbConnection).updateReciclaje(reciclaje)
    }

    @Test
    fun testDeleteReciclaje() {
        val reciclaje = Reciclaje(
            1, TipoMaterial.AMARILLO, 10.5,
            LocalDate.now(), Usuario(1)
        )

        dbConnection.createReciclaje(reciclaje)

        `when`(dbConnection.deleteReciclaje(1)).thenReturn(1)

        val rowsDeleted = dbConnection.deleteReciclaje(1)
        assertEquals(1, rowsDeleted)
        verify(dbConnection).deleteReciclaje(1)

        val deletedReciclaje = dbConnection.getReciclajeById(1)
        assertNull(deletedReciclaje)
    }
}
