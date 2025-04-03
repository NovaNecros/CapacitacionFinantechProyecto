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

class MainActivity : AppCompatActivity(), View.OnClickListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val controlEscolar = findViewById<Button>(R.id.btn_ctrl_escolar)
        val menuProfesores = findViewById<Button>(R.id.btn_profesores)
        val menuAlumnos = findViewById<TextView>(R.id.btn_alumnos)

        controlEscolar.setOnClickListener(this)
        menuProfesores.setOnClickListener(this)
        menuAlumnos.setOnClickListener(this)
    }

    override fun onClick(view : View?)
    {
        when(view?.id)
        {
            R.id.btn_ctrl_escolar ->
            {
                intent = Intent(applicationContext, ControlEscolar::class.java)
                startActivity(intent)
            }
            R.id.btn_profesores ->
            {
                intent = Intent(applicationContext, MenuProfesores::class.java)
                startActivity(intent)
            }
            R.id.btn_alumnos ->
            {
                //TODO
                //intent = Intent(applicationContext, MenuAlumnos::class.java)
                //startActivity(intent)
            }
        }
    }
}