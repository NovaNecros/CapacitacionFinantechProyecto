/*Esta clase crea un adaptador personalizado para el ListView*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomAdapterListView<T>(context : Context, val data : MutableList<T>, val colores : Array<Int>)
    : ArrayAdapter<T>(context, R.layout.item_listview, R.id.textViewItem, data)
{

    constructor(contexto : Context, data : Array<T>, colores : Array<Int>)
    : this(contexto, data.toMutableList(), colores)

    override fun getView(pos : Int, convertView : View?, parent : ViewGroup) : View
    {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false)
        val textViewItem = view.findViewById<TextView>(R.id.textViewItem)
        textViewItem.text = data[pos].toString().trim() //elimina espacios en blanco al inicio y al final

        //Alterna entre colores para facilitar la lectura
        val n = colores.size
        for(i in 0 until n)
        {
            if(i == pos % n)
            {
                view.setBackgroundColor(colores[i])
            }
        }

        return view
    }
}
