package com.record.keyword.repository

import com.record.keyword.model.PreferenceKeyword
import com.record.keyword.source.RemoteKeywordDataSource
import com.record.model.exception.ApiError
import retrofit2.HttpException
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val remoteKeywordDataSource: RemoteKeywordDataSource,
) : KeywordRepository {
    override suspend fun getKeywords(): Result<PreferenceKeyword> = runCatching {
        remoteKeywordDataSource.getKeywords()
    }.mapCatching {
        PreferenceKeyword(keywords = it)
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
