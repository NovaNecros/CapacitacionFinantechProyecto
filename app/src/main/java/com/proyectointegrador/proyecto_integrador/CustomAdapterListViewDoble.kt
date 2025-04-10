/*Esta clase crea un adaptador personalizado para el ListView de las calificaciones*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CustomAdapterListViewDoble<T, E>(contexto : Context, val data1 : MutableList<T>, val data2 : MutableList<E>, colores : Array<Int>)
    : CustomAdapterListView<E>(contexto, data2, colores)
{

    constructor(contexto : Context, data1 : Array<T>, data2 : Array<E>, colores : Array<Int>)
            : this(contexto, data1.toMutableList(), data2.toMutableList(), colores)

    override fun getView(pos : Int, convertView : View?, parent : ViewGroup) : View
    {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false)
        val textViewItem = view.findViewById<TextView>(R.id.textViewItem)
        textViewItem.text = data1[pos].toString().trim() + " - " + data2[pos].toString().trim()

        setColores(pos, view)

        return view
    }
}
