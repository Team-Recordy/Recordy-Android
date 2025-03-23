package com.viskit.user.datasource

import com.viskit.user.api.UserApi
import com.viskit.user.model.remote.request.RequestUpdateProfileDto
import com.viskit.user.model.remote.response.ResponseGetFollowerListDto
import com.viskit.user.model.remote.response.ResponseGetFollowingListDto
import com.viskit.user.model.remote.response.ResponseGetUserPreferenceDto
import com.viskit.user.model.remote.response.ResponseGetUserProfileDto
import com.viskit.user.source.remote.RemoteUserDataSource
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : RemoteUserDataSource {
    override suspend fun getFollowingList(cursorId: Long, size: Int): ResponseGetFollowingListDto = userApi.getFollowingList(if (cursorId == 0L) null else cursorId, size)

    override suspend fun getFollowerList(cursorId: Long, size: Int): ResponseGetFollowerListDto = userApi.getFollowerList(if (cursorId == 0L) null else cursorId, size)

    override suspend fun postFollow(followingId: Long): Boolean = userApi.postFollow(followingId)

    override suspend fun getUserProfile(userId: Long): ResponseGetUserProfileDto = userApi.getUserProfileDto(userId)

    override suspend fun getUserPreference(): ResponseGetUserPreferenceDto = userApi.getUserPreference()
    override suspend fun updateUserProfile(nickname: String, profileImageUrl: String?) =
        userApi.updateProfile(RequestUpdateProfileDto(nickname, profileImageUrl))
}
