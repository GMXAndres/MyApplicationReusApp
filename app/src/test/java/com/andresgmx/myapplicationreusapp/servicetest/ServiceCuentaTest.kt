import com.andresgmx.myapplicationreusapp.db.IDbConnection
import com.andresgmx.myapplicationreusapp.db.models.Cuenta
import com.andresgmx.myapplicationreusapp.db.models.Usuario
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoVia
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.ArgumentMatchers.*
import org.mockito.ArgumentMatchers.*

class ServiceCuentaTest {

    private lateinit var dbConnection: IDbConnection

    @Before
    fun setUp() {
        // Mock del IDbConnection
        dbConnection = mock(IDbConnection::class.java)
    }

    @Test
    fun testCreateCuenta() {
        // Configurar comportamiento del mock para el método createCuenta
       // `when`(dbConnection.createCuenta(any(Cuenta::class.java), anyString())).thenReturn(1L)

        val cuenta = Cuenta(1, "Usuario1", "hashedPassword", "usuario1@example.com", Usuario(1))
        val contraseña = "password123"
        `when`(dbConnection.createCuenta(cuenta, contraseña)).thenReturn(1L)

        val id = dbConnection.createCuenta(cuenta, contraseña)
        assertNotNull(id)
        verify(dbConnection).createCuenta(cuenta, contraseña)
    }

    @Test
    fun testGetAllCuentas() {
        // Configurar comportamiento del mock para el método getAllCuentas
        `when`(dbConnection.getAllCuentas()).thenReturn(
            listOf(
                Cuenta(1, "Usuario1", "hashedPassword", "usuario1@example.com", Usuario(1, "Usuario 1")),
                Cuenta(2, "Usuario2", "hashedPassword", "usuario2@example.com", Usuario(2, "Usuario 2"))
            )
        )

        val cuentas = dbConnection.getAllCuentas()
        assertEquals(2, cuentas.size)
        verify(dbConnection).getAllCuentas()
    }

    @Test
    fun testGetCuentaById() {
        // Configurar comportamiento del mock para el método getCuentaById
        val cuentaId = 1L
        `when`(dbConnection.getCuentaById(cuentaId)).thenReturn(
            Cuenta(1, "Usuario1", "hashedPassword", "usuario1@example.com", Usuario(1, "Usuario 1"))
        )

        val cuenta = dbConnection.getCuentaById(cuentaId)
        assertNotNull(cuenta)
        assertEquals("Usuario1", cuenta?.nombre)
        verify(dbConnection).getCuentaById(cuentaId)
    }

    @Test
    fun testUpdateCuenta() {
        val contraseña = "@NnewPassword123"
        // Configurar comportamiento del mock para el método updateCuenta
        val cuenta = Cuenta(1, "Usuario1", Cuenta.hashPassword(contraseña), "usuario1@example.com", Usuario(1, "Usuario 1"))
        val nuevaContrasena = "@NnewPassword12"
        `when`(dbConnection.updateCuenta(cuenta, nuevaContrasena)).thenReturn(1)
        val rowsUpdated = dbConnection.updateCuenta(cuenta, nuevaContrasena)
        assertEquals(1, rowsUpdated)
        verify(dbConnection).updateCuenta(cuenta, nuevaContrasena)
    }

    @Test
    fun testDeleteCuenta() {
        val cuentaId = 1L
        `when`(dbConnection.deleteCuenta(cuentaId)).thenReturn(1)
        val rowsDeleted = dbConnection.deleteCuenta(cuentaId)
        assertEquals(1, rowsDeleted)
        verify(dbConnection).deleteCuenta(cuentaId)
    }
}
