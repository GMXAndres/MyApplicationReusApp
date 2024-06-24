package com.andresgmx.myapplicationreusapp.db

import com.andresgmx.myapplicationreusapp.db.models.*
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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

    //APLICAR DELETE Y UPDATE PARA DIRECCION



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

    ////

    fun getAllUsers(): List<Usuario> {
        val users = ArrayList<Usuario>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT nombre, apellido, puntos FROM usuario", null)
        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"))
                //val puntos = cursor.getInt(cursor.getColumnIndexOrThrow("puntos"))
                //users.add(Usuario(nombre, fechaNacimiento)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return users
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
                    ");"
    }
}
