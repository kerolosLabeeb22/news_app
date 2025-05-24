package com.example.domain.usecase

import com.example.domain.entity.SourcesItemEntity
import com.example.domain.repositories.NewsRepository
import javax.inject.Inject

class GetSourcesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend fun invoke(category: String): List<SourcesItemEntity>{
        return repository.getSources(category)
    }
}