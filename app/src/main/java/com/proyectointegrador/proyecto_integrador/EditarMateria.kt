/*Esta clase crea una actividad en donde se pueden editar
materias existentes en la base de datos */

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

class EditarMateria : AppCompatActivity()
{
    var dataManager : DataManager? = null

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_materia)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editar_materia))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonActualizar = findViewById<Button>(R.id.btn_update)
        val botonLimpiar = findViewById<Button>(R.id.btn_clear)
        val botonCancelar = findViewById<Button>(R.id.btn_cancel)

        val lectorClave = findViewById<EditText>(R.id.lector_clave)
        val lectorNombre = findViewById<EditText>(R.id.lector_nombre)
        val lectorCreditos = findViewById<EditText>(R.id.lector_creditos)

        dataManager = DataManager(applicationContext, resources.getString(R.string.db_materias))

        val bundle = intent.extras
        val data = bundle?.getString("idParaEditar")
        var materia = dataManager!!.leerMateria(data!!.toInt())

        lectorClave.setText(materia.clave.toString())
        lectorNombre.setText(materia.nombre)
        lectorCreditos.setText(materia.creditos)

        botonActualizar.setOnClickListener(View.OnClickListener
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
                texto = "Materia ${materia} actualizada"
                color = resources.getColor(R.color.brat)

                dataManager!!.borrarMateria(materia)
                materia = Materia(applicationContext, clave, nombre, creditos)
                dataManager!!.guardarMateria(materia)

                lectorClave.text.clear()
                lectorNombre.text.clear()
                lectorCreditos.text.clear()
            }

            SnackbarUtil.showSnackbar(applicationContext, view, texto, color)

            val intent = Intent(this, AdministrarMaterias::class.java)
            startActivity(intent)
        })

        botonLimpiar.setOnClickListener(View.OnClickListener
        {
            lectorClave.text.clear()
            lectorNombre.text.clear()
            lectorCreditos.text.clear()
        })

        botonCancelar.setOnClickListener(View.OnClickListener
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