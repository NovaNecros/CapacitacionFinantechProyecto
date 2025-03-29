/*Esta clase crea una actividad que sirve como menú principal*/

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

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nuevaPersonita = findViewById<Button>(R.id.btn_nueva)
        val borrarPersonita = findViewById<Button>(R.id.btn_borrar)
        val mostrarListaPersonitas = findViewById<TextView>(R.id.btn_mostrar)


        nuevaPersonita.setOnClickListener(View.OnClickListener
        {
            intent = Intent(applicationContext, SecondActivity::class.java)
            startActivity(intent)
        })

        borrarPersonita.setOnClickListener(View.OnClickListener
        { v->
            intent = Intent(applicationContext, ThirdActivity::class.java)
            startActivity(intent)
        })

        mostrarListaPersonitas.setOnClickListener(View.OnClickListener
        { v ->
            intent = Intent(applicationContext, FourthActivity::class.java)
            startActivity(intent)
        })
    }
}