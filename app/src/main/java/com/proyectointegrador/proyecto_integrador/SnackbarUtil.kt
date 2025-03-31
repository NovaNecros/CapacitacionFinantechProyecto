/*Esta clase permite btn_menu_alumno un snackbar personalizado*/

package com.proyectointegrador.proyecto_integrador

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

object SnackbarUtil
{
    fun showSnackbar(contexto : Context, view : View, texto : String, col : Int)
    {
        val snackbar = Snackbar.make(view, texto, Snackbar.LENGTH_LONG)
        val msj = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        val layoutParams = snackbar.view.layoutParams as FrameLayout.LayoutParams

        snackbar.setBackgroundTint(col)
        snackbar.view.setPadding(0, 0, 0, 0)

        msj.textSize = 24f
        msj.gravity = Gravity.CENTER_HORIZONTAL //no sirve T___T
        msj.setTextColor(ContextCompat.getColor(contexto, R.color.white))

        layoutParams.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        layoutParams.setMargins(0, 250, 0, 0)
        layoutParams.width = FrameLayout.LayoutParams.WRAP_CONTENT
        layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
        snackbar.view.layoutParams = layoutParams

        snackbar.show()
    }
}