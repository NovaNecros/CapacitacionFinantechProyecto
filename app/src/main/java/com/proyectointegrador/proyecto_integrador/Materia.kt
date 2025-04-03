/*En esta clase se definen las propiedades de una materia*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context

class Materia()
{
    var clave : Int = 0
    var nombre : String = ""
    var creditos : Int = 0

    constructor(contexto : Context, clave : Int, nombre : String, creditos : Int)
            : this()
    {
        this.clave = DataManager(contexto, contexto.resources.getString(R.string.db_materias)).getNewAlumnoID()
        this.nombre = nombre
        this.creditos = creditos
    }

    override fun toString() : String
    {
        return this.nombre
    }
}