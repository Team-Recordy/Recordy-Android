package com.record.user.source.remote

import com.record.user.model.remote.response.ResponseGetFollowerListDto
import com.record.user.model.remote.response.ResponseGetFollowingListDto
import com.record.user.model.remote.response.ResponseGetUserProfileDto
import com.record.user.model.remote.response.ResponsePostFollowDto

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
    ): ResponsePostFollowDto

    fun getUserProfile(
        userId: Long,
    ): ResponseGetUserProfileDto
}
