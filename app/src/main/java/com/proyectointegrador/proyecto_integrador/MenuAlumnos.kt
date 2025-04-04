/*Esta clase crea un menú para que los profesores asignen calificaciones*/

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

class MenuAlumnos : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_alumnos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menu_alumnos))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ingresar = findViewById<Button>(R.id.btn_matricula)
        val regresar = findViewById<TextView>(R.id.btn_back)

        var alumno = Alumno()

        ingresar.setOnClickListener(View.OnClickListener
        {
            intent = Intent(applicationContext, PerfilAcademico::class.java)
            startActivity(intent)
        })

        regresar.setOnClickListener(View.OnClickListener
        {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        })
    }
}