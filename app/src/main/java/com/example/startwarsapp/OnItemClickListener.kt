package com.example.startwarsapp

import android.view.View
import com.example.startwarsapp.model.entity.FullInfoCard

interface OnItemClickListener {
        fun onClick(view: View, position: Int,cardsList:ArrayList<FullInfoCard>)

}