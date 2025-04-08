/*En esta clase se definen las propiedades de una calificación asignada a un alumno para una materia*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context

class Calificacion()
{
    var id : Int = 0
    var alumno : Alumno = Alumno()
    var materia : Materia = Materia()
    var numero : Double = 0.0
    var nota : String = ""

    constructor(contexto : Context, alumno : Alumno, materia : Materia, numero : Double)
            : this()
    {
        this.id = DataManager(contexto, R.string.db_calificaciones.toString()).getNewID()
        this.alumno = alumno
        this.materia = materia
        this.numero = numero
        this.nota = calcularNota(numero)
    }

    fun calcularNota(numero : Double) : String
    {
        return ":)"
//        return when
//        {
//            numero >= 9.0 -> R.string.muy_bien.toString()
//            numero >= 7.5 && numero < 9.0 -> R.string.bien.toString()
//            numero >= 6.0 && numero < 7.5 -> R.string.suficiente.toString()
//            else -> R.string.no_aprobado.toString()
//        }
    }

    override fun toString() : String
    {
        return this.nota
    }
}