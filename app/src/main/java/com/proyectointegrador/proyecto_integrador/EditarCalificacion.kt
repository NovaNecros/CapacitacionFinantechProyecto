/*En esta clase los profesores pueden corregir las calificaciones*/

package com.proyectointegrador.proyecto_integrador.com.proyectointegrador.proyecto_integrador

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.proyectointegrador.proyecto_integrador.Calificacion
import com.proyectointegrador.proyecto_integrador.ConsultarCalificaciones
import com.proyectointegrador.proyecto_integrador.DataManager
import com.proyectointegrador.proyecto_integrador.R
import com.proyectointegrador.proyecto_integrador.SnackbarUtil

class EditarCalificacion : AppCompatActivity()
{
    var dataManagerAlumnos : DataManager? = null
    var dataManagerMaterias : DataManager? = null
    var dataManagerCalificaciones : DataManager? = null

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_calificacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editar_calificacion))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val view = findViewById<View>(R.id.editar_calificacion)

        val botonActualizar = findViewById<Button>(R.id.btn_calificar)
        val botonCancelar = findViewById<Button>(R.id.btn_back)

        val alumnoTexto = findViewById<TextView>(R.id.alumno)
        val materiaTexto = findViewById<TextView>(R.id.materia)
        val lectorCalificacion = findViewById<EditText>(R.id.lector_calificacion)

        dataManagerAlumnos = DataManager(this, resources.getString(R.string.db_alumnos))
        dataManagerMaterias = DataManager(this, resources.getString(R.string.db_materias))
        dataManagerCalificaciones =
            DataManager(this, resources.getString(R.string.db_calificaciones))

        val bundle = intent.extras
        val data = bundle?.getString("idParaEditar")
        var calificacion = dataManagerCalificaciones!!.leerCalificacion(data!!.toInt())
        val alumno = dataManagerAlumnos!!.leerAlumno(calificacion.alumno.id)
        val materia = dataManagerMaterias!!.leerMateria(calificacion.materia.id)
        val id : Int = calificacion.id

        alumnoTexto.text = alumno.toString()
        materiaTexto.setText(materia.clave + " - " + materia.toString())
        lectorCalificacion.setText(calificacion.numero.toString())

        botonActualizar.setOnClickListener(
            View.OnClickListener
        {
            val nuevaCalif = lectorCalificacion.text.toString()

            var texto : String = resources.getString(R.string.no_calif_ex)
            var color : Int = resources.getColor(R.color.rojo)

            if(nuevaCalif.isNotEmpty())
            {
                dataManagerCalificaciones!!.borrarCalificacion(calificacion)
                calificacion =
                    Calificacion(applicationContext, alumno, materia, nuevaCalif.toDouble())
                calificacion.id = id
                dataManagerCalificaciones!!.guardarCalificacion(calificacion)

                texto = "${alumno} obtuvo ${nuevaCalif} en ${materia}"
                color = resources.getColor(R.color.brat)
                SnackbarUtil.showSnackbar(applicationContext, view, texto, color)

                val intent = Intent(this, ConsultarCalificaciones::class.java)
                startActivity(intent)
            }


            SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
        })

        botonCancelar.setOnClickListener(
            View.OnClickListener
        {
            val intent = Intent(this, ConsultarCalificaciones::class.java)
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
}