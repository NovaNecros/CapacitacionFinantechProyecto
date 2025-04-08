/*Esta clase gestiona la inserción, lectura y eliminación de personitas
en la base de datos con SQLite*/

package com.proyectointegrador.proyecto_integrador

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataManager(contexto : Context, dbName : String)
{
    val dbHelper  : SQLiteOpenHelper = DBHelper(contexto, dbName)
    var baseDatos : SQLiteDatabase = dbHelper.writableDatabase
    val tableName : String = when(dbName)
    {
        contexto.resources.getString(R.string.db_alumnos) -> contexto.resources.getString(R.string.table_alumnos)
        contexto.resources.getString(R.string.db_materias) -> contexto.resources.getString(R.string.table_materias)
        contexto.resources.getString(R.string.db_calificaciones) -> contexto.resources.getString(R.string.table_calificaciones)
        else -> ""
    }

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

        valores.put("id", alumno.id)
        valores.put("matricula", alumno.matricula)
        valores.put("nombre", alumno.nombre)
        valores.put("apellidoP", alumno.apellidoP)
        valores.put("apellidoM", alumno.apellidoM)
        valores.put("fecha", alumno.fecha)
        valores.put("genero", alumno.generos.toString())

        baseDatos.insert(tableName, null, valores)
    }

    fun guardarMateria(materia : Materia)
    {
        val valores = ContentValues()

        valores.put("id", materia.id)
        valores.put("clave", materia.clave)
        valores.put("nombre", materia.nombre)
        valores.put("creditos", materia.creditos)

        baseDatos.insert(tableName, null, valores)
    }

    fun guardarCalificacion(calificacion : Calificacion)
    {
        val valores = ContentValues()

        valores.put("id", calificacion.id)
        valores.put("alumno", calificacion.alumno.id)
        valores.put("materia", calificacion.materia.id)
        valores.put("numero", calificacion.numero)
        valores.put("nota", calificacion.nota)

        baseDatos.insert(tableName, null, valores)
    }

    fun leerAlumnos() : Array<Alumno>
    {
        val alumnos = mutableListOf<Alumno>()
        val columnas = arrayOf("id", "matricula", "nombre", "apellidoP", "apellidoM", "genero", "fecha")
        val cursor : Cursor = baseDatos.query(tableName, columnas, null, null, null, null, null)

        while(cursor.moveToNext())
        {
            val alumno = Alumno()

            alumno.id = cursor.getInt(0)
            alumno.matricula = cursor.getString(1)
            alumno.nombre = cursor.getString(2)
            alumno.apellidoP = cursor.getString(3)
            alumno.apellidoM = cursor.getString(4)
            alumno.generos = cursor.getString(5)
            alumno.fecha = cursor.getString(6)

            alumnos.add(alumno)
        }

        cursor.close()

        return alumnos.toTypedArray()
    }

    fun leerAlumno(id : Int) : Alumno
    {
        val alumno = Alumno()
        val columnas = arrayOf("id", "matricula", "nombre", "apellidoP", "apellidoM", "genero", "fecha")
        val cursor : Cursor = baseDatos.query(tableName, columnas, "id = ?", arrayOf(id.toString()), null, null, null)

        cursor.moveToFirst()
        alumno.id = cursor.getInt(0)
        alumno.matricula = cursor.getString(1)
        alumno.nombre = cursor.getString(2)
        alumno.apellidoP = cursor.getString(3)
        alumno.apellidoM = cursor.getString(4)
        alumno.generos = cursor.getString(5)
        alumno.fecha = cursor.getString(6)
        cursor.close()

        return alumno
    }

    fun leerMaterias() : Array<Materia>
    {
        val materias = mutableListOf<Materia>()
        val columnas = arrayOf("id", "clave", "nombre", "creditos")
        val cursor : Cursor = baseDatos.query(tableName, columnas, null, null, null, null, null)

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

    fun leerMateria(id : Int) : Materia
    {
        val materia = Materia()
        val columnas = arrayOf("id", "clave", "nombre", "creditos")
        val cursor : Cursor = baseDatos.query(tableName, columnas, "id = ?", arrayOf(id.toString()), null, null, null)

        cursor.moveToFirst()
        materia.id = cursor.getInt(0)
        materia.clave = cursor.getString(1)
        materia.nombre = cursor.getString(2)
        materia.creditos = cursor.getString(3)
        cursor.close()

        return materia
    }

    fun leerCalificaciones() : Array<Calificacion>
    {
        val calificaciones = mutableListOf<Calificacion>()
        val columnas = arrayOf("id", "alumno", "materia", "numero", "nota")
        val cursor : Cursor = baseDatos.query(tableName, columnas, null, null, null, null, null)

        while(cursor.moveToNext())
        {
            val calificacion = Calificacion()

            calificacion.id = cursor.getInt(0)
            calificacion.alumno = leerAlumno(cursor.getInt(1))
            calificacion.materia = leerMateria(cursor.getInt(2))
            calificacion.numero = cursor.getDouble(3)
            calificacion.nota = cursor.getString(4)

            calificaciones.add(calificacion)
        }

        cursor.close()

        return calificaciones.toTypedArray()
    }

    fun leerCalificacion(id : Int) : Calificacion
    {
        val calificacion = Calificacion()
        val columnas = arrayOf("id", "alumno", "materia", "numero", "nota")
        val cursor : Cursor = baseDatos.query(tableName, columnas, "id = ?", arrayOf(id.toString()), null, null, null)

        cursor.moveToFirst()
        calificacion.id = cursor.getInt(0)
        calificacion.alumno = leerAlumno(cursor.getInt(1))
        calificacion.materia = leerMateria(cursor.getInt(2))
        calificacion.numero = cursor.getDouble(3)
        calificacion.nota = cursor.getString(4)
        cursor.close()

        return calificacion
    }

    fun borrarAlumno(alumno : Alumno)
    : Int = baseDatos.delete(tableName, "id = ?", arrayOf(alumno.id.toString()))

    fun borrarMateria(materia : Materia)
    : Int = baseDatos.delete(tableName, "id = ?", arrayOf(materia.id.toString()))

    fun borrarCalificacion(calificacion : Calificacion)
    : Int = baseDatos.delete(tableName, "id = ?", arrayOf(calificacion.id.toString()))

    //asigna el primer ID disponible al nuevo dato
    fun getNewID() : Int
    {
        val cursor = baseDatos.rawQuery("SELECT id FROM ${tableName} ORDER BY id", null)

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