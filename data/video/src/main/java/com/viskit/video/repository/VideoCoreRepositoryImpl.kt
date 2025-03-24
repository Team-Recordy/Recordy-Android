package com.viskit.video.repository

import com.viskit.model.exception.ApiError
import com.viskit.video.source.remote.RemoteVideoCoreDataSource
import retrofit2.HttpException
import javax.inject.Inject

class VideoCoreRepositoryImpl @Inject constructor(
    private val remoteVideoCoreDataSource: RemoteVideoCoreDataSource,
) : VideoCoreRepository {
    override suspend fun deleteVideo(id: Long): Result<Unit> = runCatching {
        remoteVideoCoreDataSource.deleteVideo(id)
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

    override suspend fun watchVideo(id: Long): Result<Unit> = runCatching {
        remoteVideoCoreDataSource.watchVideo(id)
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

    override suspend fun postReport(id: Long, reason: String, content: String): Result<Unit> = runCatching {
        remoteVideoCoreDataSource.postReport(id, reason, content)
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
