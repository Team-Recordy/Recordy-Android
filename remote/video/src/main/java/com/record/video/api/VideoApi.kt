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
    fun getAllVideos(
        @Query("cursorId") cursorId: Long,
        @Query("size") size: Int,
    ): List<ResponseGetVideoDto>

    @GET("/api/v1/records/recent")
    fun getRecentVideos(
        @Query("keywords") keywords: List<String>,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int,
    ): ResponseGetSliceVideoDto

    @GET("/api/v1/records/famous")
    fun getPopularVideos(
        @Query("keywords") keywords: List<String>,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int,
    ): ResponseGetPagingVideoDto

    @GET("/api/v1/records/user/{otherUserId}")
    fun getUserVideos(
        @Path("otherUserId") otherUserId: Long,
        @Query("cursorId") cursorId: Long,
        @Query("size") size: Int,
    ): ResponseGetSliceVideoDto

    @GET("/api/v1/records/following")
    fun getFollowingVideos(
        @Header("userId") userId: Long,
        @Query("cursorId") cursorId: Long,
        @Query("size") size: Int,
    ): ResponseGetSliceVideoDto

    @GET("/api/v1/records/bookmark")
    fun getBookmarkVideos(
        @Query("cursorId") cursorId: Long,
        @Query("size") size: Int,
    ): ResponseGetSliceVideoDto

    @POST("/api/v1/bookmarks/{recordId}")
    fun postBookmark(
        @Path("recordId") recordId: Long,
    ): Boolean
}
