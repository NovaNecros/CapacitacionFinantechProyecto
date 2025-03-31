/*Esta clase gestiona la inserción, lectura y eliminación de personitas
en la base de datos con SQLite*/

package com.proyectointegrador.proyecto_integrador

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataManager(contexto : Context)
{
    val dbHelper : SQLiteOpenHelper = DBHelper(contexto)
    var baseDatos : SQLiteDatabase = dbHelper.writableDatabase

    fun abrir()
    {
        baseDatos = dbHelper.writableDatabase
    }

    fun cerrar()
    {
        baseDatos.close()
    }

    fun borrar()
    {
        dbHelper.onUpgrade(baseDatos, 1, 1)
    }

    fun guardarPersonita(fulanito : Alumno)
    {
        val valores = ContentValues()
        valores.put("id_personitas", fulanito.id)
        valores.put("nombre", fulanito.nombre)
        valores.put("apellidoP", fulanito.apellidoP)
        valores.put("apellidoM", fulanito.apellidoM)
        valores.put("fecha", fulanito.fecha)
        valores.put("genero", fulanito.generos.toString())

        baseDatos.insert("personitas", null, valores)
    }

    fun leerPersonitas() : Array<Alumno>
    {
        val alumnos = mutableListOf<Alumno>()
        val columnas = arrayOf("id_personitas", "nombre", "apellidoP", "apellidoM", "genero", "fecha")
        val cursor : Cursor = baseDatos.query("personitas", columnas, null, null, null, null, null)

        while(cursor.moveToNext())
        {
            val fulanito = Alumno()

            fulanito.id = cursor.getInt(0)
            fulanito.nombre = cursor.getString(1)
            fulanito.apellidoP = cursor.getString(2)
            fulanito.apellidoM = cursor.getString(3)
            fulanito.generos = cursor.getString(4)
            fulanito.fecha = cursor.getString(5)

            alumnos.add(fulanito)
        }

        cursor.close()

        return alumnos.toTypedArray()
    }

    fun borrarPersonita(fulanito: Alumno)
    : Int = baseDatos.delete("personitas", "id_personitas = ?", arrayOf(fulanito.id.toString()))

    //asigna el primer ID disponible al crear una btn_ctrl_escolar personita
    fun getNewID() : Int
    {
        val cursor = baseDatos.rawQuery("SELECT id_personitas FROM personitas ORDER BY id_personitas", null)

        if(cursor.count == 0)
        {
            cursor.close()
            return 1
        }

        var lastID : Int = 0

        while(cursor.moveToNext())
        {
            val currentID = cursor.getInt(0)

            if(currentID != lastID + 1)
            {
                cursor.close()
                return lastID + 1
            }
            lastID = currentID
        }

        cursor.close()
        return lastID + 1
    }
}