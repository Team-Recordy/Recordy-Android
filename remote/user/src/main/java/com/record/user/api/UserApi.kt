package com.record.user.api

import com.record.user.model.remote.response.ResponseGetFollowerListDto
import com.record.user.model.remote.response.ResponseGetFollowingListDto
import com.record.user.model.remote.response.ResponseGetUserPreferenceDto
import com.record.user.model.remote.response.ResponseGetUserProfileDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("/api/v1/users/following")
    suspend fun getFollowingList(
        @Query("cursorId") cursorId: Long,
        @Query("size") size: Int,
    ): ResponseGetFollowingListDto

    @GET("/api/v1/users/follower")
    suspend fun getFollowerList(
        @Query("cursorId") cursorId: Long,
        @Query("size") size: Int,
    ): ResponseGetFollowerListDto

    @POST("/api/v1/users/follow/{followingId}")
    suspend fun postFollow(
        @Path("followingId") followingId: Long,
    ): Boolean

    @GET("/api/v1/users/profile/{userId}")
    suspend fun getUserProfileDto(
        @Path("userId") userId: Long,
    ): ResponseGetUserProfileDto

    @GET("/api/v1/preference")
    suspend fun getUserPreference(): ResponseGetUserPreferenceDto
}
