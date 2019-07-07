package com.example.startwarsapp

import android.widget.ImageButton
import com.example.startwarsapp.model.entity.FullInfoCard

interface OnFavoriteClickListener {

    fun onFavoriteClickListener(position:Int,favoriteList:ArrayList<FullInfoCard>,btnFavorite:ImageButton)
}