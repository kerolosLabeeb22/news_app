package com.example.data.repository

import com.example.data.dataSource.online.NewsOnlineDataSourceImpl
import com.example.domain.entity.ArticlesItemEntity
import com.example.domain.entity.SourcesItemEntity
import com.example.domain.entity.SourcesResponseEntity
import com.example.domain.repositories.NewsOnlineDataSource
import com.example.domain.repositories.NewsRepository

class NewsRepositoryImpl(
    private val onlineDataSource: NewsOnlineDataSource
) : NewsRepository {
    override suspend fun getSources(categoryId: String): List<SourcesItemEntity> {
        return onlineDataSource.getSources(categoryId)
    }

    override suspend fun getNewsBySource(sourcesId: String): List<ArticlesItemEntity> {
        return onlineDataSource.getNewsBySource(sourcesId)
    }
}