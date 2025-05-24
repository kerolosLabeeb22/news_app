package com.example.newsapp2.news

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.ArticlesItemEntity
import com.example.domain.entity.SourcesItemEntity

import com.example.domain.usecase.GetNewsBySourceUseCase
import com.example.domain.usecase.GetSourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getSourceUseCase: GetSourcesUseCase,
    private val getArticlesBySourcesUseCase : GetNewsBySourceUseCase
) : ViewModel() {

    val sourcesList = mutableStateListOf<SourcesItemEntity>()

    val articlesList = mutableStateListOf<ArticlesItemEntity>()
    val selectedSourceId = mutableStateOf("")
    val errorState = mutableStateOf("")
    val isLoading = mutableStateOf(false)

    //for using use case



    fun getSources(
        categoryAPIKEY: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getSourceUseCase.invoke(categoryAPIKEY)


                if (response.isNotEmpty()) {
                    sourcesList.addAll(response)
                }


            } catch (e: Exception) {
                errorState.value = e.message ?: "something went wrong!"
            }


        }

    }

    //--------------------------------------------------------------------------------------------------
    fun getNewsByResource() {
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedSourceId.value.isNotEmpty()) {
                try {
                    isLoading.value = true
                    val articles =
                        getArticlesBySourcesUseCase.invoke(selectedSourceId.value)

                    isLoading.value = false

                    if (articles.isNotEmpty()) {
                        articlesList.clear()
                        articlesList.addAll(articles)

                    }
                } catch (e: Exception) {
                    isLoading.value = false
                    errorState.value = e.message ?: "something went wrong!"
                }

            }
        }
    }
}