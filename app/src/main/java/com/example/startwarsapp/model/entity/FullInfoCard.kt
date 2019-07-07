package com.example.startwarsapp.model.entity

import android.support.v7.util.DiffUtil
import java.io.Serializable

class FullInfoCard :Serializable{

    var name:String = ""
    var height:String = ""
    var mass:String = ""
    var hair_color:String = ""
    var skin_color:String = ""
    var eye_color: String = ""
    var birth_year: String = ""
    var gender:String = ""
    var color:String = ""
    var isFavorites:Boolean = false


}