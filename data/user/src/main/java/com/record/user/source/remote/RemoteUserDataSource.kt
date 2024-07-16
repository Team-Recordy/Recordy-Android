package com.record.user.source.remote

import com.record.user.model.remote.response.ResponseGetFollowerListDto
import com.record.user.model.remote.response.ResponseGetFollowingListDto
import com.record.user.model.remote.response.ResponseGetUserPreferenceDto
import com.record.user.model.remote.response.ResponseGetUserProfileDto

interface RemoteUserDataSource {
    fun getFollowingList(
        cursorId: Long,
        size: Int,
    ): ResponseGetFollowingListDto

    fun getFollowerList(
        cursorId: Long,
        size: Int,
    ): ResponseGetFollowerListDto

    fun postFollow(
        followingId: Long,
    ): Boolean

    fun getUserProfile(
        userId: Long,
    ): ResponseGetUserProfileDto

    fun getUserPreference(): ResponseGetUserPreferenceDto
}
