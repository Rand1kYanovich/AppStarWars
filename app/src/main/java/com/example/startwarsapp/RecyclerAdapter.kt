package com.example.startwarsapp

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.startwarsapp.model.entity.FullInfoCard
import com.example.startwarsapp.model.entity.InfoPageAndResult


class RecyclerAdapter :
    PagedListAdapter<FullInfoCard, RecyclerViewHolder>(FullInfoCard.diffCalback) {

    private var cardsList:ArrayList<FullInfoCard>?=null
    private lateinit var colorArray:Array<String>
    private lateinit var listener: OnItemClickListener
    private var i:Int = 0

    init {
        val paramsMsg:LinearLayout.LayoutParams  = LinearLayout.LayoutParams(300,450)
        paramsMsg.gravity = Gravity.CENTER
    }




    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerViewHolder {
        val layoutView:View = LayoutInflater.from(parent.context).inflate(R.layout.card_item,null,false)
        val lp: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(300,450)
        layoutView.layoutParams = lp
        val rcv = RecyclerViewHolder(layoutView)

        return rcv
    }



    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val paramsMsg:LinearLayout.LayoutParams  = LinearLayout.LayoutParams(300,450)
        i++
        if(i >= colorArray.size)i=0
        holder.tvName!!.setText(getItem(position)!!.name)
        holder.clCard!!.setBackgroundColor(Color.parseColor(colorArray[i]))
        holder.bind(position,listener)
        getItem(position)!!.color = colorArray[i]




    }


    fun setColorArray(colorArray:Array<String>){ this.colorArray = colorArray }

    fun setClickListener(listener: OnItemClickListener) {this.listener = listener}


}





