/*Esta clase crea un adaptador personalizado para Spinners*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class CustomAdapterSpinner<T>(contexto : Context, resource : Int, data : MutableList<T>, val colores : Array<Int>)
    : ArrayAdapter<T>(contexto, resource, data)
{

    constructor(contexto : Context, resource: Int, data : Array<T>, colores : Array<Int>)
    : this(contexto, resource, data.toMutableList(), colores)

    val n = colores.size

    override fun getView(pos : Int, convertView : View?, parent : ViewGroup) : View
    {
        val view = super.getView(pos, convertView, parent)
        setColores(pos, view, colores)

        return view
    }

    override fun getDropDownView(pos : Int, convertView : View?, parent : ViewGroup) : View
    {
        val view = super.getDropDownView(pos, convertView, parent)
        setColores(pos, view, colores)

        return view
    }

    //Alterna entre colores para facilitar la lectura
    fun setColores(pos : Int, view : View, colores : Array<Int>)
    {
        if(view is TextView)
        {
            if(pos == 0)
            {
                view.setBackgroundColor(context.resources.getColor(R.color.plateado))
            }
            else for(i in 0 until n)
            {
                if(pos % n == i)
                {
                    view.setBackgroundColor(colores[i])
                    break
                }
            }
        }
    }
}
