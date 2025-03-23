package com.viskit.keyword.repository

import com.viskit.keyword.model.PreferenceKeyword
import com.viskit.keyword.source.RemoteKeywordDataSource
import com.viskit.model.exception.ApiError
import retrofit2.HttpException
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val remoteKeywordDataSource: RemoteKeywordDataSource,
) : KeywordRepository {
    override suspend fun getKeywords(): Result<PreferenceKeyword> = runCatching {
        remoteKeywordDataSource.getKeywords()
    }.mapCatching {
        PreferenceKeyword(keywords = it.map { char -> char.replace("_", " ") })
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
