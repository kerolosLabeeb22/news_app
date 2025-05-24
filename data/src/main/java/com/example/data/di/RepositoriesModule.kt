package com.example.data.di

import com.example.data.api.NewsService
import com.example.data.dataSource.online.NewsOnlineDataSourceImpl
import com.example.data.repository.NewsRepositoryImpl
import com.example.domain.repositories.NewsOnlineDataSource
import com.example.domain.repositories.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Singleton
    @Provides
    fun provideNewsOnlineDataSource(newsService: NewsService): NewsOnlineDataSource {
        return NewsOnlineDataSourceImpl(newsService)
    }

    @Singleton
    @Provides
    fun provideNewsRepository(onlineDataSource: NewsOnlineDataSource): NewsRepository{
        return NewsRepositoryImpl(onlineDataSource)

    }

}