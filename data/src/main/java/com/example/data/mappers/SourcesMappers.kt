package com.example.data.mappers

import com.example.data.api.model.SourcesItem
import com.example.data.api.model.SourcesResponse
import com.example.domain.entity.SourcesItemEntity
import com.example.domain.entity.SourcesResponseEntity

fun SourcesResponse.toEntity(): SourcesResponseEntity {
    return SourcesResponseEntity(sources?.map { it.toEntity() }, status, message, code)
}

fun SourcesItem.toEntity(): SourcesItemEntity {
    return SourcesItemEntity(name, id)
}