package com.example.newsapp2.api.model

import android.util.Log
import com.example.newsapp2.api.NewsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {

    private val httpLoggingInterceptor = HttpLoggingInterceptor{ message->

        Log.e("API",  message )
    }.apply {
        level =HttpLoggingInterceptor.Level.BODY
    }

    private val okhttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttpClient)
        .build()


    val newsService = retrofit.create(NewsService::class.java)




















//    private val retrofit= Retrofit.Builder()
//        .baseUrl("https://newsapi.org/v2/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val newsService = retrofit.create(NewsService::class.java)
}