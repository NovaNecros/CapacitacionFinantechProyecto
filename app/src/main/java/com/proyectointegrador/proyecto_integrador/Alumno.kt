/*En esta clase se definen las propiedades de un alumno*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context

class Alumno()
{
    var id : Int = 0
    var matricula : String = ""
    var nombre : String = ""
    var apellidoP : String = ""
    var apellidoM : String = ""
    var generos : String = ""
    var fecha : String = ""

    //Este constructor se usa para crear un alumno artificial para darle una opción de default al spinner
    constructor(nombre : String) : this()
    {
        this.nombre = nombre
    }

    constructor(contexto : Context, matricula : String, nombre : String, apellidoP : String, apellidoM : String, generos : String, fecha : String)
            : this(nombre)
    {
        this.id = DataManager(contexto, contexto.resources.getString(R.string.db_alumnos)).getNewID()
        this.matricula = matricula
        this.apellidoP = apellidoP
        this.apellidoM = apellidoM
        this.generos = generos
        this.fecha = fecha
    }

    override fun toString() : String
    {
        return this.nombre
    }
}