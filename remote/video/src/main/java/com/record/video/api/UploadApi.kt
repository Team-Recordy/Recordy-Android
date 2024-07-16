package com.record.video.api

import com.record.video.model.remote.request.RequestPostVideoDto
import com.record.video.model.remote.response.ResponseGetPresignedUrlDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UploadApi {
    @GET("/api/v1/records/presigned-url")
    fun getPresignedUploadUrl(): ResponseGetPresignedUrlDto

    @POST("/api/v1/records")
    fun postRecord(@Body requestPostVideoDto: RequestPostVideoDto)
}
