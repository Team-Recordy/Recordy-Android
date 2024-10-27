package com.example.exhibition.repository

import com.example.exhibition.model.remote.response.toDomain
import com.example.exhibition.source.remote.RemoteSearchDataSource
import com.record.exhibition.model.PlaceUsingMap
import com.record.exhibition.model.SearchResult
import com.record.exhibition.repository.SearchRepository
import com.record.model.exception.ApiError
import retrofit2.HttpException
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: RemoteSearchDataSource,
) : SearchRepository {
    override suspend fun searchExhibition(query: String): Result<List<SearchResult>> = runCatching {
        searchDataSource.searchExhibition(query)
    }.mapCatching {
        it.map { it.toDomain() }
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> {
                throw ApiError(exception.message())
            }

            else -> {
                throw exception
            }
        }
    }

    override suspend fun searchPlace(query: String): Result<List<PlaceUsingMap>> = runCatching {
        searchDataSource.searchPlace(query)
    }.mapCatching {
        it.map { it.toDomain() }
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> {
                throw ApiError(exception.message())
            }

            else -> {
                throw exception
            }
        }
    }
}
