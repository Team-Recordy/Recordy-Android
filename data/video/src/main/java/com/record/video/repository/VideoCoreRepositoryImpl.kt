package com.record.video.repository

import com.record.model.exception.ApiError
import com.record.video.source.remote.RemoteVideoCoreDataSource
import retrofit2.HttpException
import javax.inject.Inject

class VideoCoreRepositoryImpl @Inject constructor(
    private val remoteVideoCoreDataSource: RemoteVideoCoreDataSource,
) : VideoCoreRepository {
    override fun deleteVideo(id: Long): Result<Unit> = runCatching {
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

    override fun watchVideo(id: Long): Result<Unit> = runCatching {
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
}
