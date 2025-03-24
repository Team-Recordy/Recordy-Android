package com.viskit.video.api

import com.viskit.video.model.remote.response.ResponseGetPagingVideoDto
import com.viskit.video.model.remote.response.ResponseGetSliceVideoDto
import com.viskit.video.model.remote.response.ResponseGetVideoDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoApi {
    @GET("/api/v1/records/random")
    suspend fun getAllVideos(
        @Query("size") size: Int,
    ): List<ResponseGetVideoDto>

    @GET("/api/v1/records/recent")
    suspend fun getRecentVideos(
        @Query("keywords") keywords: List<String>?,
        @Query("cursorId", encoded = true) cursor: Long,
        @Query("size") pageSize: Int,
    ): ResponseGetSliceVideoDto

    @GET("/api/v1/records/famous")
    suspend fun getPopularVideos(
        @Query("keywords") keywords: List<String>?,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int,
    ): ResponseGetPagingVideoDto

    @GET("/api/v1/records/place")
    suspend fun getPlaceVideos(
        @Query("placeId") placeId: Int,
        @Query("cursorId", encoded = true) cursor: Long?,
        @Query("size") pageSize: Int,
    ): ResponseGetSliceVideoDto

    @GET("/api/v1/records/user/{otherUserId}")
    suspend fun getUserVideos(
        @Path("otherUserId") otherUserId: Long,
        @Query("cursorId", encoded = true) cursorId: Long?,
        @Query("size") size: Int,
    ): ResponseGetSliceVideoDto

    @GET("/api/v1/records/follow")
    suspend fun getFollowingVideos(
        @Query("cursorId", encoded = true) cursorId: Long?,
        @Query("size") size: Int,
    ): List<ResponseGetVideoDto>

    @GET("/api/v1/records/bookmarks")
    suspend fun getBookmarkVideos(
        @Query("cursorId", encoded = true) cursorId: Long?,
        @Query("size") size: Int,
    ): ResponseGetSliceVideoDto

    @POST("/api/v1/bookmarks/{recordId}")
    suspend fun postBookmark(
        @Path("recordId") recordId: Long,
    ): Boolean
}
