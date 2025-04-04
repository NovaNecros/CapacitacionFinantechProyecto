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
        var materia = Materia()
        val materias = findViewById<ListView>(R.id.lista_materias)

        regresar.setOnClickListener(View.OnClickListener
        {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        })
    }
}