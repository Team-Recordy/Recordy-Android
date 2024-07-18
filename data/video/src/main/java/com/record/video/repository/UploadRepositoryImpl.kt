package com.record.video.repository

import com.record.model.exception.ApiError
import com.record.upload.model.UploadInfo
import com.record.upload.model.VideoInfo
import com.record.upload.repository.UploadRepository
import com.record.video.model.remote.request.toData
import com.record.video.model.remote.response.toCore
import com.record.video.source.remote.RemoteUploadDataSource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
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

    override suspend fun uploadVideoToS3Bucket(url: String, file: File): Result<Unit> = runCatching {
        val requestFile = file.asRequestBody("video/mp4".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        remoteUploadDataSource.uploadVideoToS3Bucket(url = url, body)
    }.recoverCatching { exception: Throwable ->
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
