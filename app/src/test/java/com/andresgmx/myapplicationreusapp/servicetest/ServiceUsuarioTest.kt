import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import com.andresgmx.myapplicationreusapp.db.IDbConnection
import com.andresgmx.myapplicationreusapp.db.dbConnection
import com.andresgmx.myapplicationreusapp.db.models.Usuario
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoVia
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.time.LocalDate

class ServiceUsuarioTest {

    private lateinit var dbConnection: IDbConnection


    @Before
    fun setUp() {
        // Crear un mock de IDbConnection
        dbConnection = mock(IDbConnection::class.java)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testCreateUsuario() {
        val usuario = Usuario(
            1, "Usuario1", "Apellido1", "1234567890",
            "1234567890", LocalDate.now(), LocalDate.now()
        )

        `when`(dbConnection.createUsuario(usuario)).thenReturn(1L)

        val id = dbConnection.createUsuario(usuario)
        assertNotEquals(-1, id)
        verify(dbConnection).createUsuario(usuario)
    }

    @Test
    fun testGetAllUsuarios() {
        val usuario1 = Usuario(
            1, "Usuario1", "Apellido1", "1234567890",
            "1234567890", LocalDate.now(), LocalDate.now()
        )
        val usuario2 = Usuario(
            2, "Usuario2", "Apellido2", "0987654321",
            "0987654321", LocalDate.now(), LocalDate.now()
        )

        dbConnection.createUsuario(usuario1)
        dbConnection.createUsuario(usuario2)

        `when`(dbConnection.getAllUsuarios()).thenReturn(listOf(usuario1, usuario2))

        val usuarios = dbConnection.getAllUsuarios()
        assertEquals(2, usuarios.size)
        verify(dbConnection).getAllUsuarios()
    }

    @Test
    fun testGetUsuarioById() {
        val usuario = Usuario(
            1, "Usuario1", "Apellido1", "1234567890",
            "1234567890", LocalDate.now(), LocalDate.now()
        )

        dbConnection.createUsuario(usuario)

        `when`(dbConnection.getUsuarioById(1)).thenReturn(usuario)

        val retrievedUsuario = dbConnection.getUsuarioById(1)
        assertNotNull(retrievedUsuario)
        assertEquals(usuario.nombre, retrievedUsuario?.nombre)
        verify(dbConnection).getUsuarioById(1)
    }

    @Test
    fun testUpdateUsuario() {
        val usuario = Usuario(
            1, "Usuario1", "Apellido1", "1234567890",
            "1234567890", LocalDate.now(), LocalDate.now()
        )

        dbConnection.createUsuario(usuario)

        usuario.nombre = "NuevoNombre"

        `when`(dbConnection.updateUsuario(usuario)).thenReturn(1)

        val rowsUpdated = dbConnection.updateUsuario(usuario)
        assertEquals(1, rowsUpdated)
        verify(dbConnection).updateUsuario(usuario)
    }

    @Test
    fun testDeleteUsuario() {
        val usuario = Usuario(
            1, "Usuario1", "Apellido1", "1234567890",
            "1234567890", LocalDate.now(), LocalDate.now()
        )

        dbConnection.createUsuario(usuario)

        `when`(dbConnection.deleteUsuario(1)).thenReturn(1)

        val rowsDeleted = dbConnection.deleteUsuario(1)
        assertEquals(1, rowsDeleted)
        verify(dbConnection).deleteUsuario(1)

        val deletedUsuario = dbConnection.getUsuarioById(1)
        assertNull(deletedUsuario)
    }
}
