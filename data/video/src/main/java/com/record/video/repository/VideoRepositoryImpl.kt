package com.record.video.repository

import com.record.common.util.toUTF8HexString
import com.record.model.Cursor
import com.record.model.exception.ApiError
import com.record.video.model.VideoData
import com.record.video.model.remote.response.toCore
import com.record.video.model.remote.response.toDomain
import com.record.video.source.remote.RemoteVideoDataSource
import retrofit2.HttpException
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val remoteVideoDataSource: RemoteVideoDataSource,
) : VideoRepository {
    override fun getAllVideos(cursorId: Long, pageSize: Int): Result<List<VideoData>> = runCatching {
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

    override fun getRecentVideos(keywords: List<String>, pageNumber: Int, pageSize: Int): Result<Cursor<VideoData>> = runCatching {
        val encodedKeywords = keywords.map { toUTF8HexString(it) }
        remoteVideoDataSource.getRecentVideos(encodedKeywords, pageNumber, pageSize)
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
}
