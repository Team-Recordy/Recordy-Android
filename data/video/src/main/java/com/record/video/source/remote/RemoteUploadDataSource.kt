package com.record.video.source.remote

import com.record.video.model.remote.request.RequestPostVideoDto
import com.record.video.model.remote.response.ResponseGetPresignedUrlDto
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.Url

interface RemoteUploadDataSource {
    suspend fun getUploadUrl(): ResponseGetPresignedUrlDto
    suspend fun uploadRecord(requestPostVideoDto: RequestPostVideoDto)
    suspend fun uploadVideoToS3Bucket(url: String,multipart: MultipartBody.Part)
}
