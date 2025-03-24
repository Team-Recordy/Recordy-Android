package com.viskit.user.api

import com.viskit.user.model.remote.request.RequestUpdateProfileDto
import com.viskit.user.model.remote.response.ResponseGetFollowerListDto
import com.viskit.user.model.remote.response.ResponseGetFollowingListDto
import com.viskit.user.model.remote.response.ResponseGetUserPreferenceDto
import com.viskit.user.model.remote.response.ResponseGetUserProfileDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("/api/v1/users/following")
    suspend fun getFollowingList(
        @Query("cursorId", encoded = true) cursorId: Long?,
        @Query("size") size: Int,
    ): ResponseGetFollowingListDto

    @GET("/api/v1/users/follower")
    suspend fun getFollowerList(
        @Query("cursorId", encoded = true) cursorId: Long?,
        @Query("size") size: Int,
    ): ResponseGetFollowerListDto

    @PATCH("/api/v1/users")
    suspend fun updateProfile(
        @Body updateProfileRequest: RequestUpdateProfileDto,
    )

    @POST("/api/v1/users/follow/{followingId}")
    suspend fun postFollow(
        @Path("followingId") followingId: Long,
    ): Boolean

    @GET("/api/v1/users/profile/{otherUserId}")
    suspend fun getUserProfileDto(
        @Path("otherUserId") userId: Long,
    ): ResponseGetUserProfileDto

    @GET("/api/v1/preference")
    suspend fun getUserPreference(): ResponseGetUserPreferenceDto
}
