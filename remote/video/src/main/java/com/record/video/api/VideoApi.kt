package com.record.video.api

import com.record.video.model.remote.response.ResponseGetPagingVideoDto
import com.record.video.model.remote.response.ResponseGetSliceVideoDto
import com.record.video.model.remote.response.ResponseGetVideoDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoApi {
    @GET("/api/v1/records")
    suspend fun getAllVideos(
        @Query("cursorId") cursorId: Long,
        @Query("size") size: Int,
    ): List<ResponseGetVideoDto>

    @GET("/api/v1/records/recent")
    suspend fun getRecentVideos(
        @Query("keywords") keywords: List<String>?,
        @Query("cursorId") cursor: Long,
        @Query("size") pageSize: Int,
    ): ResponseGetSliceVideoDto

    @GET("/api/v1/records/famous")
    suspend fun getPopularVideos(
        @Query("keywords") keywords: List<String>?,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int,
    ): ResponseGetPagingVideoDto

    @GET("/api/v1/records/user/{otherUserId}")
    suspend fun getUserVideos(
        @Path("otherUserId") otherUserId: Long,
        @Query("cursorId") cursorId: Long,
        @Query("size") size: Int,
    ): ResponseGetSliceVideoDto

    @GET("/api/v1/records/following")
    suspend fun getFollowingVideos(
        @Header("userId") userId: Long,
        @Query("cursorId") cursorId: Long,
        @Query("size") size: Int,
    ): ResponseGetSliceVideoDto

    @GET("/api/v1/records/bookmarks")
    suspend fun getBookmarkVideos(
        @Query("cursorId") cursorId: Long,
        @Query("size") size: Int,
    ): ResponseGetSliceVideoDto

    @POST("/api/v1/bookmarks/{recordId}")
    suspend fun postBookmark(
        @Path("recordId") recordId: Long,
    ): Boolean
}
