package com.example.data.api

import com.example.data.api.model.NewsResponse
import com.example.data.api.model.SourcesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines/sources")
    suspend fun getResource(
        @Query("category") category: String,
        @Query("apiKey") apikey: String = "a7121b602b324625987412c439a51923"
    ): Response<SourcesResponse>

    @GET("everything")
    suspend fun getNewsBySources(
        @Query("sources") sources: String,
        @Query("apiKey") apikey: String = "a7121b602b324625987412c439a51923"
    ): Response<NewsResponse>
}