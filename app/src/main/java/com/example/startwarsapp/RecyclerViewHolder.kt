package com.example.startwarsapp

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvName: TextView?=null
    var clCard: ConstraintLayout?=null
    init {
        clCard = itemView.findViewById(R.id.clCard)
        tvName = itemView.findViewById(R.id.tvName)

    }

    fun bind(position:Int,listener:OnItemClickListener){
        itemView.setOnClickListener { v -> listener.onClick(v!!,position) }
    }

}