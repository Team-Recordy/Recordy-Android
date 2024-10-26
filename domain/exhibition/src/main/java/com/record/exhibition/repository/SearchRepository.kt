package com.record.exhibition.repository

import com.record.exhibition.model.SearchResult

interface SearchRepository {
    suspend fun searchExhibition(query: String): Result<List<SearchResult>>
}
