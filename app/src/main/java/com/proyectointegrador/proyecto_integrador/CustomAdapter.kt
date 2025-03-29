/*Esta clase crea un adaptador personalizado para el ListView*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class CustomAdapter(context : Context, private val data : Array<Personita>
) : ArrayAdapter<Personita>(context, R.layout.item_listview, R.id.textViewItem, data)
{

    override fun getView(pos : Int, convertView : View?, parent : ViewGroup) : View
    {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false)
        val textViewItem = view.findViewById<TextView>(R.id.textViewItem)
        textViewItem.text = data[pos].toString().trim() //elimina espacios en blanco al inicio y al final

        //Alterna entre dos colores para facilitar la lectura
        if(pos % 2 == 0)
        {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.moradofuerte))
        }
        else
        {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.violeta))
        }

        return view
    }
}
