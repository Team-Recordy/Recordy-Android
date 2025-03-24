package com.viskit.exhibition.repository

import com.viskit.exhibition.model.PlaceUsingMap
import com.viskit.exhibition.model.SearchResult

interface SearchRepository {
    suspend fun searchExhibition(query: String): Result<List<SearchResult>>
    suspend fun searchPlace(query: String): Result<List<PlaceUsingMap>>
}
