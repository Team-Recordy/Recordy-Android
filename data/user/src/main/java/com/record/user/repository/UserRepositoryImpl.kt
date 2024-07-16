package com.record.user.repository

import com.record.model.Cursor
import com.record.model.exception.ApiError
import com.record.user.model.Preference
import com.record.user.model.Profile
import com.record.user.model.User
import com.record.user.model.remote.response.toCore
import com.record.user.model.remote.response.toDomain
import com.record.user.source.remote.RemoteUserDataSource
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
) : UserRepository {
    override fun getFollowingList(cursorId: Long, size: Int): Result<Cursor<User>> = runCatching {
        remoteUserDataSource.getFollowerList(cursorId, size)
    }.mapCatching {
        it.toCore()
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> {
                throw ApiError(exception.message())
            }

            else -> {
                throw exception
            }
        }
    }

    override fun getFollowerList(cursorId: Long, size: Int): Result<Cursor<User>> = runCatching {
        remoteUserDataSource.getFollowerList(cursorId, size)
    }.mapCatching {
        it.toCore()
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> {
                throw ApiError(exception.message())
            }

            else -> {
                throw exception
            }
        }
    }

    override fun postFollow(followingId: Long): Result<Boolean> = runCatching {
        remoteUserDataSource.postFollow(followingId)
    }.map {
        it
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> {
                throw ApiError(exception.message())
            }

            else -> {
                throw exception
            }
        }
    }

    override fun getUserProfile(userId: Long): Result<Profile> = runCatching {
        remoteUserDataSource.getUserProfile(userId)
    }.mapCatching {
        it.toDomain()
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> {
                throw ApiError(exception.message())
            }

            else -> {
                throw exception
            }
        }
    }

    override fun getUserPreference(): Result<Triple<Preference, Preference, Preference>> = runCatching {
        remoteUserDataSource.getUserPreference().preference
    }.mapCatching {
        Triple(
            Preference(it[0][0], it[0][1].toInt()),
            Preference(it[1][0], it[1][1].toInt()),
            Preference(it[2][0], it[2][1].toInt()),
        )
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> {
                throw ApiError(exception.message())
            }

            else -> {
                throw exception
            }
        }
    }
}
