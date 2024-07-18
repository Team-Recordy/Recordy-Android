package com.record.video.repository

import com.record.common.util.toUTF8
import com.record.model.Cursor
import com.record.model.Page
import com.record.model.exception.ApiError
import com.record.video.model.VideoData
import com.record.video.model.remote.response.toCore
import com.record.video.model.remote.response.toDomain
import com.record.video.source.local.LocalUserInfoDataSource
import com.record.video.source.remote.RemoteVideoDataSource
import retrofit2.HttpException
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val remoteVideoDataSource: RemoteVideoDataSource,
    private val localUserInfoDataSource: LocalUserInfoDataSource,
) : VideoRepository {
    override suspend fun getAllVideos(cursorId: Long, pageSize: Int): Result<List<VideoData>> = runCatching {
        remoteVideoDataSource.getAllVideos(cursorId, pageSize)
    }.mapCatching {
        it.map { video -> video.toDomain() }
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

    override suspend fun getRecentVideos(keywords: List<String>?, cursor: Long, pageSize: Int): Result<Cursor<VideoData>> = runCatching {
        val encodedKeywords = keywords?.map { it.replace(" ", "_") }?.map { toUTF8(it) }

        remoteVideoDataSource.getRecentVideos(encodedKeywords, cursor, pageSize)
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

    override suspend fun getPopularVideos(keywords: List<String>?, pageNumber: Int, pageSize: Int): Result<Page<VideoData>> = runCatching {
        val encodedKeywords = keywords?.map { it.replace(" ", "_") }?.map { toUTF8(it) }
        remoteVideoDataSource.getPopularVideos(encodedKeywords, pageNumber, pageSize)
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

    override suspend fun getUserVideos(otherUserId: Long, cursorId: Long, size: Int): Result<Cursor<VideoData>> = runCatching {
        remoteVideoDataSource.getUserVideos(otherUserId, cursorId, size)
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

    override suspend fun getMyVideos(cursorId: Long, size: Int): Result<Cursor<VideoData>> = runCatching {
        remoteVideoDataSource.getUserVideos(
            localUserInfoDataSource.getMyId(),
            cursorId,
            size,
        )
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

    override suspend fun getFollowingVideos(userId: Long, cursorId: Long, size: Int): Result<Cursor<VideoData>> = runCatching {
        remoteVideoDataSource.getFollowingVideos(userId, cursorId, size)
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

    override suspend fun getBookmarkVideos(cursorId: Long, size: Int): Result<Cursor<VideoData>> = runCatching {
        remoteVideoDataSource.getBookmarkVideos(cursorId, size)
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

    override suspend fun bookmark(videoId: Long): Result<Boolean> = runCatching {
        remoteVideoDataSource.bookmark(videoId)
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
