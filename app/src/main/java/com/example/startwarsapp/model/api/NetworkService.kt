package com.example.startwarsapp.model.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {
    private var mRetrofit: Retrofit?=null

    companion object{
        private var mInstance :NetworkService?= null
        private val BASE_URL:String = "https://swapi.co/api/people/"

        public fun getInstance():NetworkService {
            if(mInstance==null) mInstance = NetworkService()
            return mInstance!!
        }

        public fun getOkHttpClient():OkHttpClient{
            var logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.NONE)
            var okClient:OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            return okClient
        }


    }

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    public fun getJSONApi():JSONPlaceHolderApi{
        return mRetrofit!!.create(JSONPlaceHolderApi::class.java)
    }


}