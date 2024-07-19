package com.record.user.datasource

import com.record.user.api.UserApi
import com.record.user.model.remote.response.ResponseGetFollowerListDto
import com.record.user.model.remote.response.ResponseGetFollowingListDto
import com.record.user.model.remote.response.ResponseGetUserPreferenceDto
import com.record.user.model.remote.response.ResponseGetUserProfileDto
import com.record.user.source.remote.RemoteUserDataSource
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : RemoteUserDataSource {
    override suspend fun getFollowingList(cursorId: Long, size: Int): ResponseGetFollowingListDto = userApi.getFollowingList(cursorId, size)

    override suspend fun getFollowerList(cursorId: Long, size: Int): ResponseGetFollowerListDto = userApi.getFollowerList(cursorId, size)

    override suspend fun postFollow(followingId: Long): Boolean = userApi.postFollow(followingId)

    override suspend fun getUserProfile(userId: Long): ResponseGetUserProfileDto = userApi.getUserProfileDto(userId)

    override suspend fun getUserPreference(): ResponseGetUserPreferenceDto = userApi.getUserPreference()
}
