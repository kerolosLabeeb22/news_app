package com.example.domain.usecase

import com.example.domain.entity.ArticlesItemEntity
import com.example.domain.repositories.NewsRepository
import javax.inject.Inject

class GetNewsBySourceUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend fun invoke(sourceId: String): List<ArticlesItemEntity>{
        return repository.getNewsBySource(sourceId)
    }
}