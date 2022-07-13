package com.example.fitnesswatchapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesswatchapp.R
import com.example.fitnesswatchapp.domain.model.Rutina

/**
 * además de la lista de las rutinas, le pasaremos una función lamdam para que al clickear sobre un item,
 * este nos devuelva el obj rutina del que se trata
 */

class RutinaAdapter(
    private val rutinaList: List<Rutina>,
    private val onClickListener: (Rutina, Boolean) -> Unit
) : RecyclerView.Adapter<RutinaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutinaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RutinaViewHolder(layoutInflater.inflate(R.layout.item_rutina, parent, false))
    }

    override fun onBindViewHolder(holder: RutinaViewHolder, position: Int) {
        val item = rutinaList[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = rutinaList.size
}