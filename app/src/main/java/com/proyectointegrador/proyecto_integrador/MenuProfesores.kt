/*Esta clase crea un menú para el control escolar*/

package com.proyectointegrador.proyecto_integrador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.TextView
import android.widget.Button
import android.widget.Spinner
import android.view.View
import android.content.Intent

class MenuProfesores : AppCompatActivity()
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

        val calificar = findViewById<Button>(R.id.btn_calificar)
        val regresar = findViewById<TextView>(R.id.btn_back)

        val materias = findViewById<Spinner>(R.id.spinner_materias)
        val dataManagerMaterias : DataManager = DataManager(applicationContext, resources.getString(R.string.db_materias))
        val alumnos = findViewById<Spinner>(R.id.spinner_alumnos)
        val dataManagerAlumnos : DataManager = DataManager(applicationContext, resources.getString(R.string.db_alumnos))

        try
        {
            val colores = arrayOf(resources.getColor(R.color.azulchillon), resources.getColor(R.color.azulmetalico))
            val materiasToDisplay = dataManagerMaterias.leerMaterias()
            val adaptador = CustomAdapter<Materia>(applicationContext, materiasToDisplay, colores)
            materias.adapter = adaptador
            materias.isVerticalScrollBarEnabled = true
        }
        catch(ex : Exception)
        {
            val view = findViewById<View>(android.R.id.content)
            val texto : String = ex.message.toString()
            val color : Int = resources.getColor(R.color.rojosangre)
            SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
        }

        try
        {
            val colores = arrayOf(resources.getColor(R.color.azulmetalico), resources.getColor(R.color.azulchillon))
            val alumnosToDisplay = dataManagerAlumnos.leerMaterias()
            val adaptador = CustomAdapter<Materia>(applicationContext, alumnosToDisplay, colores)
            alumnos.adapter = adaptador
            alumnos.isVerticalScrollBarEnabled = true
        }
        catch(ex : Exception)
        {
            val view = findViewById<View>(android.R.id.content)
            val texto : String = ex.message.toString()
            val color : Int = resources.getColor(R.color.rojosangre)
            SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
        }

        calificar.setOnClickListener(View.OnClickListener
        {
            //TODO
        })

        regresar.setOnClickListener(View.OnClickListener
        {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        })
    }
}