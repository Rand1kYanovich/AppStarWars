package com.example.startwarsapp

import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import android.arch.paging.PositionalDataSource
import android.net.Uri
import android.util.Log
import com.example.startwarsapp.model.api.NetworkService
import com.example.startwarsapp.model.entity.FullInfoCard
import com.example.startwarsapp.model.entity.InfoPageAndResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URI

class MyPositionalDataSource: PageKeyedDataSource<String, FullInfoCard>() {

    var infoPageAndResult: InfoPageAndResult? = null
    var cardsList: MutableList<FullInfoCard>? = null



    //Функция для первой подгрузки элементов
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, FullInfoCard>) {

            NetworkService.getInstance()
                .getJSONApi()
                .getCards("1")
                .enqueue(object : Callback<InfoPageAndResult> {
                    override fun onFailure(call: Call<InfoPageAndResult>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<InfoPageAndResult>, response: Response<InfoPageAndResult>) {
                        if (response.isSuccessful) {
                            infoPageAndResult = response.body()
                            cardsList = ArrayList(infoPageAndResult!!.results)
                            callback.onResult(cardsList!!, null, "2")
                        }
                    }
                })

    }

    //Динамическая загрузка следующих n элементов с другой страницы.(N указывается в коде)
    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, FullInfoCard>) {
        NetworkService.getInstance()
            .getJSONApi()
            .getCards((params.key).toString())
            .enqueue(object: Callback<InfoPageAndResult> {
                override fun onFailure(call: Call<InfoPageAndResult>, t: Throwable) {
                }

                override fun onResponse(call: Call<InfoPageAndResult>, response: Response<InfoPageAndResult>) {
                    if(response.isSuccessful){
                        infoPageAndResult = response.body()
                        cardsList = ArrayList(infoPageAndResult!!.results)
                        callback.onResult(cardsList!!,(params.key.toInt()+1).toString())
                    }
                }
            })
    }


//Пока не нужна,но надо будет использовать
    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, FullInfoCard>) {
        NetworkService.getInstance()
            .getJSONApi()
            .getCards((params.key).toString())
            .enqueue(object: Callback<InfoPageAndResult> {
                override fun onFailure(call: Call<InfoPageAndResult>, t: Throwable) {
                }

                override fun onResponse(call: Call<InfoPageAndResult>, response: Response<InfoPageAndResult>) {
                    if(response.isSuccessful){
                        infoPageAndResult = response.body()
                        cardsList = ArrayList(infoPageAndResult!!.results)
                        callback.onResult(cardsList!!,params.key+(-1))
                    }
                }
            })
    }



}


