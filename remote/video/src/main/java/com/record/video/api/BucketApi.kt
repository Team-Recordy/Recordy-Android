package com.record.video.api

import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Url

interface BucketApi {
    @Multipart
    @PUT
    suspend fun uploadVideoWithS3Bucket(
        @Url url: String,
        @Part video: MultipartBody.Part,
        @Header("Content-Type") contentType: String,
    ): Unit
}
