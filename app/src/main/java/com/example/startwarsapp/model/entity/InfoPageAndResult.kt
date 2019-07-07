package com.example.startwarsapp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class InfoPageAndResult {

    var next:String?=null
    var previous:String?=null
    var count:Int?=null
    @SerializedName("results")
    @Expose
    var results:List<FullInfoCard> = ArrayList()
}