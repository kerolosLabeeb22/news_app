package com.example.data.mappers

import com.example.data.api.model.ArticlesItem
import com.example.data.api.model.NewsResponse
import com.example.domain.entity.ArticlesItemEntity
import com.example.domain.entity.NewsResponseEntity

fun NewsResponse.toEntity(): NewsResponseEntity{
    return NewsResponseEntity(articles?.map { it.toEntity() },message)
}

fun ArticlesItem.toEntity(): ArticlesItemEntity{
    return ArticlesItemEntity(publishedAt,author,urlToImage,description,title,url,content)
}