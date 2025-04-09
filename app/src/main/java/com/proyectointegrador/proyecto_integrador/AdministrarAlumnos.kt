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
    var dataManagerAlumnos : DataManager? = null
    var dataManagerCalificaciones : DataManager? = null

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
        val alumnos = findViewById<ListView>(R.id.alumnos)
        var selected = Alumno()

        dataManagerAlumnos = DataManager(applicationContext, resources.getString(R.string.db_alumnos))
        dataManagerCalificaciones = DataManager(applicationContext, resources.getString(R.string.db_calificaciones))

        editar.visibility = View.GONE
        borrar.visibility = View.GONE

        actualizarListaAlumnos()

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
            dataManagerCalificaciones!!.borrarCalificacionesPorAlumno(selected)
            val res : Int = dataManagerAlumnos!!.borrarAlumno(selected)

            if(res>0)
            {
                val texto : String = "Alumno ${selected} eliminado"
                val color : Int = resources.getColor(R.color.brat)
                SnackbarUtil.showSnackbar(applicationContext, view, texto, color)

                //actualiza el ListView de alumnos
                actualizarListaAlumnos()
                editar.visibility = View.GONE
                borrar.visibility = View.GONE
            }
            else
            {
                val texto : String = "Error al eliminar materia"
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
        dataManagerAlumnos!!.cerrar()
        dataManagerCalificaciones!!.cerrar()
        super.onPause()
    }

    override fun onResume()
    {
        dataManagerAlumnos!!.abrir()
        dataManagerCalificaciones!!.abrir()
        super.onResume()
    }

    fun actualizarListaAlumnos()
    {
        val alumnos = dataManagerAlumnos!!.leerAlumnos()

        val colores = arrayOf(resources.getColor(R.color.naranja), resources.getColor(R.color.naranjafuerte))
        val adaptador = CustomAdapterListView<Alumno>(applicationContext, alumnos, colores)

        val alumnosToDisplay = findViewById<ListView>(R.id.alumnos)
        alumnosToDisplay.adapter = adaptador
        alumnosToDisplay.isVerticalScrollBarEnabled = true
    }
}