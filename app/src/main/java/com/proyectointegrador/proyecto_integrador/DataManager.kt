/*Esta clase gestiona la inserción, lectura y eliminación de personitas
en la base de datos con SQLite*/

package com.proyectointegrador.proyecto_integrador

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataManager(val contexto : Context, dbName : String)
{
    val dbHelper : SQLiteOpenHelper = DBHelper(contexto, dbName)
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

    fun guardarAlumno(alumno : Alumno)
    {
        val valores = ContentValues()
        valores.put("id", alumno.matricula)
        valores.put("nombre", alumno.nombre)
        valores.put("apellidoP", alumno.apellidoP)
        valores.put("apellidoM", alumno.apellidoM)
        valores.put("fecha", alumno.fecha)
        valores.put("genero", alumno.generos.toString())

        baseDatos.insert(contexto.resources.getString(R.string.table_alumnos), null, valores)
    }

    fun guardarMateria(materia : Materia)
    {
        val valores = ContentValues()
        valores.put("id", materia.clave)
        valores.put("nombre", materia.nombre)
        valores.put("creditos", materia.creditos)

        baseDatos.insert(contexto.resources.getString(R.string.table_materias), null, valores)
    }

    fun leerAlumnos() : Array<Alumno>
    {
        val alumnos = mutableListOf<Alumno>()
        val columnas = arrayOf("id", "nombre", "apellidoP", "apellidoM", "genero", "fecha")
        val cursor : Cursor = baseDatos.query(contexto.resources.getString(R.string.table_alumnos), columnas, null, null, null, null, null)

        while(cursor.moveToNext())
        {
            val alumno = Alumno()

            alumno.matricula = cursor.getInt(0)
            alumno.nombre = cursor.getString(1)
            alumno.apellidoP = cursor.getString(2)
            alumno.apellidoM = cursor.getString(3)
            alumno.generos = cursor.getString(4)
            alumno.fecha = cursor.getString(5)

            alumnos.add(alumno)
        }

        cursor.close()

        return alumnos.toTypedArray()
    }

    fun leerMaterias() : Array<Materia>
    {
        val materias = mutableListOf<Materia>()
        val columnas = arrayOf("id", "clave", "nombre", "creditos")
        val cursor : Cursor = baseDatos.query(contexto.resources.getString(R.string.table_materias), columnas, null, null, null, null, null)

        while(cursor.moveToNext())
        {
            val materia = Materia()

            materia.id = cursor.getInt(0)
            materia.clave = cursor.getString(1)
            materia.nombre = cursor.getString(2)
            materia.creditos = cursor.getString(3)

            materias.add(materia)
        }

        cursor.close()

        return materias.toTypedArray()
    }

    fun borrarAlumno(alumno : Alumno)
    : Int = baseDatos.delete(contexto.resources.getString(R.string.table_alumnos), "id = ?", arrayOf(alumno.matricula.toString()))

    fun borrarMateria(materia : Materia)
    : Int = baseDatos.delete(contexto.resources.getString(R.string.table_materias), "id = ?", arrayOf(materia.clave.toString()))

    //asigna el primer ID disponible al crear un nuevo alumno
    fun getNewAlumnoID() : Int
    {
        val cursor = baseDatos.rawQuery("SELECT matricula FROM" + contexto.resources.getString(R.string.table_alumnos) +
                " ORDER BY id", null)

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

    //asigna el primer ID disponible al crear una nueva materia
    fun getNewMateriaID() : Int
    {
        val cursor = baseDatos.rawQuery("SELECT id FROM" + contexto.resources.getString(R.string.table_materias) +
                " ORDER BY id", null)

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