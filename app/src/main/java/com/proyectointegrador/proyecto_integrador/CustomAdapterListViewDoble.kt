/*Esta clase crea un adaptador personalizado para el ListView de las calificaciones*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CustomAdapterListViewDoble<T, E>(contexto : Context, val data1 : MutableList<E>, val data2 : MutableList<T>, colores : Array<Int>)
    : CustomAdapterListView<T>(contexto, data2, colores)
{

    constructor(contexto : Context, data1 : Array<E>, data2 : Array<T>, colores : Array<Int>)
            : this(contexto, data1.toMutableList(), data2.toMutableList(), colores)

    override fun getView(pos : Int, convertView : View?, parent : ViewGroup) : View
    {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false)
        val textViewItem = view.findViewById<TextView>(R.id.textViewItem)
        textViewItem.text = data[pos].toString().trim() //elimina espacios en blanco al inicio y al final

        setColores(pos, view)

        return view
    }
}
