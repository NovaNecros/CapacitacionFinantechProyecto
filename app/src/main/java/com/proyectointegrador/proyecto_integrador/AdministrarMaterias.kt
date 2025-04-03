/*Esta clase abre un menú para administrar las materias*/

package com.proyectointegrador.proyecto_integrador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.TextView
import android.widget.Button
import android.view.View

import android.content.Intent
import android.widget.ListView

class AdministrarMaterias : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_administrar_materias)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.administrar_materias))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nuevo = findViewById<Button>(R.id.btn_nuevo)
        val editar = findViewById<Button>(R.id.btn_edit)
        val borrar = findViewById<Button>(R.id.btn_borrar)
        val regresar = findViewById<TextView>(R.id.btn_back)
        val materias = findViewById<ListView>(R.id.materias)
        val dataManager = DataManager(applicationContext, resources.getString(R.string.db_materias))
        var selected = Materia()

        editar.visibility = View.INVISIBLE
        borrar.visibility = View.INVISIBLE

        try
        {
            val colores = arrayOf(resources.getColor(R.color.naranja), resources.getColor(R.color.naranjafuerte))
            val materiasToDisplay = dataManager.leerMaterias()
            val adaptador = CustomAdapterListView<Materia>(applicationContext, materiasToDisplay, colores)
            materias.adapter = adaptador
            materias.isVerticalScrollBarEnabled = true
        }
        catch(ex : Exception)
        {
            val view = findViewById<View>(android.R.id.content)
            val texto : String = ex.message.toString()
            val color : Int = resources.getColor(R.color.rojosangre)
            SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
        }

        materias.setOnItemClickListener(
        { parent, view, pos, id ->

            selected = parent.getItemAtPosition(pos) as Materia
            editar.visibility = View.VISIBLE
            borrar.visibility = View.VISIBLE
            val texto : String = "Materia ${selected} seleccionada"
            val color : Int = resources.getColor(R.color.brat)
            SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
        })

        nuevo.setOnClickListener(View.OnClickListener
        {
            intent = Intent(applicationContext, NuevaMateria::class.java)
            startActivity(intent)
        })

        editar.setOnClickListener(View.OnClickListener
        {
            var data = selected.id.toString()
            val intent = Intent(applicationContext, EditarMateria::class.java)
            intent.putExtra("idParaEditar", data) //manda el input a la segunda actividad
            startActivity(intent)
        })

        borrar.setOnClickListener(View.OnClickListener
        { view ->
            val res : Int = dataManager.borrarMateria(selected)
            if(res>0)
            {
                val texto : String = "Materia ${selected} eliminada"
                val color : Int = resources.getColor(R.color.brat)
                SnackbarUtil.showSnackbar(applicationContext, view, texto, color)

                //reinicia la actividad para actualizar la lista
                intent = Intent(applicationContext, AdministrarMaterias::class.java)
                startActivity(intent)
            }
            else
            {
                //inidca el ID que tiene la materia que no se pudo eliminar
                //para ayudar a rastrear el error a la implementación de la base de datos
                val texto : String = "Error al eliminar\nID=${res}"
                val color : Int = resources.getColor(R.color.rojosangre)
                SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
            }
        })

        regresar.setOnClickListener(View.OnClickListener
        {
            intent = Intent(applicationContext, ControlEscolar::class.java)
            startActivity(intent)
        })
    }
}