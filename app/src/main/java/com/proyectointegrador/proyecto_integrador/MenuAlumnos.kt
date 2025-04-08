/*Esta clase crea un menú para que los alumnos consulten sus calificaciones*/

package com.proyectointegrador.proyecto_integrador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import android.view.View
import android.content.Intent

class MenuAlumnos : AppCompatActivity()
{
    var dataManager : DataManager? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_alumnos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menu_alumnos))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val matriculaLector = findViewById<EditText>(R.id.lector_matricula)
        val ingresar = findViewById<Button>(R.id.btn_matricula)
        val regresar = findViewById<TextView>(R.id.btn_back)

        dataManager = DataManager(applicationContext, resources.getString(R.string.db_alumnos))

        ingresar.setOnClickListener(View.OnClickListener
        {
            val matricula : String = matriculaLector.text.toString().trim()
            val view = findViewById<View>(android.R.id.content)
            var texto : String = resources.getString(R.string.matricula_invalida_ex)
            var color : Int = resources.getColor(R.color.rojo)

            if(matricula.isNotEmpty())
            {
                val alumno : Alumno? = dataManager!!.buscarMatricula(matricula)

                if(alumno != null)
                {
                    val intent = Intent(applicationContext, PerfilAcademico::class.java)
                    intent.putExtra("alumno", alumno.id.toString())
                    startActivity(intent)
                }
            }
            else
            {
                texto = resources.getString(R.string.no_matricula_ex)
            }

            SnackbarUtil.showSnackbar(applicationContext, view!!, texto, color)
        })

        regresar.setOnClickListener(View.OnClickListener
        {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        })
    }
}