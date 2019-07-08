package com.example.startwarsapp.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.v7.util.DiffUtil
import java.io.Serializable

@Entity
class FullInfoCard :Serializable{

    @PrimaryKey
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