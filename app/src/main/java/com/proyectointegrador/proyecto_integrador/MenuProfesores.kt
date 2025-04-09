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
    var dataManagerAlumnos : DataManager? = null
    var dataManagerMaterias : DataManager? = null
    var dataManagerCalificaciones : DataManager? = null

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

        val calificacionTexto = findViewById<TextView>(R.id.calificacion)
        val lectorCalificacion = findViewById<EditText>(R.id.lector_calificacion)

        val calificar = findViewById<Button>(R.id.btn_calificar)
        val regresar = findViewById<TextView>(R.id.btn_back)

        var materia = Materia()
        var alumno = Alumno()
        var calificacion = Calificacion()

        val materias = findViewById<Spinner>(R.id.spinner_materias)
        val alumnos = findViewById<Spinner>(R.id.spinner_alumnos)

        calificacionTexto.visibility = View.GONE
        lectorCalificacion.visibility = View.GONE
        alumnos.visibility = View.GONE
        calificar.visibility = View.GONE

        dataManagerAlumnos = DataManager(applicationContext, resources.getString(R.string.db_alumnos))
        dataManagerMaterias = DataManager(applicationContext, resources.getString(R.string.db_materias))
        dataManagerCalificaciones = DataManager(applicationContext, resources.getString(R.string.db_calificaciones))

        val colores = arrayOf(resources.getColor(R.color.azulchillon), resources.getColor(R.color.azulmetalico),
            resources.getColor(R.color.azulreal))
        val materiasToDisplay = dataManagerMaterias!!.leerMaterias().toMutableList()
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

                    actualizarSpinnerAlumnos(materia)

                    val texto : String = "${materia} seleccionada"
                    val color : Int = resources.getColor(R.color.brat)
                    SnackbarUtil.showSnackbar(applicationContext, view!!, texto, color)

                    alumnos.setSelection(0)
                    alumnos.visibility = View.VISIBLE
                }
                else
                {
                    alumnos.visibility = View.GONE
                }

                calificacionTexto.visibility = View.GONE
                lectorCalificacion.visibility = View.GONE
                calificar.visibility = View.GONE
            }

            override fun onNothingSelected(parent : AdapterView<*>) { }
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

                    calificacionTexto.visibility = View.VISIBLE
                    lectorCalificacion.visibility = View.VISIBLE
                    calificar.visibility = View.VISIBLE
                }
                else
                {
                    calificacionTexto.visibility = View.GONE
                    lectorCalificacion.visibility = View.GONE
                    calificar.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent : AdapterView<*>) { }
        }

        calificar.setOnClickListener(View.OnClickListener
        {
            val calificacionInput : String = lectorCalificacion.text.toString()

            val view = findViewById<View>(android.R.id.content)
            var texto : String = resources.getString(R.string.no_calif_ex)
            var color : Int = resources.getColor(R.color.rojo)

            if(calificacionInput.isNotEmpty())
            {
                calificacion = Calificacion(applicationContext, alumno, materia, calificacionInput.toDouble())

                dataManagerCalificaciones!!.guardarCalificacion(calificacion)

                texto = "${alumno} obtuvo ${calificacion} en ${materia}"
                color = resources.getColor(R.color.brat)

                lectorCalificacion.text.clear()
                lectorCalificacion.visibility = View.GONE
                alumnos.setSelection(0)
            }

            SnackbarUtil.showSnackbar(applicationContext, view!!, texto, color)

            actualizarSpinnerAlumnos(materia)
        })

        regresar.setOnClickListener(View.OnClickListener
        {
            intent = Intent(applicationContext, MainActivity::class.java)
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

    fun actualizarSpinnerAlumnos(materia : Materia)
    {
        val alumnosTodos = dataManagerAlumnos!!.leerAlumnos()

        val calificaciones = dataManagerCalificaciones!!.leerCalificaciones()
            .filter { it.materia.id == materia.id }.map { it.alumno.id }

        val alumnosSinCalificacion = alumnosTodos
            .filterNot { calificaciones.contains(it.id) }.toMutableList()

        alumnosSinCalificacion.add(0, Alumno("Selecciona un alumno..."))

        val colores = arrayOf(resources.getColor(R.color.azulmetalico), resources.getColor(R.color.azulchillon),
            resources.getColor(R.color.azulreal))

        val adaptadorAlumnos = CustomAdapterSpinner<Alumno>(applicationContext, R.layout.item_spinner, alumnosSinCalificacion, colores)
        adaptadorAlumnos.setDropDownViewResource(R.layout.item_dropdown)

        val alumnos = findViewById<Spinner>(R.id.spinner_alumnos)
        alumnos.adapter = adaptadorAlumnos
        alumnos.isVerticalScrollBarEnabled = true
    }
}