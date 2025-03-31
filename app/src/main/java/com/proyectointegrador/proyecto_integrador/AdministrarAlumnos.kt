/*Esta clase abre un menú para administrar a los alumnos*/

package com.proyectointegrador.proyecto_integrador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.TextView
import android.widget.Button
import android.view.View

import android.content.Intent
import android.widget.ListView

class AdministrarAlumnos : AppCompatActivity(), View.OnClickListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_administrar_alumnos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.administrar_alumnos))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nuevo = findViewById<Button>(R.id.btn_nuevo)
        val borrar = findViewById<Button>(R.id.btn_borrar)
        val regresar = findViewById<TextView>(R.id.btn_back)
        val listaPersonitas = findViewById<ListView>(R.id.lista_alumnos)
        val dataManager = DataManager(applicationContext)

        nuevo.setOnClickListener(this)
        borrar.setOnClickListener(this)
        regresar.setOnClickListener(this)

        try
        {
            val personitas = dataManager.leerPersonitas()
            val adaptador = CustomAdapter(applicationContext, personitas)
            listaPersonitas.adapter = adaptador
            listaPersonitas.isVerticalScrollBarEnabled = true
        }
        catch(ex : Exception)
        {
            val view = findViewById<View>(android.R.id.content)
            val texto : String = ex.message.toString()
            val color : Int = resources.getColor(R.color.rojosangre)
            SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
        }

        listaPersonitas.setOnItemClickListener(
        { parent, view, pos, id ->

            val selected = parent.getItemAtPosition(pos) as Alumno
            val res : Int = dataManager.borrarPersonita(selected)
            if(res>0)
            {
                val texto : String = "Alumno ${selected} eliminada"
                val color : Int = resources.getColor(R.color.brat)
                SnackbarUtil.showSnackbar(applicationContext, view, texto, color)

                //reinicia la actividad para actualizar la lista
                intent = Intent(applicationContext, ThirdActivity::class.java)
                startActivity(intent)
            }
            else
            {
                //inidca el ID que tiene la personita que no se pudo eliminar
                //para ayudar a rastrear el error a la implemntación de la base de datos
                val texto : String = "Error al eliminar.\nID=${res}"
                val color : Int = resources.getColor(R.color.rojosangre)
                SnackbarUtil.showSnackbar(applicationContext, view, texto, color)
            }
        })
    }

    override fun onClick(view : View?)
    {
        when(view?.id)
        {
            R.id.btn_nuevo ->
            {
                intent = Intent(applicationContext, NuevoAlumno::class.java)
                startActivity(intent)
            }
            R.id.btn_borrar ->
            {
                //TODO
            }
            R.id.btn_back ->
            {
                intent = Intent(applicationContext, ControlEscolar::class.java)
                startActivity(intent)
            }
        }
    }
}