/*Esta clase crea una actividad en donde se pueden agregar
personitas a la base de datos */

package com.proyectointegrador.proyecto_integrador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.CheckBox
import android.content.Intent
import com.google.android.material.snackbar.Snackbar

class SecondActivity : AppCompatActivity()
{
    var dataManager : DataManager? = null

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.second))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonGuardar = findViewById<Button>(R.id.boton_guardar)
        val botonRegresar = findViewById<Button>(R.id.btn_back)
        val lectorNombre = findViewById<EditText>(R.id.lector_nombre)
        val lectorApellidoP = findViewById<EditText>(R.id.lector_apellido_p)
        val lectorApellidoM = findViewById<EditText>(R.id.lector_apellido_m)
        val masculino = findViewById<CheckBox>(R.id.masculino)
        val femenino = findViewById<CheckBox>(R.id.femenino)
        val nobinario = findViewById<CheckBox>(R.id.nobinario)
        val otroGenero = findViewById<EditText>(R.id.lector_otro_genero)
        val calendarioView = findViewById<CalendarView>(R.id.fecha_calendario)

        dataManager = DataManager(this)

        botonGuardar.setOnClickListener(View.OnClickListener
        {
            val nombre : String = lectorNombre.getText().toString()
            val apellidoP : String = lectorApellidoP.getText().toString()
            val apellidoM : String = lectorApellidoM.getText().toString()
            val generosUsuario = mutableListOf<String>()
            val cumFecha : String = calendarioView.date.toString()
            val generos = arrayOf(masculino, femenino, nobinario)

            //se incluyen las opciones no-binario y otro y se utilizan
            //checkboxes en vez de radiobuttons para fines de inclusividad
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
            else if(generosUsuario.isEmpty()) //valida que se haya seleccionado un género
            {
                Snackbar.make(it, resources.getString(R.string.no_genero_ex), Snackbar.LENGTH_SHORT).show()
            }
            else if(nombre.isNotEmpty() && apellidoP.isNotEmpty() && cumFecha.isNotEmpty())
            {
                //valida que el usuario haya llenado los datos solicitados
                //excepto posiblemente el apellido materno
                val fulanito = Personita(applicationContext, nombre, apellidoP, apellidoM, generosUsuario.toString(), cumFecha)
                dataManager!!.guardarPersonita(fulanito)

                val view = findViewById<View>(android.R.id.content)
                val texto : String = "Personita ${fulanito} guardada"
                val color : Int = resources.getColor(R.color.brat)
                SnackbarUtil.showSnackbar(applicationContext, view, texto, color)

                lectorNombre.text.clear()
                lectorApellidoP.text.clear()
                lectorApellidoM.text.clear()
                calendarioView.date = System.currentTimeMillis()

                for(genero in generos)
                {
                    genero.isChecked = false
                }
                otroGenero.text.clear()
            }
            else
            {
                val view = findViewById<View>(android.R.id.content)
                val texto : String = resources.getString(R.string.datos_incompletos_ex)
                val color : Int = resources.getColor(R.color.rojosangre)
                SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
            }
        })

        botonRegresar.setOnClickListener(View.OnClickListener
        {
            val intent = Intent(this, MainActivity::class.java)
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