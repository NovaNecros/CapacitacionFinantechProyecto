/*Esta clase crea un menú para que los profesores asignen calificaciones*/

package com.proyectointegrador.proyecto_integrador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.TextView
import android.widget.Button
import android.widget.ListView
import android.view.View
import android.content.Intent

class PerfilAcademico : AppCompatActivity()
{
    var dataManagerAlumnos : DataManager? = null
    var dataManagerMaterias : DataManager? = null
    var dataManagerCalificaciones : DataManager? = null

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_academico)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.perfil_academico))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombre = findViewById<TextView>(R.id.titulo)
        val regresar = findViewById<Button>(R.id.btn_back)
        val calificaciones = findViewById<ListView>(R.id.lista_materias)
        val colores = arrayOf(resources.getColor(R.color.naranja), resources.getColor(R.color.naranjafuerte))

        dataManagerAlumnos = DataManager(applicationContext, resources.getString(R.string.db_alumnos))
        dataManagerMaterias = DataManager(applicationContext, resources.getString(R.string.db_materias))
        dataManagerCalificaciones = DataManager(applicationContext, resources.getString(R.string.db_calificaciones))

        val bundle = intent.extras
        val alumnoID = bundle?.getInt("alumno")
        nombre.text = dataManagerAlumnos!!.leerAlumno(alumnoID!!).toString()

        val calificacionesTexto = mutableListOf<String>()
        val calificacionesToDisplay = dataManagerCalificaciones!!.leerCalificacionesAlumno(alumnoID)

        for(calificacion in calificacionesToDisplay)
        {
            val materia = dataManagerMaterias!!.leerMateria(calificacion.materia.id)
            calificacionesTexto.add("${materia} - ${calificacion}")
        }

        val adaptador = CustomAdapterListView<String>(applicationContext, calificacionesTexto, colores)
        calificaciones.adapter = adaptador
        calificaciones.isVerticalScrollBarEnabled = true

        regresar.setOnClickListener(View.OnClickListener
        {
            intent = Intent(applicationContext, MenuAlumnos::class.java)
            startActivity(intent)
        })
    }

    override fun onPause()
    {
        dataManagerAlumnos!!.cerrar()
        dataManagerMaterias!!.cerrar()
        dataManagerCalificaciones!!.cerrar()
        super.onPause()
    }

    override fun onResume()
    {
        dataManagerAlumnos!!.abrir()
        dataManagerMaterias!!.abrir()
        dataManagerCalificaciones!!.abrir()
        super.onResume()
    }
}