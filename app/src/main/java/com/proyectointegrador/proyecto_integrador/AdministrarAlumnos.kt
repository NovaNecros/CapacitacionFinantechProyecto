/*Esta clase abre un menú para administrar a los alumnos*/

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

class AdministrarAlumnos : AppCompatActivity()
{
    var dataManager : DataManager? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_administrar_alumnos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.administrar_alumnos))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nuevo = findViewById<Button>(R.id.btn_nuevo)
        val editar = findViewById<Button>(R.id.btn_edit)
        val borrar = findViewById<Button>(R.id.btn_borrar)
        val regresar = findViewById<TextView>(R.id.btn_back)
        val alumnos = findViewById<ListView>(R.id.lista_alumnos)
        dataManager = DataManager(applicationContext, resources.getString(R.string.db_alumnos))

        var selected = Alumno()

        val colores = arrayOf(resources.getColor(R.color.naranja), resources.getColor(R.color.naranjafuerte))
        val alumnosToDisplay = dataManager!!.leerAlumnos()
        val adaptador = CustomAdapterListView<Alumno>(applicationContext, alumnosToDisplay, colores)
        alumnos.adapter = adaptador
        alumnos.isVerticalScrollBarEnabled = true

        editar.visibility = View.INVISIBLE
        borrar.visibility = View.INVISIBLE

        alumnos.setOnItemClickListener(
        { parent, view, pos, id ->

            selected = parent.getItemAtPosition(pos) as Alumno

            editar.text = resources.getString(R.string.btn_edit) + " a ${selected}"
            borrar.text = resources.getString(R.string.btn_delete) + " a ${selected}"
            editar.visibility = View.VISIBLE
            borrar.visibility = View.VISIBLE

            val texto : String = "Alumno ${selected} seleccionado"
            val color : Int = resources.getColor(R.color.brat)
            SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
        })

        nuevo.setOnClickListener(View.OnClickListener
        {
            intent = Intent(applicationContext, NuevoAlumno::class.java)
            startActivity(intent)
        })

        editar.setOnClickListener(View.OnClickListener
        {
            var data = selected.id.toString()
            val intent = Intent(applicationContext, EditarAlumno::class.java)
            intent.putExtra("idParaEditar", data)
            startActivity(intent)
        })

        borrar.setOnClickListener(View.OnClickListener
        { view ->
            val res : Int = dataManager!!.borrarAlumno(selected)
            if(res>0)
            {
                val texto : String = "Alumno ${selected} eliminado"
                val color : Int = resources.getColor(R.color.brat)
                SnackbarUtil.showSnackbar(applicationContext, view, texto, color)

                //reinicia la actividad para actualizar la lista
                intent = Intent(applicationContext, AdministrarAlumnos::class.java)
                startActivity(intent)
            }
            else
            {
                //inidca el ID que tiene el alumno que no se pudo eliminar
                //para ayudar a rastrear el error a la implemntación de la base de datos
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

    override fun onPause()
    {
        dataManager!!.cerrar()
        super.onPause()
    }

    override fun onResume()
    {
        dataManager!!.abrir()
        super.onResume()
    }
}