package com.record.user.repository

import com.record.model.Cursor
import com.record.user.model.Preference
import com.record.user.model.Profile
import com.record.user.model.User

interface UserRepository {
    fun getFollowingList(
        cursorId: Long,
        size: Int,
    ): Result<Cursor<User>>

    fun getFollowerList(
        cursorId: Long,
        size: Int,
    ): Result<Cursor<User>>

    fun postFollow(
        followingId: Long,
    ): Result<Boolean>

    fun getUserProfile(
        userId: Long,
    ): Result<Profile>

    fun getUserPreference(): Result<Triple<Preference, Preference, Preference>>
}
