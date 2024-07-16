package com.record.video.api

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url

interface BucketApi {
    @Multipart
    @POST

    suspend fun uploadVideoWithS3Bucket(
        @Url url: String,
        @Part video: MultipartBody.Part,
    ): Unit
}
