/*Esta clase interactúa directamente con la base de datos de SQLite*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(val contexto : Context?, val dbName : String?) : SQLiteOpenHelper(contexto, dbName, null, 1)
{
    var tableName : String =
        if(dbName == contexto!!.resources.getString(R.string.db_alumnos)) { contexto.resources.getString(R.string.table_alumnos) }
    else if(dbName == contexto.resources.getString(R.string.db_materias)) { contexto.resources.getString(R.string.table_materias) }
    else { "" }

    override fun onCreate(db : SQLiteDatabase)
    {
        if(dbName == contexto!!.resources.getString(R.string.db_alumnos))
        {
            val comandoSQL : String = "CREATE TABLE ${tableName}(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "nombre VARCHAR(100), apellidoP VARCHAR(100), apellidoM VARCHAR(100), " +
                    "fecha VARCHAR(20), genero VARCHAR(100))"

            db.execSQL(comandoSQL)
        }
        else if(dbName == contexto.resources.getString(R.string.db_materias))
        {
            val comandoSQL : String = "CREATE TABLE ${tableName}(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "clave VARCHAR(20), nombre VARCHAR(100), creditos VARCHAR(10))"

            db.execSQL(comandoSQL)
        }


    }

    override fun onUpgrade(db : SQLiteDatabase, oldVersion : Int, newVersion : Int)
    {
        val comandoSQL : String = "DROP TABLE IF EXISTS ${tableName}"
        db.execSQL(comandoSQL)
        onCreate(db)
    }
}