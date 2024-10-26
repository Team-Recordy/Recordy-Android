package com.record.exhibition.model


data class SearchResult(
    val id: Long,
    val type: ResultType,
    val address: String,
    val name: String,
)
