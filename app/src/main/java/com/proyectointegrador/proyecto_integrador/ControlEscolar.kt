/*Esta clase crea un menú para el control escolar*/

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

class ControlEscolar : AppCompatActivity(), View.OnClickListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_control_escolar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.control_escolar))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val alumnos = findViewById<Button>(R.id.btn_alumnos)
        val materias = findViewById<Button>(R.id.btn_materias)
        val regresar = findViewById<TextView>(R.id.btn_back)

        alumnos.setOnClickListener(this)
        materias.setOnClickListener(this)
        regresar.setOnClickListener(this)
    }

    override fun onClick(view : View?)
    {
        when(view?.id)
        {
            R.id.btn_alumnos ->
            {
                intent = Intent(applicationContext, AdministrarAlumnos::class.java)
                startActivity(intent)
            }
            R.id.btn_materias ->
            {
                intent = Intent(applicationContext, ::class.java)
                startActivity(intent)
            }
            R.id.btn_back ->
            {
                intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}