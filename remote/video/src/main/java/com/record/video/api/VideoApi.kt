package com.record.video.api

import com.record.video.model.remote.response.ResponseGetRecentVideoDto
import com.record.video.model.remote.response.ResponseGetVideoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApi {
    @GET("/api/v1/records")
    fun getAllVideos(
        @Query("cursorId") cursorId: Long,
        @Query("size") size: Int,
    ): List<ResponseGetVideoDto>

    @GET("/api/v1/records/recent")
    fun getRecentVideos(
        @Query("keywords") keywords: List<String>,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int,
    ): ResponseGetRecentVideoDto
}
