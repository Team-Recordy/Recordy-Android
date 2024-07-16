package com.record.user.source.remote

import com.record.user.model.remote.response.ResponseGetFollowerListDto
import com.record.user.model.remote.response.ResponseGetFollowingListDto
import com.record.user.model.remote.response.ResponseGetUserPreferenceDto
import com.record.user.model.remote.response.ResponseGetUserProfileDto

interface RemoteUserDataSource {
    suspend fun getFollowingList(
        cursorId: Long,
        size: Int,
    ): ResponseGetFollowingListDto

    suspend fun getFollowerList(
        cursorId: Long,
        size: Int,
    ): ResponseGetFollowerListDto

    suspend fun postFollow(
        followingId: Long,
    ): Boolean

    suspend fun getUserProfile(
        userId: Long,
    ): ResponseGetUserProfileDto

    suspend fun getUserPreference(): ResponseGetUserPreferenceDto
}
