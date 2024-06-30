package com.andresgmx.myapplicationreusapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.andresgmx.myapplicationreusapp.db.models.*
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoVia

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
                val numeroVia = cursor.getInt(cursor.getColumnIndexOrThrow("NumeroVia"))
                val tipoVia = TipoVia.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("TipoVia")))
                val complemento = cursor.getString(cursor.getColumnIndexOrThrow("Complemento"))
                val barrio = cursor.getString(cursor.getColumnIndexOrThrow("Barrio"))
                val comuna = cursor.getString(cursor.getColumnIndexOrThrow("Comuna"))
                val direccion = Direccion(numeroVia, tipoVia, complemento, barrio, comuna)
                direcciones.add(direccion)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return direcciones
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

    fun createUsuario(
        nombre: String,
        apellido: String,
        direccion: String,
        cedula: Long,
        correo: String,
        contrasena: String,
        telefono: Long,
        nombreUsuario: String
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("apellido", apellido)
            put("direccion", direccion)
            put("cedula", cedula)
            put("puntos", 0)
            put("telefono", telefono)
            put("nombreUsuario", nombreUsuario)
            put("contrasena", contrasena)
            put("correo", correo)
            put("fechaRegistro", Utils.getCurrentTime())
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
                val direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion"))
                val cedula = cursor.getLong(cursor.getColumnIndexOrThrow("cedula"))
                val puntos = cursor.getInt(cursor.getColumnIndexOrThrow("puntos"))
                val telefono = cursor.getLong(cursor.getColumnIndexOrThrow("telefono"))
                val nombreUsuario = cursor.getString(cursor.getColumnIndexOrThrow("nombreUsuario"))
                val contrasena = cursor.getString(cursor.getColumnIndexOrThrow("contrasena"))
                val correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"))
                val fechaRegistro = cursor.getString(cursor.getColumnIndexOrThrow("fechaRegistro"))
                val usuario = Usuario(id, nombre, apellido, direccion, cedula, puntos, telefono, nombreUsuario, contrasena, correo, fechaRegistro)
                users.add(usuario)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return users
    }

    // Métodos delete y update para Usuario
    fun updateUsuario(usuario: Usuario): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", usuario.nombre)
            put("apellido", usuario.apellido)
            put("direccion", usuario.direccion)
            put("cedula", usuario.cedula)
            put("puntos", usuario.puntos)
            put("telefono", usuario.telefono)
            put("nombreUsuario", usuario.nombreUsuario)
            put("contrasena", usuario.contrasena)
            put("correo", usuario.correo)
            put("fechaRegistro", usuario.fechaRegistro)
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
    fun createCuenta(cuenta: Cuenta): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("numero", cuenta.numero)
            put("tipo", cuenta.tipo.toString())
            put("banco", cuenta.banco)
            put("usuario_id", cuenta.usuarioId)
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
                val numero = cursor.getString(cursor.getColumnIndexOrThrow("numero"))
                val tipo = TipoCuenta.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("tipo")))
                val banco = cursor.getString(cursor.getColumnIndexOrThrow("banco"))
                val usuarioId = cursor.getLong(cursor.getColumnIndexOrThrow("usuario_id"))
                val cuenta = Cuenta(id, numero, tipo, banco, usuarioId)
                cuentas.add(cuenta)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return cuentas
    }

    fun updateCuenta(cuenta: Cuenta): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("numero", cuenta.numero)
            put("tipo", cuenta.tipo.toString())
            put("banco", cuenta.banco)
            put("usuario_id", cuenta.usuarioId)
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
        val values = ContentValues().apply {
            put("usuario_id", puntos.usuarioId)
            put("cantidad", puntos.cantidad)
            put("fecha", puntos.fecha)
        }
        val id = db.insert("puntos", null, values)
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
                val usuarioId = cursor.getLong(cursor.getColumnIndexOrThrow("usuario_id"))
                val cantidad = cursor.getInt(cursor.getColumnIndexOrThrow("cantidad"))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
                val puntos = Puntos(id, usuarioId, cantidad, fecha)
                puntosList.add(puntos)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return puntosList
    }

    fun updatePuntos(puntos: Puntos): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("usuario_id", puntos.usuarioId)
            put("cantidad", puntos.cantidad)
            put("fecha", puntos.fecha)
        }
        val rowsUpdated = db.update("puntos", values, "id = ?", arrayOf(puntos.id.toString()))
        db.close()
        return rowsUpdated
    }

    fun deletePuntos(id: Long): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete("puntos", "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }

    // RecompensaCRUD
    fun createRecompensa(recompensa: Recompensa): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("usuario_id", recompensa.usuarioId)
            put("nombre", recompensa.nombre)
            put("min_puntos", recompensa.minPuntos)
            put("descripcion", recompensa.descripcion)
        }
        val id = db.insert("recompensa", null, values)
        db.close()
        return id
    }

    fun getAllRecompensas(): List<Recompensa> {
        val recompensas = ArrayList<Recompensa>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM recompensa", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val usuarioId = cursor.getLong(cursor.getColumnIndexOrThrow("usuario_id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val minPuntos = cursor.getInt(cursor.getColumnIndexOrThrow("min_puntos"))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                val recompensa = Recompensa(id, usuarioId, nombre, minPuntos, descripcion)
                recompensas.add(recompensa)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return recompensas
    }

    fun updateRecompensa(recompensa: Recompensa): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("usuario_id", recompensa.usuarioId)
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
            put("usuario_id", reciclaje.usuarioId)
            put("material", reciclaje.material)
            put("peso", reciclaje.peso)
            put("fecha", reciclaje.fecha)
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
                val usuarioId = cursor.getLong(cursor.getColumnIndexOrThrow("usuario_id"))
                val material = cursor.getString(cursor.getColumnIndexOrThrow("material"))
                val peso = cursor.getFloat(cursor.getColumnIndexOrThrow("peso"))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
                val reciclaje = Reciclaje(id, usuarioId, material, peso, fecha)
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
            put("usuario_id", reciclaje.usuarioId)
            put("material", reciclaje.material)
            put("peso", reciclaje.peso)
            put("fecha", reciclaje.fecha)
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
            put("puntos_id", puntosRecompensas.puntosId)
            put("recompensa_id", puntosRecompensas.recompensaId)
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
                val puntosRecompensas = PuntosRecompensas(id, puntosId, recompensaId)
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
            put("puntos_id", puntosRecompensas.puntosId)
            put("recompensa_id", puntosRecompensas.recompensaId)
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
    fun createReporte(reporte: Reporte): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("reciclaje_id", reporte.reciclajeId)
            put("periodicidad", reporte.periodicidad)
        }
        val id = db.insert("reporte", null, values)
        db.close()
        return id
    }

    fun getAllReportes(): List<Reporte> {
        val reportes = ArrayList<Reporte>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM reporte", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val reciclajeId = cursor.getLong(cursor.getColumnIndexOrThrow("reciclaje_id"))
                val periodicidad = cursor.getString(cursor.getColumnIndexOrThrow("periodicidad"))
                val reporte = Reporte(id, reciclajeId, periodicidad)
                reportes.add(reporte)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return reportes
    }

    fun updateReporte(reporte: Reporte): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("reciclaje_id", reporte.reciclajeId)
            put("periodicidad", reporte.periodicidad)
        }
        val rowsUpdated = db.update("reporte", values, "id = ?", arrayOf(reporte.id.toString()))
        db.close()
        return rowsUpdated
    }

    fun deleteReporte(id: Long): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete("reporte", "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "db_reusapp"
        const val DATABASE_CREATE =
            "CREATE TABLE usuario (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "cedula INTEGER," +
                    "puntos INTEGER," +
                    "nombre TEXT," +
                    "apellido TEXT," +
                    "telefono INTEGER," +
                    "direccion TEXT," +
                    "fechaRegistro TEXT," +
                    "nombreUsuario TEXT," +
                    "contrasena TEXT," +
                    "correo TEXT" +
                    ");" +
                    "CREATE TABLE recompensa (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "usuario_id INTEGER," +
                    "nombre TEXT," +
                    "min_puntos INTEGER," +
                    "descripcion TEXT," +
                    "FOREIGN KEY(usuario_id) REFERENCES usuario(id)" +
                    ");" +
                    "CREATE TABLE reciclaje (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "usuario_id INTEGER," +
                    "material TEXT," +
                    "peso FLOAT," +
                    "fecha TEXT," +
                    "FOREIGN KEY(usuario_id) REFERENCES usuario(id)" +
                    ");" +
                    "CREATE TABLE direccion (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NumeroVia TEXT," +
                    "TipoVia TEXT," +
                    "Complemento TEXT," +
                    "Barrio TEXT," +
                    "Comuna TEXT" +
                    ");" +
                    "CREATE TABLE reporte (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "reciclaje_id INTEGER," +
                    "periodicidad TEXT," +
                    "FOREIGN KEY(reciclaje_id) REFERENCES reciclaje(id)" +
                    ");" +
                    "CREATE TABLE cuenta (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "numero TEXT," +
                    "tipo TEXT," +
                    "banco TEXT," +
                    "usuario_id INTEGER," +
                    "FOREIGN KEY(usuario_id) REFERENCES usuario(id)" +
                    ");" +
                    "CREATE TABLE puntos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "usuario_id INTEGER," +
                    "cantidad INTEGER," +
                    "fecha TEXT," +
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

