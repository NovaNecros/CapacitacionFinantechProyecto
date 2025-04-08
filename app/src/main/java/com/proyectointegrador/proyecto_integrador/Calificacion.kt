/*En esta clase se definen las propiedades de una calificación asignada a un alumno para una materia*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context

class Calificacion()
{
    var contexto : Context? = null
    var id : Int = 0
    var alumno : Alumno = Alumno()
    var materia : Materia = Materia()
    var numero : Double = 0.0
    var nota : String = ""

    constructor(contexto : Context, alumno : Alumno, materia : Materia, numero : Double)
            : this()
    {
        this.contexto = contexto
        this.id = DataManager(contexto, contexto.resources.getString(R.string.db_calificaciones)).getNewID()
        this.alumno = alumno
        this.materia = materia
        this.numero = numero
        this.nota = calcularNota(numero)
    }

    fun calcularNota(numero : Double) : String
    {
        return when
        {
            numero >= 9.0 -> contexto!!.resources.getString(R.string.muy_bien)
            numero >= 7.5 -> contexto!!.resources.getString(R.string.bien)
            numero >= 6.0 -> contexto!!.resources.getString(R.string.suficiente)
            else -> contexto!!.resources.getString(R.string.no_aprobado)
        }
    }

    override fun toString() : String
    {
        return this.nota
    }
}