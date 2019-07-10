package com.example.startwarsapp.model.api

import com.example.startwarsapp.model.entity.InfoPageAndResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JSONPlaceHolderApi {

    @GET("people/")
    fun getCards(@Query("page") page: String, @Query("search") search: String): Call<InfoPageAndResult>

}