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

        var materia = Materia()
        var alumno = Alumno()

        val materias = findViewById<Spinner>(R.id.spinner_materias)
        val alumnos = findViewById<Spinner>(R.id.spinner_alumnos)

        alumnos.visibility = View.INVISIBLE
        calificar.visibility = View.INVISIBLE

        try
        {
            val dataManager = DataManager(applicationContext, resources.getString(R.string.db_materias))
            val colores = arrayOf(resources.getColor(R.color.azulchillon), resources.getColor(R.color.azulmetalico))
            val materiasToDisplay = dataManager.leerMaterias()
            val adaptador = CustomAdapterSpinner<Materia>(applicationContext, materiasToDisplay, colores)
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

        materias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent : AdapterView<*>, view : View?, pos : Int, id : Long)
            {
                if(pos > 0)
                {
                    try
                    {
                        materia = parent.getItemAtPosition(pos) as Materia

                        val dataManager = DataManager(applicationContext, resources.getString(R.string.db_alumnos))
                        val colores = arrayOf(resources.getColor(R.color.azulmetalico), resources.getColor(R.color.azulchillon))
                        val alumnosToDisplay = dataManager.leerAlumnos()
                        val adaptador = CustomAdapterSpinner<Alumno>(applicationContext, alumnosToDisplay, colores)
                        alumnos.adapter = adaptador
                        alumnos.isVerticalScrollBarEnabled = true

                        val texto : String = "${materia} seleccionada"
                        val color : Int = resources.getColor(R.color.brat)
                        SnackbarUtil.showSnackbar(applicationContext, view!!, texto, color)

                        alumnos.visibility = View.VISIBLE
                    }
                    catch(ex : Exception)
                    {
                        val view = findViewById<View>(android.R.id.content)
                        val texto : String = ex.message.toString()
                        val color : Int = resources.getColor(R.color.rojosangre)
                        SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
                    }
                }
                else
                {
                    alumnos.visibility = View.INVISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) { }
        }

        alumnos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent : AdapterView<*>, view : View?, pos : Int, id : Long)
            {
                if(pos > 0)
                {
                    alumno = parent.getItemAtPosition(pos) as Alumno

                    val texto: String = "${alumno} seleccionado"
                    val color: Int = resources.getColor(R.color.brat)
                    SnackbarUtil.showSnackbar(applicationContext, view!!, texto, color)

                    calificar.visibility = View.VISIBLE
                }
                else
                {
                    calificar.visibility = View.INVISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) { }
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