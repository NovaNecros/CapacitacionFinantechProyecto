/*Esta clase crea una actividad en donde se pueden agregar
alumnos a la base de datos */

package com.proyectointegrador.proyecto_integrador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.CheckBox
import android.content.Intent
import com.google.android.material.snackbar.Snackbar
import android.app.DatePickerDialog
import androidx.core.view.isGone

import java.util.Calendar

class NuevoAlumno : AppCompatActivity()
{
    var dataManager : DataManager? = null

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nuevo_alumno)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nuevo_alumno))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonGuardar = findViewById<Button>(R.id.btn_save)
        val botonRegresar = findViewById<Button>(R.id.btn_back)

        val lectorMatricula = findViewById<EditText>(R.id.lector_matricula)
        val lectorNombre = findViewById<EditText>(R.id.lector_nombre)
        val lectorApellidoP = findViewById<EditText>(R.id.lector_apellido_p)
        val lectorApellidoM = findViewById<EditText>(R.id.lector_apellido_m)

        val fechaDisplay = findViewById<EditText>(R.id.cumple)
        val botonFecha = findViewById<Button>(R.id.fecha_calendario)
        val calendario = Calendar.getInstance()
        val year = calendario.get(Calendar.YEAR)
        val mes  = calendario.get(Calendar.YEAR)
        val dia  = calendario.get(Calendar.YEAR)
        val lectorFecha = DatePickerDialog(applicationContext,
            { view, selectedYear, selectedMonth, selectedDay ->
                val fecha = "${selectedDay}/${selectedMonth+1}/${selectedYear}"
                fechaDisplay.setText(fecha)
            }, year, mes, dia)

        val masculino = findViewById<CheckBox>(R.id.masculino)
        val femenino = findViewById<CheckBox>(R.id.femenino)
        //se incluyen las opciones no-binario y otro y se utilizan
        //checkboxes en vez de radiobuttons para fines de inclusividad
        val nobinario = findViewById<CheckBox>(R.id.nobinario)
        val otroGenero = findViewById<EditText>(R.id.lector_otro_genero)
        val botonGenero = findViewById<Button>(R.id.btn_otro_genero)

        dataManager = DataManager(this, resources.getString(R.string.db_alumnos))
        fechaDisplay.visibility = View.GONE
        otroGenero.visibility = View.GONE

        botonGenero.setOnClickListener(View.OnClickListener
        {
            if(otroGenero.isGone)
            {
                otroGenero.visibility = View.VISIBLE
            }
            else
            {
                otroGenero.text.clear()
                otroGenero.visibility = View.GONE
            }
        })

        botonFecha.setOnClickListener(View.OnClickListener
        {
            lectorFecha.show()
            fechaDisplay.visibility = View.VISIBLE
        })

        botonGuardar.setOnClickListener(View.OnClickListener
        {
            val matricula : String = lectorMatricula.getText().toString()
            val nombre : String = lectorNombre.getText().toString()
            val apellidoP : String = lectorApellidoP.getText().toString()
            val apellidoM : String = lectorApellidoM.getText().toString()
            val generosUsuario = mutableListOf<String>()
            val cumFecha : String = fechaDisplay.getText().toString()
            val generos = arrayOf(masculino, femenino, nobinario)

            val view = findViewById<View>(android.R.id.content)
            var texto : String = ""
            var color : Int = 0

            //se valida que el usuario haya llenado los datos solicitados
            //excepto posiblemente el apellido materno
            if(matricula.isEmpty())
            {
                texto = resources.getString(R.string.no_matricula_ex)
                color = resources.getColor(R.color.rojosangre)
            }
            else if(nombre.isEmpty())
            {
                texto = resources.getString(R.string.no_nombre_alumno_ex)
                color = resources.getColor(R.color.rojosangre)
            }
            else if(apellidoP.isEmpty())
            {
                texto = resources.getString(R.string.no_apellido_p_ex)
                color = resources.getColor(R.color.rojosangre)
            }
            else if(cumFecha.isEmpty())
            {
                texto = resources.getString(R.string.no_cum_ex)
                color = resources.getColor(R.color.rojosangre)
            }
            else for(genero in generos)
            {
                if(genero.isChecked)
                {
                    generosUsuario.add(genero.text.toString())
                }
            }
            if(otroGenero.text.toString().isNotEmpty())
            {
                generosUsuario.add(otroGenero.text.toString())
            }
            else if(generosUsuario.isEmpty()) //valida que se haya seleccionado un género
            {
                Snackbar.make(it, resources.getString(R.string.no_genero_ex), Snackbar.LENGTH_SHORT).show()
            }
            else
            {
                val fulanito = Alumno(applicationContext, matricula, nombre, apellidoP, apellidoM, generosUsuario.toString(), cumFecha)
                dataManager!!.guardarAlumno(fulanito)

                texto = "Alumno ${fulanito} guardado"
                color = resources.getColor(R.color.brat)

                lectorNombre.text.clear()
                lectorApellidoP.text.clear()
                lectorApellidoM.text.clear()
                fechaDisplay.text.clear()
                fechaDisplay.visibility = View.GONE

                for(genero in generos)
                {
                    genero.isChecked = false
                }
                otroGenero.text.clear()
            }

            SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
        })

        botonRegresar.setOnClickListener(View.OnClickListener
        {
            val intent = Intent(this, AdministrarAlumnos::class.java)
            startActivity(intent)
        })
    }

    override fun onPause()
    {
        dataManager!!.cerrar()
        super.onPause()
    }

    override fun onResume()
    {
        dataManager!!.abrir()
        super.onResume()
    }
}