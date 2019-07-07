package com.example.startwarsapp.util

import android.content.Context

class ViewUtil {

    companion object{
        fun pxFromDp(context: Context, dp: Int): Int {
            return ((dp.toFloat()) * context.resources.displayMetrics.density).toInt()
        }
    }
}