package com.example.startwarsapp.util

import android.util.Log
import com.example.startwarsapp.model.api.NetworkService
import com.example.startwarsapp.model.entity.FullInfoCard
import com.example.startwarsapp.model.entity.InfoPageAndResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestsUtil {


    companion object {
        fun loadFirstData(page: Int): ArrayList<FullInfoCard> {
            var cardList: ArrayList<FullInfoCard>? = null
            NetworkService.getInstance()
                .getJSONApi()
                .getCards(page.toString(), "")
                .enqueue(object : Callback<InfoPageAndResult> {
                    override fun onFailure(call: Call<InfoPageAndResult>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<InfoPageAndResult>, response: Response<InfoPageAndResult>) {
                        if (response.isSuccessful) {
                            Log.e("Req", call.request().toString())
                            val infoPageAndResult: InfoPageAndResult = response.body()!!
                            cardList = ArrayList(infoPageAndResult.results)


                        }
                    }
                })
            return cardList!!
        }


        fun loadDataWithFilter(filter: String): ArrayList<FullInfoCard> {

            var cardsList: ArrayList<FullInfoCard>? = null
            NetworkService.getInstance()
                .getJSONApi()
                .getCards("", filter)
                .enqueue(object : Callback<InfoPageAndResult> {
                    override fun onFailure(call: Call<InfoPageAndResult>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<InfoPageAndResult>, response: Response<InfoPageAndResult>) {
                        if (response.isSuccessful) {
                            Log.e("Req", call.request().toString())
                            val infoPageAndResult: InfoPageAndResult = response.body()!!
                            cardsList = ArrayList(infoPageAndResult.results)


                        }
                    }
                })
            return cardsList!!
        }


        fun loadData(page: Int):ArrayList<FullInfoCard> {
            var cardsList: ArrayList<FullInfoCard>? = null
            NetworkService.getInstance()
                .getJSONApi()
                .getCards(page.toString(), "")
                .enqueue(object : Callback<InfoPageAndResult> {
                    override fun onFailure(call: Call<InfoPageAndResult>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<InfoPageAndResult>, response: Response<InfoPageAndResult>) {
                        if (response.isSuccessful) {
                            Log.e("Req", call.request().toString())
                            val infoPageAndResult: InfoPageAndResult = response.body()!!
                            cardsList = ArrayList(infoPageAndResult.results)


                        }
                    }
                })
            return cardsList!!
        }
    }
}