package com.viskit.video.repository

import com.viskit.common.util.toUTF8
import com.viskit.model.Cursor
import com.viskit.model.Page
import com.viskit.model.exception.ApiError
import com.viskit.video.model.VideoData
import com.viskit.video.model.remote.response.toCore
import com.viskit.video.model.remote.response.toDomain
import com.viskit.video.source.local.LocalUserInfoDataSource
import com.viskit.video.source.remote.RemoteVideoDataSource
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
        val encodedKeywords = if (keywords?.first() == "전체") null else keywords?.map { it.replace(" ", "_") }?.map { toUTF8(it) }
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
        val encodedKeywords = if (keywords?.first() == "전체") null else keywords?.map { it.replace(" ", "_") }?.map { toUTF8(it) }
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

    override suspend fun getPlaceVideos(placeId: Int, cursor: Long, pageSize: Int): Result<Cursor<VideoData>> = runCatching {
        remoteVideoDataSource.getPlaceVideos(placeId, cursor, pageSize)
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

    override suspend fun getFollowingVideos(cursorId: Long, size: Int): Result<List<VideoData>> = runCatching {
        remoteVideoDataSource.getFollowingVideos(cursorId, size)
    }.mapCatching {
        it.map { it.toDomain() }
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
