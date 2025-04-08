/*Esta clase crea un menú para que los profesores asignen calificaciones*/

package com.proyectointegrador.proyecto_integrador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.TextView
import android.widget.EditText
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

        val calificacion = findViewById<TextView>(R.id.calificacion)
        val lectorCalificacion = findViewById<EditText>(R.id.lector_calificacion)

        val calificar = findViewById<Button>(R.id.btn_calificar)
        val regresar = findViewById<TextView>(R.id.btn_back)

        var materia = Materia()
        var alumno = Alumno()

        val materias = findViewById<Spinner>(R.id.spinner_materias)
        val alumnos = findViewById<Spinner>(R.id.spinner_alumnos)

        calificacion.visibility = View.INVISIBLE
        lectorCalificacion.visibility = View.INVISIBLE
        alumnos.visibility = View.INVISIBLE
        calificar.visibility = View.INVISIBLE

        val dataManager = DataManager(applicationContext, resources.getString(R.string.db_materias))
        val colores = arrayOf(resources.getColor(R.color.azulchillon), resources.getColor(R.color.azulmetalico))
        val materiasToDisplay = dataManager.leerMaterias().toMutableList()
        //La mejor manera que se me ocurrió de agregar un default al spinner
        materiasToDisplay.add(0, Materia("Selecciona una materia..."))

        val adaptadorMaterias = CustomAdapterSpinner<Materia>(applicationContext, R.layout.item_spinner, materiasToDisplay, colores)
        adaptadorMaterias.setDropDownViewResource(R.layout.item_dropdown)
        materias.adapter = adaptadorMaterias
        materias.isVerticalScrollBarEnabled = true

        materias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent : AdapterView<*>, view : View?, pos : Int, id : Long)
            {
                if(pos > 0)
                {
                    materia = parent.getItemAtPosition(pos) as Materia

                    val dataManager = DataManager(applicationContext, resources.getString(R.string.db_alumnos))
                    val colores = arrayOf(resources.getColor(R.color.azulmetalico), resources.getColor(R.color.azulchillon))
                    val alumnosToDisplay = dataManager.leerAlumnos().toMutableList()
                    alumnosToDisplay.add(0, Alumno("Selecciona un alumno..."))

                    val adaptadorAlumnos = CustomAdapterSpinner<Alumno>(applicationContext, R.layout.item_spinner, alumnosToDisplay, colores)
                    adaptadorAlumnos.setDropDownViewResource(R.layout.item_dropdown)
                    alumnos.adapter = adaptadorAlumnos
                    alumnos.isVerticalScrollBarEnabled = true

                    val texto : String = "${materia} seleccionada"
                    val color : Int = resources.getColor(R.color.brat)
                    SnackbarUtil.showSnackbar(applicationContext, view!!, texto, color)

                    alumnos.setSelection(0)
                    alumnos.visibility = View.VISIBLE
                }
                else
                {
                    alumnos.visibility = View.INVISIBLE
                }

                calificacion.visibility = View.INVISIBLE
                lectorCalificacion.visibility = View.INVISIBLE
                calificar.visibility = View.INVISIBLE
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

                    val texto : String = "${alumno} seleccionado"
                    val color : Int = resources.getColor(R.color.brat)
                    SnackbarUtil.showSnackbar(applicationContext, view!!, texto, color)

                    calificacion.visibility = View.VISIBLE
                    lectorCalificacion.visibility = View.VISIBLE
                    calificar.visibility = View.VISIBLE
                }
                else
                {
                    calificacion.visibility = View.INVISIBLE
                    lectorCalificacion.visibility = View.INVISIBLE
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