/*Esta clase crea una actividad que sirve como menú principal*/

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

class MenuProfesores : AppCompatActivity(), View.OnClickListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_profesores)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menu_profesores))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val calificar = findViewById<Button>(R.id.btn_ctrl_escolar)
        val consultarCalificaciones = findViewById<Button>(R.id.btn_profesores)
        val regresar = findViewById<Button>(R.id.btn_alumnos)

        calificar.setOnClickListener(this)
        consultarCalificaciones.setOnClickListener(this)
        regresar.setOnClickListener(this)
    }

    override fun onClick(view : View?)
    {
        when(view?.id)
        {
            R.id.btn_ctrl_escolar ->
            {
                intent = Intent(applicationContext, Calificar::class.java)
                startActivity(intent)
            }
            R.id.btn_profesores ->
            {
                intent = Intent(applicationContext, ConsultarCalificaciones::class.java)
                startActivity(intent)
            }
            R.id.btn_alumnos ->
            {
                intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}