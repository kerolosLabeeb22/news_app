package com.example.data.dataSource.online

import com.example.data.api.NewsService
import com.example.data.mappers.toEntity
import com.example.domain.entity.ArticlesItemEntity
import com.example.domain.entity.SourcesItemEntity
import com.example.domain.entity.SourcesResponseEntity
import com.example.domain.repositories.NewsOnlineDataSource

class NewsOnlineDataSourceImpl(
    val newsService: NewsService
) : NewsOnlineDataSource {
    override suspend fun getSources(categoryId: String): List<SourcesItemEntity> {
        return newsService.getResource(categoryId).body()?.sources?.map { it.toEntity() }
            ?: emptyList()

    }

    override suspend fun getNewsBySource(sourceId: String): List<ArticlesItemEntity> {
        return newsService.getNewsBySources(sourceId).body()?.articles?.map { it.toEntity() }
            ?: emptyList()
    }

}


