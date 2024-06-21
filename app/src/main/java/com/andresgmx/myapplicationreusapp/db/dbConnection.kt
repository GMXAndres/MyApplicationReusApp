package com.andresgmx.myapplicationreusapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.ArrayList

class dbConnection(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DATABASE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS usuario")
        db.execSQL("DROP TABLE IF EXISTS recompensa")
        db.execSQL("DROP TABLE IF EXISTS reciclaje")
        db.execSQL("DROP TABLE IF EXISTS reporte")
        onCreate(db)
    }

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
    fun getAllUsers(): List<ModelUser> {
        val users = ArrayList<ModelUser>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT nombre, apellido, puntos FROM usuario", null)
        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"))
                val puntos = cursor.getInt(cursor.getColumnIndexOrThrow("puntos"))
                users.add(ModelUser(nombre, apellido, puntos))
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

            "CREATE TABLE reporte (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "reciclaje_id INTEGER," +
            "periodicidad TEXT," +
            "FOREIGN KEY(reciclaje_id) REFERENCES reciclaje(id)" +
            ");"
    }

}