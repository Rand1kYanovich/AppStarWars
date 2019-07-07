package com.example.startwarsapp

import android.view.View
import android.widget.ImageButton

interface OnFavoriteClick {

    fun onFavoriteClick(btnFavorite:ImageButton,isFavorite:Boolean)
}