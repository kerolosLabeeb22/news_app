package com.example.newsapp2.api

import com.example.newsapp2.api.model.NewsResponse
import com.example.newsapp2.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines/sources")
    fun getResource(@Query("apiKey") apikey:String ="a7121b602b324625987412c439a51923") : Call<SourcesResponse>

    @GET("everything")
    fun getNewsBySources(
        @Query("sources") sources : String,
        @Query("apiKey") apikey:String ="a7121b602b324625987412c439a51923"
    ) : Call<NewsResponse>
}