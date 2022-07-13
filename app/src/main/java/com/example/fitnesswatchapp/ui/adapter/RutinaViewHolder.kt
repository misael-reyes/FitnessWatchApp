package com.example.fitnesswatchapp.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesswatchapp.databinding.ItemRutinaBinding
import com.example.fitnesswatchapp.domain.model.Rutina

class RutinaViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemRutinaBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(rutina: Rutina, onClickListener:(Rutina, Boolean) -> Unit) {
        with(binding) {
            tvnombrerutina.text = rutina.nombre
            tvtiempotrabajo.text = rutina.minutos_trabajo.toString() + " min " + rutina.segundos_trabajo.toString() + " seg"
            tvtiempodescanso.text = rutina.minutos_descanso.toString() + " min " + rutina.segundos_descanso.toString() + " seg"
            tvtotalsesiones.text = "Número de sesiones: " + rutina.sesiones.toString()

            /**
             * pasamos la rutina que seleccionó el usuario indicando con una
             * variable boolean si se debe de mostrar o eliminar
             */
            itemView.setOnClickListener { onClickListener(rutina, false) }
            btnEliminar.setOnClickListener { onClickListener(rutina, true) }
        }
    }
}