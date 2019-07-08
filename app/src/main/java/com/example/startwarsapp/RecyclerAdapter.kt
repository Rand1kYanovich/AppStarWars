package com.example.startwarsapp

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.startwarsapp.model.entity.FullInfoCard
import com.example.startwarsapp.util.ViewUtil


class RecyclerAdapter constructor(private var cardsList:ArrayList<FullInfoCard>, private val context:Context) :
    RecyclerView.Adapter<RecyclerViewHolder>() {


    private lateinit var colorArray:Array<String>
    private lateinit var listener: OnItemClickListener
    private lateinit var favoriteListener: OnFavoriteClickListener
    private var i:Int = 0


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerViewHolder {
        val layoutView:View = LayoutInflater.from(parent.context).inflate(R.layout.card_item,null,false)
        val rcv = RecyclerViewHolder(layoutView)

        return rcv
    }



    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item:FullInfoCard = cardsList.get(position)
        holder.tvName!!.setText(item.name)
        if(item.isFavorites)holder.btnFavorite!!.setImageResource(R.drawable.ic_favorite_true)
        else if(!item.isFavorites)holder.btnFavorite!!.setImageResource(R.drawable.ic_favorite_false)

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
        holder.bind(position,listener,cardsList,favoriteListener, holder.btnFavorite!!)
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }


    fun setColorArray(colorArray:Array<String>){ this.colorArray = colorArray }

    fun setClickListener(listener: OnItemClickListener) {this.listener = listener}

    fun setFavoriteListener(favoriteListener: OnFavoriteClickListener){this.favoriteListener = favoriteListener}





    fun setList(list:ArrayList<FullInfoCard>){
        val sizeOld = cardsList.size
        cardsList = list
        val sizeNew = cardsList.size
        notifyItemRangeChanged(sizeOld,sizeNew)
        notifyDataSetChanged()
    }




}





