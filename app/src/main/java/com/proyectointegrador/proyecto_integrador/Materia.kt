/*En esta clase se definen las propiedades de una materia*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context

class Materia()
{
    var id : Int = 0
    var clave : String = ""
    var nombre : String = ""
    var creditos : String = ""

    constructor(nombre : String) : this()
    {
        this.nombre = nombre
    }

    constructor(contexto : Context, clave : String, nombre : String, creditos : String)
            : this(nombre)
    {
        this.id = DataManager(contexto, contexto.resources.getString(R.string.db_materias)).getNewID()
        this.clave = clave
        this.creditos = creditos
    }

    override fun toString() : String
    {
        return this.nombre
    }
}