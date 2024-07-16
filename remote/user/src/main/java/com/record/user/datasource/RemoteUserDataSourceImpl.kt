package com.record.user.datasource

import com.record.user.api.UserApi
import com.record.user.model.remote.response.ResponseGetFollowerListDto
import com.record.user.model.remote.response.ResponseGetFollowingListDto
import com.record.user.model.remote.response.ResponseGetUserPreferenceDto
import com.record.user.model.remote.response.ResponseGetUserProfileDto
import com.record.user.model.remote.response.ResponsePostFollowDto
import com.record.user.source.remote.RemoteUserDataSource
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : RemoteUserDataSource {
    override fun getFollowingList(cursorId: Long, size: Int): ResponseGetFollowingListDto = userApi.getFollowingList(cursorId, size)

    override fun getFollowerList(cursorId: Long, size: Int): ResponseGetFollowerListDto = userApi.getFollowerList(cursorId, size)

    override fun postFollow(followingId: Long): Boolean = userApi.postFollow(followingId)

    override fun getUserProfile(userId: Long): ResponseGetUserProfileDto = userApi.getUserProfileDto(userId)

    override fun getUserPreference(): ResponseGetUserPreferenceDto = userApi.getUserPreference()
}
