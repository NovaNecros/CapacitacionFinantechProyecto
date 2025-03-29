/*Esta clase interactúa directamente con la base de datos de SQLite*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(contexto : Context?) : SQLiteOpenHelper(contexto, contexto!!.getString(R.string.db_name), null, 1)
{
    override fun onCreate(db : SQLiteDatabase)
    {
        val comandoSQL : String = "CREATE TABLE personitas(id_personitas INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "nombre VARCHAR(100), apellidoP VARCHAR(100), apellidoM VARCHAR(100), " +
                "fecha VARCHAR(20), genero VARCHAR(100))"

        db.execSQL(comandoSQL)
    }

    override fun onUpgrade(db : SQLiteDatabase, oldVersion : Int, newVersion : Int)
    {
        val comandoSQL : String = "DROP TABLE IF EXISTS personitas"
        db.execSQL(comandoSQL)
        onCreate(db)
    }
}