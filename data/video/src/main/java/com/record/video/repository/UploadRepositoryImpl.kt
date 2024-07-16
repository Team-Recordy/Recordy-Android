package com.record.video.repository

import com.record.model.exception.ApiError
import com.record.upload.model.UploadInfo
import com.record.upload.model.VideoInfo
import com.record.upload.repository.UploadRepository
import com.record.video.model.remote.request.toData
import com.record.video.model.remote.response.toCore
import com.record.video.source.remote.RemoteUploadDataSource
import retrofit2.HttpException
import javax.inject.Inject

class UploadRepositoryImpl @Inject constructor(
    private val remoteUploadDataSource: RemoteUploadDataSource,
) : UploadRepository {
    override suspend fun getPresignedUrl(): Result<UploadInfo> = runCatching {
        remoteUploadDataSource.getUploadUrl()
    }.mapCatching {
        it.toCore()
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

    override suspend fun uploadRecord(videoInfo: VideoInfo): Result<Unit> = runCatching {
        remoteUploadDataSource.uploadRecord(videoInfo.toData())
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
