/*En esta clase se definen las propiedades de una personita*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context

class Personita()
{
    var id : Int = 0
    var nombre : String = ""
    var apellidoP : String = ""
    var apellidoM : String = ""
    var generos : String = ""
    var fecha : String = ""

    constructor(contexto : Context, nombre : String, apellidoP : String, apellidoM : String, generos : String, fecha : String)
            : this()
    {
        this.id = DataManager(contexto).getNewID()
        this.nombre = nombre
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