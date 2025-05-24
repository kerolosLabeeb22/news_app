package com.example.domain.repositories

import com.example.domain.entity.ArticlesItemEntity
import com.example.domain.entity.SourcesItemEntity
import com.example.domain.entity.SourcesResponseEntity

interface NewsRepository {
    suspend fun getSources(categoryId: String): List<SourcesItemEntity>
    suspend fun getNewsBySource(sourceId: String) : List<ArticlesItemEntity>
}

interface NewsOnlineDataSource{
    suspend fun getSources(categoryId: String): List<SourcesItemEntity>
    suspend fun getNewsBySource(sourceId: String): List<ArticlesItemEntity>
}