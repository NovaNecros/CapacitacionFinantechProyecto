/*Esta clase crea un menú para que los profesores asignen calificaciones*/

package com.proyectointegrador.proyecto_integrador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.TextView
import android.widget.Button
import android.widget.AdapterView
import android.widget.ListView
import android.view.View
import android.content.Intent

class PerfilAcademico : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
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

        val regresar = findViewById<TextView>(R.id.btn_back)
        val materias = findViewById<ListView>(R.id.lista_materias)

        val colores = arrayOf(resources.getColor(R.color.naranja), resources.getColor(R.color.naranjafuerte))
        val dataManager = DataManager(applicationContext, resources.getString(R.string.db_materias))
        val materiasToDisplay = dataManager.leerMaterias()

        val adaptador = CustomAdapterListView<Materia>(applicationContext, materiasToDisplay, colores)
        materias.adapter = adaptador
        materias.isVerticalScrollBarEnabled = true

        regresar.setOnClickListener(View.OnClickListener
        {
            intent = Intent(applicationContext, MenuAlumnos::class.java)
            startActivity(intent)
        })
    }
}