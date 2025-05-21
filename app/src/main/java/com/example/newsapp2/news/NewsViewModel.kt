package com.example.newsapp2.news

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.newsapp2.api.model.ApiManager
import com.example.newsapp2.api.model.ArticlesItem
import com.example.newsapp2.api.model.NewsResponse
import com.example.newsapp2.api.model.SourcesItem
import com.example.newsapp2.api.model.SourcesResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.addAll

class NewsViewModel : ViewModel() {

    val sourcesList = mutableStateListOf<SourcesItem>()

    val articlesList = mutableStateListOf<ArticlesItem>()
    val selectedSourceId = mutableStateOf("")
    val errorState = mutableStateOf("")

    val isLoading = mutableStateOf(false)

    fun getSources(
        categoryAPIKEY: String,
    ) {
        ApiManager.newsService.getResource(categoryAPIKEY)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>, response: Response<SourcesResponse>

                ) {
                    if (response.isSuccessful) {
                        val list = response.body()?.sources
                        if (list?.isNotEmpty() == true) {
                            sourcesList.addAll(list as List<SourcesItem>)
                        }
                    }else{
                        val errorBody = response.errorBody()?.string()
                        val gson = Gson()
                        val sourcesResponse= gson.fromJson(errorBody, SourcesResponse::class.java)
                        errorState.value = "${sourcesResponse.message}"
                    }

                    Log.e("TAG", "onResponse:${response.body()} ")
                }

                override fun onFailure(
                    call: Call<SourcesResponse>, throwable: Throwable
                ) {
                    errorState.value = throwable.message ?: "something went wrong!"

                }

            })
    }

    fun getNewsByResource() {
        if (selectedSourceId.value.isNotEmpty()) {
            isLoading.value = true
            ApiManager.newsService.getNewsBySources(sources = selectedSourceId.value)
                .enqueue(object : Callback<NewsResponse> {
                    override fun onResponse(
                        p0: Call<NewsResponse>, response: Response<NewsResponse>
                    ) {
                        isLoading.value = false
                        val list = response.body()?.articles
                        if (list?.isNotEmpty() == true) {

                            articlesList.addAll(list as List<ArticlesItem>)
                        }
                    }

                    override fun onFailure(p0: Call<NewsResponse>, p1: Throwable) {
                        isLoading.value = false
                        errorState.value = p1.message ?: "something went wrong!"
                    }

                })
        }
    }
}