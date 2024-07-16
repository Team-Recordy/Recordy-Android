package com.record.user.api

import com.record.user.model.remote.response.ResponseGetFollowerListDto
import com.record.user.model.remote.response.ResponseGetFollowingListDto
import com.record.user.model.remote.response.ResponseGetUserProfileDto
import com.record.user.model.remote.response.ResponsePostFollowDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("/api/v1/users/following")
    fun getFollowingList(
        @Query("cursorId") cursorId: Long,
        @Query("size") size: Int,
    ): ResponseGetFollowingListDto

    @GET("/api/v1/users/follower")
    fun getFollowerList(
        @Query("cursorId") cursorId: Long,
        @Query("size") size: Int,
    ): ResponseGetFollowerListDto

    @POST("/api/v1/users/follow/{followingId}")
    fun postFollow(
        @Path("followingId") followingId: Long,
    ): ResponsePostFollowDto

    @GET("/api/v1/users/profile/{userId}")
    fun getUserProfileDto(
        @Path("userId") userId: Long,
    ): ResponseGetUserProfileDto
}
