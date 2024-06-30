package com.andresgmx.myapplicationreusapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.andresgmx.myapplicationreusapp.db.models.*
import com.andresgmx.myapplicationreusapp.db.models.enums.Periodicidad
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoMaterial
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoVia
import java.time.LocalDate

class dbConnection(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DATABASE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS direccion")
        db.execSQL("DROP TABLE IF EXISTS cuenta")
        db.execSQL("DROP TABLE IF EXISTS usuario")
        db.execSQL("DROP TABLE IF EXISTS puntos")
        db.execSQL("DROP TABLE IF EXISTS recompensa")
        db.execSQL("DROP TABLE IF EXISTS reciclaje")
        db.execSQL("DROP TABLE IF EXISTS puntosrecompensas")
        db.execSQL("DROP TABLE IF EXISTS reporte")
        onCreate(db)
    }

    // DireccionCRUD

    fun createDireccion(direccion: Direccion): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("NumeroVia", direccion.numeroVia)
            put("TipoVia", direccion.tipoVia.toString())
            put("Complemento", direccion.complemento)
            put("Barrio", direccion.barrio)
            put("Comuna", direccion.comuna)
        }
        val id = db.insert("direccion", null, values)
        db.close()
        return id
    }

    fun getAllDirecciones(): List<Direccion> {
        val direcciones = ArrayList<Direccion>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM direccion", null)

        if (cursor.moveToFirst()) {
            do {
                val id =  cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val numeroVia = cursor.getInt(cursor.getColumnIndexOrThrow("NumeroVia"))
                val tipoVia = TipoVia.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("TipoVia")))
                val complemento = cursor.getString(cursor.getColumnIndexOrThrow("Complemento"))
                val barrio = cursor.getString(cursor.getColumnIndexOrThrow("Barrio"))
                val comuna = cursor.getString(cursor.getColumnIndexOrThrow("Comuna"))
                val direccion = Direccion(id,numeroVia, tipoVia, complemento, barrio, comuna)
                direcciones.add(direccion)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return direcciones
    }

    fun getDireccionById(direccionId: Long): Direccion? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM cuenta WHERE id = ?", arrayOf(direccionId.toString()))
        if (cursor.moveToFirst()) {
            val id =  cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val numeroVia = cursor.getInt(cursor.getColumnIndexOrThrow("NumeroVia"))
            val tipoVia = TipoVia.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("TipoVia")))
            val complemento = cursor.getString(cursor.getColumnIndexOrThrow("Complemento"))
            val barrio = cursor.getString(cursor.getColumnIndexOrThrow("Barrio"))
            val comuna = cursor.getString(cursor.getColumnIndexOrThrow("Comuna"))
            val direccion = Direccion(id,numeroVia, tipoVia, complemento, barrio, comuna)
            cursor.close()
            db.close()
            return direccion
        }
        cursor.close()
        db.close()
        return null
    }

    // Métodos delete y update para Direccion
    fun updateDireccion(direccion: Direccion): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("NumeroVia", direccion.numeroVia)
            put("TipoVia", direccion.tipoVia.toString())
            put("Complemento", direccion.complemento)
            put("Barrio", direccion.barrio)
            put("Comuna", direccion.comuna)
        }
        val rowsUpdated = db.update("direccion", values, "id = ?", arrayOf(direccion.id.toString()))
        db.close()
        return rowsUpdated
    }

    fun deleteDireccion(id: Long): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete("direccion", "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }

    // UsuarioCRUD

    fun createUsuario(usuario: Usuario): Long{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", usuario.nombre)
            put("apellido", usuario.apellido)
            put("cedula", usuario.cedula)
            put("telefono", usuario.telefono)
            put("fechaNacimiento", usuario.fechaNacimiento.toString())
            put("fechaRegistro", usuario.fechaRegistro.toString())
            put("cuenta", usuario.cuenta?.nombre)  // Suponiendo que 'nombreUsuario' está en la cuenta
            put("direccion", usuario.direccion?.id)  // Suponiendo que 'direccion' se refiere al id de la dirección
            put("puntos", usuario.puntos?.id)
        }

        val id = db.insert("usuario", null, values)
        db.close()
        return id
    }

    fun getAllUsers(): List<Usuario> {
        val users = ArrayList<Usuario>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM usuario", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"))
                val cedula = cursor.getString(cursor.getColumnIndexOrThrow("cedula"))
                val telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono"))
                val fechaNacimiento = LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("fechaNacimiento")))
                val fechaRegistro = LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("fechaRegistro")))
                val cuentaId = cursor.getLong(cursor.getColumnIndexOrThrow("cuenta"))

                val direccionId = cursor.getLong(cursor.getColumnIndexOrThrow("direccion"))
                val puntosId = cursor.getLong(cursor.getColumnIndexOrThrow("puntos"))

                // Obtener la cuenta asociada al usuario
                val cuenta = getCuentaById(cuentaId)
                val direccion = getDireccionById(direccionId)
                val puntos = getPuntosById(puntosId)

                val usuario = Usuario(id, nombre, apellido, cedula, telefono, fechaNacimiento, fechaRegistro, cuenta,  direccion, puntos)
                users.add(usuario)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return users
    }

    fun getUsuarioById(usuarioId: Long): Usuario? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM usuario WHERE id = ?", arrayOf(usuarioId.toString()))
        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"))
            val cedula = cursor.getString(cursor.getColumnIndexOrThrow("cedula"))
            val telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono"))
            val fechaNacimiento = LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("fechaNacimiento")))
            val fechaRegistro = LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("fechaRegistro")))
            val usuario = Usuario(id, nombre, apellido, cedula, telefono, fechaNacimiento, fechaRegistro)
            cursor.close()
            db.close()
            return usuario
        }
        cursor.close()
        db.close()
        return null
    }



    // Métodos delete y update para Usuario
    fun updateUsuario(usuario: Usuario): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", usuario.nombre)
            put("apellido", usuario.apellido)
            put("cedula", usuario.cedula)
            put("telefono", usuario.telefono)
            put("fechaNacimiento", usuario.fechaNacimiento.toString())
            put("fechaRegistro", usuario.fechaRegistro.toString())
        }
        val rowsUpdated = db.update("usuario", values, "id = ?", arrayOf(usuario.id.toString()))
        db.close()
        return rowsUpdated
    }

    fun deleteUsuario(id: Long): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete("usuario", "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }

    // CuentaCRUD
    fun createCuenta(cuenta: Cuenta, contraseña: String): Long {
        val db = this.writableDatabase
        val hashedPassword = Cuenta.hashPassword(contraseña)
        val values = ContentValues().apply {
            put("id", cuenta.id)
            put("nombre", cuenta.nombre)
            put("hashedPassword", hashedPassword)
            put("correo", cuenta.correo)
            put("usuario_id", cuenta.usuario.id)
        }
        val id = db.insert("cuenta", null, values)
        db.close()
        return id
    }

    fun getAllCuentas(): List<Cuenta> {
        val cuentas = ArrayList<Cuenta>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM cuenta", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val hashedPassword = cursor.getString(cursor.getColumnIndexOrThrow("hashedPassword"))
                val correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"))
                val usuarioId = cursor.getLong(cursor.getColumnIndexOrThrow("usuario_id"))
                val usuario = getUsuarioById(usuarioId)?: throw IllegalArgumentException("Usuario no encontrado para ID: $usuarioId")
                val cuenta = Cuenta(id, nombre, hashedPassword, correo, usuario)
                cuentas.add(cuenta)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return cuentas
    }

    fun getCuentaById(cuentaId: Long): Cuenta? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM cuenta WHERE id = ?", arrayOf(cuentaId.toString()))
        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val hashedPassword = cursor.getString(cursor.getColumnIndexOrThrow("hashedPassword"))
            val correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"))
            val usuarioId = cursor.getLong(cursor.getColumnIndexOrThrow("usuario_id"))
            val usuario = getUsuarioById(usuarioId)?: throw IllegalArgumentException("Usuario no encontrado para ID: $usuarioId")
            val cuenta = Cuenta(id, nombre, hashedPassword, correo, usuario)
            cursor.close()
            db.close()
            return cuenta
        }
        cursor.close()
        db.close()
        return null
    }

    fun updateCuenta(cuenta: Cuenta, contraseña: String): Int {
        val db = this.writableDatabase
        val hashedPassword = Cuenta.hashPassword(contraseña)
        val values = ContentValues().apply {
            put("id", cuenta.id)
            put("nombre", cuenta.nombre)
            put("hashedPassword", hashedPassword)
            put("correo", cuenta.correo)
            put("usuario_id", cuenta.usuario.id)
        }
        val rowsUpdated = db.update("cuenta", values, "id = ?", arrayOf(cuenta.id.toString()))
        db.close()
        return rowsUpdated
    }

    fun deleteCuenta(id: Long): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete("cuenta", "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }

    // PuntosCRUD
    fun createPuntos(puntos: Puntos): Long {
        val db = this.writableDatabase
        val codigo = puntos.randomCode()
        val values = ContentValues().apply {
            put("cantidad", puntos.cantidad)
            put("codigo", codigo)
            put("usuario_id", puntos.usuario.id)
        }
        val id = db.insert("puntos", null, values)
        for (puntosRecompensas in puntos.recompensas) {
            val valuesPR = ContentValues().apply {
                put("puntos_id", id)
                put("recompensa_id", puntosRecompensas.recompensa.id)
            }
            db.insert("puntos_recompensas", null, valuesPR)
        }
        db.close()
        return id
    }

    fun getAllPuntos(): List<Puntos> {
        val puntosList = ArrayList<Puntos>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM puntos", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val cantidad = cursor.getInt(cursor.getColumnIndexOrThrow("cantidad"))
                val codigo = cursor.getString(cursor.getColumnIndexOrThrow("codigo"))
                val usuarioId = cursor.getLong(cursor.getColumnIndexOrThrow("usuario_id"))
                val usuario = getUsuarioById(usuarioId) ?: throw IllegalArgumentException("Usuario no encontrado para ID: $usuarioId")  // Método que necesitas implementar para obtener un usuario por ID
                val puntos = Puntos(id, cantidad, codigo, usuario)
                puntosList.add(puntos)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return puntosList
    }

    fun getPuntosById(puntosId: Long): Puntos? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM puntos WHERE id = ?", arrayOf(puntosId.toString()))
        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val cantidad = cursor.getInt(cursor.getColumnIndexOrThrow("cantidad"))
            val codigo = cursor.getString(cursor.getColumnIndexOrThrow("codigo"))
            val usuarioId = cursor.getLong(cursor.getColumnIndexOrThrow("usuario_id"))
            val usuario = getUsuarioById(usuarioId) ?: throw IllegalArgumentException("Usuario no encontrado para ID: $usuarioId")
            cursor.close()
            db.close()
            return Puntos(id, cantidad, codigo, usuario)
        }
        cursor.close()
        db.close()
        return null
    }








    fun updatePuntos(puntos: Puntos): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("cantidad", puntos.cantidad)
        }
        val rowsUpdated = db.update("puntos", values, "id = ?", arrayOf(puntos.id.toString()))
        db.close()
        return rowsUpdated
    }

    fun asignarPuntos(puntos: Puntos): Int {
        val cantidad = puntos.asignarPuntos()
        puntos.cantidad += cantidad
        updatePuntos(puntos) // Actualiza la cantidad de puntos en la base de datos
        return cantidad
    }
    fun aplicarRecompensa(puntos: Puntos, recompensa: Recompensas) {
        puntos.aplicarRecompensa(recompensa)
        updatePuntos(puntos)
    }

    fun deletePuntos(id: Long): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete("puntos", "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }

    // RecompensaCRUD
    fun createRecompensa(recompensa: Recompensas): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("id", recompensa.id)
            put("nombre", recompensa.nombre)
            put("min_puntos", recompensa.minPuntos)
            put("descripcion", recompensa.descripcion)
        }
        val id = db.insert("recompensa", null, values)
        db.close()
        return id
    }

    fun getAllRecompensas(): List<Recompensas> {
        val recompensas = ArrayList<Recompensas>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM recompensa", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val minPuntos = cursor.getInt(cursor.getColumnIndexOrThrow("min_puntos"))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                val recompensa = Recompensas(id,  nombre, minPuntos, descripcion)
                recompensas.add(recompensa)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return recompensas
    }

    fun getRecompensaById(recompensaId: Long): Recompensas? {
        var recompensa: Recompensas? = null
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM recompensa WHERE id = ?", arrayOf(recompensaId.toString()))
        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val minPuntos = cursor.getInt(cursor.getColumnIndexOrThrow("min_puntos"))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
            recompensa = Recompensas(id, nombre, minPuntos, descripcion)
        }
        cursor.close()
        db.close()
        return recompensa
    }

    fun updateRecompensa(recompensa: Recompensas): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("id", recompensa.id)
            put("nombre", recompensa.nombre)
            put("min_puntos", recompensa.minPuntos)
            put("descripcion", recompensa.descripcion)
        }
        val rowsUpdated = db.update("recompensa", values, "id = ?", arrayOf(recompensa.id.toString()))
        db.close()
        return rowsUpdated
    }

    fun deleteRecompensa(id: Long): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete("recompensa", "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }

    // ReciclajeCRUD
    fun createReciclaje(reciclaje: Reciclaje): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("material", reciclaje.material.toString())
            put("peso", reciclaje.peso)
            put("fecha", reciclaje.fecha.toString())
            put("usuario_id", reciclaje.usuario?.cedula)
        }
        val id = db.insert("reciclaje", null, values)
        db.close()
        return id
    }

    fun getAllReciclajes(): List<Reciclaje> {
        val reciclajes = ArrayList<Reciclaje>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM reciclaje", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val materialStr = cursor.getString(cursor.getColumnIndexOrThrow("material"))
                val material = TipoMaterial.valueOf(materialStr)

                val peso = cursor.getDouble(cursor.getColumnIndexOrThrow("peso"))
                val fecha = LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("fecha")))
                val reciclaje = Reciclaje(id, material, peso, fecha)
                reciclajes.add(reciclaje)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return reciclajes
    }

    fun updateReciclaje(reciclaje: Reciclaje): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("material", reciclaje.material.toString())
            put("peso", reciclaje.peso)
            put("fecha", reciclaje.fecha.toString())
            put("usuario_id", reciclaje.usuario?.cedula)
        }
        val rowsUpdated = db.update("reciclaje", values, "id = ?", arrayOf(reciclaje.id.toString()))
        db.close()
        return rowsUpdated
    }

    fun deleteReciclaje(id: Long): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete("reciclaje", "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }

    // PuntosRecompensasCRUD
    fun createPuntosRecompensas(puntosRecompensas: PuntosRecompensas): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("id", puntosRecompensas.id)
            put("puntos_id", puntosRecompensas.punto.id)
            put("recompensa_id", puntosRecompensas.recompensa.id)
        }
        val id = db.insert("puntosrecompensas", null, values)
        db.close()
        return id
    }

    fun getAllPuntosRecompensas(): List<PuntosRecompensas> {
        val puntosRecompensasList = ArrayList<PuntosRecompensas>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM puntosrecompensas", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val puntosId = cursor.getLong(cursor.getColumnIndexOrThrow("puntos_id"))
                val recompensaId = cursor.getLong(cursor.getColumnIndexOrThrow("recompensa_id"))
                val punto = getPuntosById(puntosId) ?: throw IllegalArgumentException("Puntos no encontrado para ID: $puntosId")
                val recompensa = getRecompensaById(recompensaId) ?: throw IllegalArgumentException("Recompensa no encontrada para ID: $recompensaId")
                val puntosRecompensas = PuntosRecompensas(id, punto, recompensa)
                puntosRecompensasList.add(puntosRecompensas)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return puntosRecompensasList
    }

    fun updatePuntosRecompensas(puntosRecompensas: PuntosRecompensas): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("id", puntosRecompensas.id)
            put("puntos_id", puntosRecompensas.punto.id)
            put("recompensa_id", puntosRecompensas.recompensa.id)
        }
        val rowsUpdated = db.update("puntosrecompensas", values, "id = ?", arrayOf(puntosRecompensas.id.toString()))
        db.close()
        return rowsUpdated
    }

    fun deletePuntosRecompensas(id: Long): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete("puntosrecompensas", "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }

    // ReporteCRUD
    fun getReportes(fecha: LocalDate, periodicidad: Periodicidad): ReporteFiltrado {
        val reciclajes = getAllReciclajes()
        return Reporte.filtrarActividadesReciclaje(reciclajes, fecha, periodicidad)
    }




    companion object {


        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "db_reusapp"
        const val DATABASE_CREATE =
            "CREATE TABLE usuario (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT," +
                    "apellido TEXT," +
                    "cedula TEXT," +
                    "telefono TEXT," +
                    "fechaNacimiento TEXT," +
                    "fechaRegistro TEXT," +
                    "cuenta_id INTEGER," +
                    "direccion_id INTEGER," +
                    "puntos_id INTEGER" +
                    ");" +
                    "CREATE TABLE cuenta (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT," +
                    "hashedPassword TEXT," +
                    "correo TEXT," +
                    "usuario_id INTEGER," +
                    "FOREIGN KEY(usuario_id) REFERENCES usuario(id)" +
                    ");" +
                    "CREATE TABLE direccion (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "numeroVia INTEGER," +
                    "tipoVia TEXT," +
                    "complemento TEXT," +
                    "barrio TEXT," +
                    "comuna TEXT," +
                    "codigoPostal TEXT" +
                    ");" +
                    "CREATE TABLE reciclaje (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "material TEXT," +
                    "peso REAL," +
                    "fecha TEXT," +
                    "usuario_id INTEGER," +
                    "FOREIGN KEY(usuario_id) REFERENCES usuario(id)" +
                    ");" +
                    "CREATE TABLE recompensa (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT," +
                    "min_puntos INTEGER," +
                    "descripcion TEXT" +
                    ");" +
                    "CREATE TABLE puntos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "cantidad INTEGER," +
                    "codigo TEXT," +
                    "usuario_id INTEGER," +
                    "FOREIGN KEY(usuario_id) REFERENCES usuario(id)" +
                    ");" +
                    "CREATE TABLE puntosrecompensas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "puntos_id INTEGER," +
                    "recompensa_id INTEGER," +
                    "FOREIGN KEY(puntos_id) REFERENCES puntos(id)," +
                    "FOREIGN KEY(recompensa_id) REFERENCES recompensa(id)" +
                    ");"

    }
}