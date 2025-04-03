/*Esta clase crea una actividad en donde se pueden agregar
materias a la base de datos */

package com.proyectointegrador.proyecto_integrador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.widget.TextView

class NuevaMateria : AppCompatActivity()
{
    var dataManager : DataManager? = null

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nueva_materia)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nueva_materia))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonGuardar = findViewById<Button>(R.id.btn_save)
        val botonLimpiar = findViewById<Button>(R.id.btn_clear)
        val botonRegresar = findViewById<Button>(R.id.btn_back)

        val lectorClave = findViewById<EditText>(R.id.lector_clave)
        val lectorNombre = findViewById<EditText>(R.id.lector_nombre)
        val lectorCreditos = findViewById<EditText>(R.id.lector_creditos)

        dataManager = DataManager(applicationContext, resources.getString(R.string.db_materias))
        var materia = Materia()

        botonGuardar.setOnClickListener(View.OnClickListener
        {
            val clave : String = lectorClave.getText().toString()
            val nombre : String = lectorNombre.getText().toString()
            val creditos : String = lectorCreditos.getText().toString()

            val view = findViewById<View>(android.R.id.content)
            var texto : String = ""
            var color : Int = 0

            //se valida que se haya introducido la clave, el nombre y número de creditos de la materia
            if(clave.isEmpty())
            {
                texto = resources.getString(R.string.no_clave_ex)
                color = resources.getColor(R.color.rojosangre)
            }
            else if(nombre.isEmpty())
            {
                texto = resources.getString(R.string.no_nombre_materia_ex)
                color = resources.getColor(R.color.rojosangre)
            }
            else if(creditos.isEmpty())
            {
                texto = resources.getString(R.string.no_creditos_ex)
                color = resources.getColor(R.color.rojosangre)
            }
            else
            {
                materia = Materia(applicationContext, clave, nombre, creditos)
                dataManager!!.guardarMateria(materia)

                texto = "Materia ${materia} guardada"
                color = resources.getColor(R.color.brat)

                lectorClave.text.clear()
                lectorNombre.text.clear()
                lectorCreditos.text.clear()
            }

            SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
        })

        botonLimpiar.setOnClickListener(View.OnClickListener
        {
            lectorClave.text.clear()
            lectorNombre.text.clear()
            lectorCreditos.text.clear()
        })

        botonRegresar.setOnClickListener(View.OnClickListener
        {
            val intent = Intent(this, AdministrarMaterias::class.java)
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