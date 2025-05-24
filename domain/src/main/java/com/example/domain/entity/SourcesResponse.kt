package com.example.domain.entity


data class SourcesItemEntity(
    val name: String? = null,
    val id: String? = null,

)

data class SourcesResponseEntity(

    val sources: List<SourcesItemEntity>? = null,

    val status: String? = null,
    val message: String? = null,
    val code: String? = null,
)
