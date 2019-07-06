package com.example.startwarsapp.model.api

import com.example.startwarsapp.model.entity.InfoPageAndResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JSONPlaceHolderApi {

@GET(".")
public fun getCards(@Query("page") page:String): Call<InfoPageAndResult>

@GET(".")
public fun searchCards(@Query("search")name:String):Call<InfoPageAndResult>

}