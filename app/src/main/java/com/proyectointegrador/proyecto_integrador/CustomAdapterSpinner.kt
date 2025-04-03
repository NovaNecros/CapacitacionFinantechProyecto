/*Esta clase crea un adaptador personalizado para Spinners*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomAdapterSpinner<T>(contexto : Context, private val data : Array<T>, val colores : Array<Int>)
    : ArrayAdapter<T>(contexto, R.layout.item_dropdown, R.id.textViewItem, data)
{

    override fun getView(pos : Int, convertView : View?, parent : ViewGroup) : View
    {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_dropdown, parent, false)
        val textViewItem = view.findViewById<TextView>(R.id.textViewItem)
        textViewItem.text = data[pos].toString().trim() //elimina espacios en blanco al inicio y al final

        //Alterna entre colores para facilitar la lectura
        val n = colores.size

        if(pos == 0)
        {
            view.setBackgroundColor(context.resources.getColor(R.color.plateado))
        }
        else for(i in 0 until n)
        {
            if(pos % n == i)
            {
                view.setBackgroundColor(colores[i])
            }
        }

        return view
    }
}
