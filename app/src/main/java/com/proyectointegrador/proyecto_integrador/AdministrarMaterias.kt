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
    var dataManagerMaterias : DataManager? = null
    var dataManagerCalificaciones : DataManager? = null

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
        var selected = Materia()

        dataManagerMaterias = DataManager(applicationContext, resources.getString(R.string.db_materias))
        dataManagerCalificaciones = DataManager(applicationContext, resources.getString(R.string.db_calificaciones))

        editar.visibility = View.INVISIBLE
        borrar.visibility = View.INVISIBLE

        actualizarListaMaterias()

        materias.setOnItemClickListener(
        { parent, view, pos, id ->

            selected = parent.getItemAtPosition(pos) as Materia

            editar.text = resources.getString(R.string.btn_edit) + " ${selected}"
            borrar.text = resources.getString(R.string.btn_delete) + " ${selected}"
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
            intent.putExtra("idParaEditar", data)
            startActivity(intent)
        })

        borrar.setOnClickListener(View.OnClickListener
        { view ->
            val resMateria : Int = dataManagerMaterias!!.borrarMateria(selected)
            val resCalifs : Int = dataManagerCalificaciones!!.borrarCalificacionesPorMateria(selected)

            if(resMateria>0 && resCalifs>0)
            {
                val texto : String = "Materia ${selected} eliminada"
                val color : Int = resources.getColor(R.color.brat)
                SnackbarUtil.showSnackbar(applicationContext, view, texto, color)

                //actualiza el ListView de materias
                actualizarListaMaterias()
                editar.visibility = View.INVISIBLE
                borrar.visibility = View.INVISIBLE
            }
            else if(resMateria==0)
            {
                //inidca el ID que tiene la materia que no se pudo eliminar
                //para ayudar a rastrear el error a la implementación de la base de datos
                val texto : String = "Error al eliminar\nID=${resMateria}"
                val color : Int = resources.getColor(R.color.rojosangre)
                SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
            }
            else
            {
                val texto : String = "Error al eliminar\nID=${resCalifs}"
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

    override fun onPause()
    {
        dataManagerMaterias!!.cerrar()
        super.onPause()
    }

    override fun onResume()
    {
        dataManagerMaterias!!.abrir()
        super.onResume()
    }

    fun actualizarListaMaterias()
    {
        val materias = dataManagerMaterias!!.leerMaterias()

        val colores = arrayOf(resources.getColor(R.color.naranja), resources.getColor(R.color.naranjafuerte))
        val adaptador = CustomAdapterListView<Materia>(applicationContext, materias, colores)

        val materiasToDisplay = findViewById<ListView>(R.id.lista_materias)
        materiasToDisplay.adapter = adaptador
        materiasToDisplay.isVerticalScrollBarEnabled = true
    }
}