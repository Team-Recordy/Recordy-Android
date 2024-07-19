package com.record.video.source.remote

import com.record.video.model.remote.request.RequestPostVideoDto
import com.record.video.model.remote.response.ResponseGetPresignedUrlDto
import okhttp3.MultipartBody
import java.io.File

interface RemoteUploadDataSource {
    suspend fun getUploadUrl(): ResponseGetPresignedUrlDto
    suspend fun uploadRecord(requestPostVideoDto: RequestPostVideoDto)
    suspend fun uploadVideoToS3Bucket(url: String, file: File):String
    suspend fun uploadThumbnailToS3Bucket(url: String, file: File):String
}
