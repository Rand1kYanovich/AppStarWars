package com.example.startwarsapp

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
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
import com.example.startwarsapp.util.CardUtil


class RecyclerAdapter constructor(private var cardsList:ArrayList<FullInfoCard>, private val context:Context) :
    RecyclerView.Adapter<RecyclerViewHolder>() {


    private lateinit var colorArray:Array<String>
    private lateinit var listener: OnItemClickListener
    private var i:Int = 0






    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerViewHolder {
        val layoutView:View = LayoutInflater.from(parent.context).inflate(R.layout.card_item,null,false)
        val lp= ConstraintLayout.LayoutParams(pxFromDp(context,300),pxFromDp(context,400))
        lp.leftMargin = pxFromDp(context,50)
        layoutView.layoutParams = lp
        val rcv = RecyclerViewHolder(layoutView)

        return rcv
    }



    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item:FullInfoCard = cardsList.get(position)
        holder.tvName!!.setText(item.name)
        if(item.color.equals("")) {
            if(i >= colorArray.size)i=0
            holder.clCard!!.setBackgroundColor(Color.parseColor(colorArray[i]))
            item.color = colorArray[i]
            holder.btnFavorite!!.setBackgroundColor(Color.parseColor(colorArray[i]))
            i++
        }
        else{
            holder.clCard!!.setBackgroundColor(Color.parseColor(item.color))
            holder.btnFavorite!!.setBackgroundColor(Color.parseColor(item.color))
        }
        holder.bind(position,listener,cardsList)





    }

    override fun getItemCount(): Int {
        return cardsList.size
    }


    fun setColorArray(colorArray:Array<String>){ this.colorArray = colorArray }

    fun setClickListener(listener: OnItemClickListener) {this.listener = listener}



    fun pxFromDp(context: Context, dp: Int): Int {
        return ((dp.toFloat()) * context.resources.displayMetrics.density).toInt()
    }

    fun setList(list:ArrayList<FullInfoCard>){
        val sizeOld = cardsList.size
        cardsList = list
        val sizeNew = CardUtil.cardsList.size
        notifyItemRangeChanged(sizeOld,sizeNew)
        notifyDataSetChanged()
    }

    fun setListWithRemove(list:ArrayList<FullInfoCard>){
        val sizeOld = cardsList.size
        cardsList.removeAll(cardsList)
        cardsList.addAll(list)
        val sizeNew = CardUtil.cardsList.size
        notifyItemRangeChanged(sizeOld,sizeNew)
        notifyDataSetChanged()
    }



}





