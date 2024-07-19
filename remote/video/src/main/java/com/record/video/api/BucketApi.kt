package com.record.video.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Url

interface BucketApi {
    @PUT
    suspend fun uploadVideoWithS3Video(
        @Url url: String,
        @Body requestBody: RequestBody,
        @Header("Content-Type") contentType: String = "application/octet-stream",
    ): Response<ResponseBody>

    @PUT
    suspend fun uploadThumbnailWithS3Video(
        @Url url: String,
        @Body requestBody: RequestBody,
        @Header("Content-Type") contentType: String = "application/octet-stream",
    ): Response<ResponseBody>
}
