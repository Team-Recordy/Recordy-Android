package com.viskit.video.api

import com.viskit.video.model.remote.request.RequestPostVideoDto
import com.viskit.video.model.remote.response.ResponseGetPresignedUrlDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UploadApi {
    @GET("/api/v1/records/presigned-url")
    suspend fun getPresignedUploadUrl(): ResponseGetPresignedUrlDto

    @POST("/api/v1/records")
    suspend fun postRecord(@Body requestPostVideoDto: com.viskit.video.model.remote.request.RequestPostVideoDto)
}
