package com.record.user.repository

import com.record.model.Cursor
import com.record.user.model.Preference
import com.record.user.model.Profile
import com.record.user.model.User

interface UserRepository {
    suspend fun getFollowingList(
        cursorId: Long,
        size: Int,
    ): Result<Cursor<User>>

    suspend fun getFollowerList(
        cursorId: Long,
        size: Int,
    ): Result<Cursor<User>>

    suspend fun postFollow(
        followingId: Long,
    ): Result<Boolean>

    suspend fun getUserProfile(
        userId: Long,
    ): Result<Profile>

    suspend fun getUserPreference(): Result<Triple<Preference, Preference, Preference>>

    suspend fun saveUserId(userId: Long): Result<Unit>

    suspend fun getUserId(): Result<Long>
}
