/*Esta clase crea un menú para que los profesores revisen calificaciones existentes*/

package com.proyectointegrador.proyecto_integrador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.ListView
import android.widget.Button
import android.widget.AdapterView
import android.widget.Spinner
import android.view.View
import android.content.Intent

class ConsultarCalificaciones : AppCompatActivity()
{
    var dataManagerAlumnos : DataManager? = null
    var dataManagerMaterias : DataManager? = null
    var dataManagerCalificaciones : DataManager? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_consultar_calificaciones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.consultar_calificaciones))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editar = findViewById<Button>(R.id.btn_edit)
        val eliminar = findViewById<Button>(R.id.btn_borrar)
        val regresar = findViewById<Button>(R.id.btn_back)

        var materia = Materia()
        var alumno = Alumno()
        var calificacion = Calificacion()

        val alumnos = findViewById<ListView>(R.id.alumnos)
        val materias = findViewById<Spinner>(R.id.spinner_materias)

        alumnos.visibility = View.GONE
        editar.visibility = View.GONE
        eliminar.visibility = View.GONE

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

                editar.visibility = View.GONE
                eliminar.visibility = View.GONE
            }

            override fun onNothingSelected(parent : AdapterView<*>) { }
        }

        alumnos.setOnItemClickListener(
        { parent, view, pos, id ->

            alumno = parent.getItemAtPosition(pos) as Alumno
            calificacion = dataManagerCalificaciones!!.leerCalificacion(alumno.id, materia.id)!!

            editar.text = resources.getString(R.string.btn_edit) + resources.getString(R.string.calif) + " de ${alumno}"
            eliminar.text = resources.getString(R.string.btn_delete) + resources.getString(R.string.calif) + " de ${alumno}"
            editar.visibility = View.VISIBLE
            eliminar.visibility = View.VISIBLE

            val texto : String = "Alumno ${alumno} seleccionado"
            val color : Int = resources.getColor(R.color.brat)
            SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
        })

        editar.setOnClickListener(View.OnClickListener
        {
            //TODO
        })

        eliminar.setOnClickListener(View.OnClickListener
        { view ->
            dataManagerCalificaciones!!.borrarCalificacionesPorAlumno(alumno)
            val res : Int = dataManagerAlumnos!!.borrarAlumno(alumno)

            if(res>0)
            {
                val texto : String = "Alumno ${alumno} eliminado"
                val color : Int = resources.getColor(R.color.brat)
                SnackbarUtil.showSnackbar(applicationContext, view, texto, color)

                //actualiza el ListView de alumnos
                actualizarListaAlumnos()
                editar.visibility = View.GONE
                eliminar.visibility = View.GONE
            }
            else
            {
                val texto : String = "Error al eliminar materia"
                val color : Int = resources.getColor(R.color.rojosangre)
                SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
            }
        })

        regresar.setOnClickListener(View.OnClickListener
        {
            intent = Intent(applicationContext, MenuProfesores::class.java)
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

        val colores = arrayOf(resources.getColor(R.color.verdeclaro), resources.getColor(R.color.verdebosque),
            resources.getColor(R.color.brat))

        val adaptadorAlumnos = CustomAdapterSpinner<Alumno>(applicationContext, R.layout.item_spinner, alumnosSinCalificacion, colores)
        adaptadorAlumnos.setDropDownViewResource(R.layout.item_dropdown)

        val alumnos = findViewById<Spinner>(R.id.spinner_alumnos)
        alumnos.adapter = adaptadorAlumnos
        alumnos.isVerticalScrollBarEnabled = true
    }

    fun actualizarListaAlumnos()
    {
        val alumnos = dataManagerAlumnos!!.leerAlumnos()

        val colores = arrayOf(resources.getColor(R.color.verdebosque), resources.getColor(R.color.verdeclaro))
        val adaptador = CustomAdapterListView<Alumno>(applicationContext, alumnos, colores)

        val alumnosToDisplay = findViewById<ListView>(R.id.alumnos)
        alumnosToDisplay.adapter = adaptador
        alumnosToDisplay.isVerticalScrollBarEnabled = true
    }
}