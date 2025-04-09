/*Esta clase crea una actividad en donde se pueden agregar
alumnos a la base de datos */

package com.proyectointegrador.proyecto_integrador

import android.app.DatePickerDialog
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
import androidx.core.view.isGone
import com.proyectointegrador.proyecto_integrador.NuevoAlumno

import java.util.Calendar

class EditarAlumno : AppCompatActivity()
{
    var dataManager : DataManager? = null

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_alumno)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editar_alumno))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonActualizar = findViewById<Button>(R.id.btn_save)
        val botonCancelar = findViewById<Button>(R.id.btn_back)

        val lectorMatricula = findViewById<EditText>(R.id.lector_matricula)
        val lectorNombre = findViewById<EditText>(R.id.lector_nombre)
        val lectorApellidoP = findViewById<EditText>(R.id.lector_apellido_p)
        val lectorApellidoM = findViewById<EditText>(R.id.lector_apellido_m)

        val calendario = Calendar.getInstance()
        val year = calendario.get(Calendar.YEAR)
        val mes  = calendario.get(Calendar.MONTH)
        val dia  = calendario.get(Calendar.DAY_OF_MONTH)
        val lectorFecha = findViewById<EditText>(R.id.lector_fecha)
        val datePicker = DatePickerDialog(this@EditarAlumno,
            { view, year, mes, dia ->
                val fecha = "${dia}/${mes+1}/${year}"
                lectorFecha.setText(fecha)
            }, year, mes, dia)

        val masculino = findViewById<CheckBox>(R.id.masculino)
        val femenino = findViewById<CheckBox>(R.id.femenino)
        //se incluyen las opciones no-binario y otro y se utilizan
        //checkboxes en vez de radiobuttons para fines de inclusividad
        val nobinario = findViewById<CheckBox>(R.id.nobinario)
        val generos = arrayOf(masculino, femenino, nobinario)
        val otroGenero = findViewById<EditText>(R.id.lector_otro_genero)
        val botonGenero = findViewById<Button>(R.id.btn_otro_genero)

        dataManager = DataManager(this, resources.getString(R.string.db_alumnos))
        otroGenero.visibility = View.GONE

        val bundle = intent.extras
        val data = bundle?.getString("idParaEditar")
        var alumno = dataManager!!.leerAlumno(data!!.toInt())
        val id : Int = alumno.id

        lectorMatricula.setText(alumno.matricula.toString())
        lectorNombre.setText(alumno.nombre)
        lectorApellidoP.setText(alumno.apellidoP)
        lectorApellidoM.setText(alumno.apellidoM)
        lectorFecha.setText(alumno.fecha)

        lectorFecha.setOnClickListener(View.OnClickListener
        {
            datePicker.show()
        })

        for(genero in generos)
        {
            if(alumno.generos.contains(genero.text.toString()))
            {
                genero.isChecked = true
            }
        }

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

        botonActualizar.setOnClickListener(View.OnClickListener
        {
            val matricula : String = lectorMatricula.getText().toString()
            val nombre : String = lectorNombre.getText().toString()
            val apellidoP : String = lectorApellidoP.getText().toString()
            val apellidoM : String = lectorApellidoM.getText().toString()
            val generosUsuario = mutableListOf<String>()
            val cumFecha : String = lectorFecha.getText().toString()

            val view = findViewById<View>(android.R.id.content)
            var texto : String = ""
            var color : Int = 0

            for(genero in generos)
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
            else if(generosUsuario.isEmpty()) //valida que se haya seleccionado un género
            {
                texto = resources.getString(R.string.no_genero_ex)
                color = resources.getColor(R.color.rojosangre)
            }
            else
            {
                dataManager!!.borrarAlumno(alumno)

                alumno = Alumno(applicationContext, matricula, nombre, apellidoP, apellidoM, generosUsuario.toString(), cumFecha)
                alumno.id = id

                dataManager!!.guardarAlumno(alumno)

                texto = "Alumno ${alumno} actualizado"
                color = resources.getColor(R.color.brat)

                lectorMatricula.text.clear()
                lectorNombre.text.clear()
                lectorApellidoP.text.clear()
                lectorApellidoM.text.clear()
                lectorFecha.text.clear()

                for(genero in generos)
                {
                    genero.isChecked = false
                }
                otroGenero.text.clear()
                otroGenero.visibility = View.GONE

                val intent = Intent(this, AdministrarAlumnos::class.java)
                startActivity(intent)
            }

            SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
        })

        botonCancelar.setOnClickListener(View.OnClickListener
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