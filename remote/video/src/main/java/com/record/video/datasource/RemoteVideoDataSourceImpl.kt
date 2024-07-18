package com.record.video.datasource

import com.record.video.api.VideoApi
import com.record.video.model.remote.response.ResponseGetPagingVideoDto
import com.record.video.model.remote.response.ResponseGetSliceVideoDto
import com.record.video.model.remote.response.ResponseGetVideoDto
import com.record.video.source.remote.RemoteVideoDataSource
import javax.inject.Inject

class RemoteVideoDataSourceImpl @Inject constructor(
    private val videoApi: VideoApi,
) : RemoteVideoDataSource {
    override suspend fun getAllVideos(cursorId: Long, size: Int): List<ResponseGetVideoDto> = videoApi.getAllVideos(cursorId, size)

    override suspend fun getRecentVideos(keywords: List<String>?, cursor: Long, pageSize: Int): ResponseGetSliceVideoDto =
        videoApi.getRecentVideos(
            keywords,
            cursor,
            pageSize,
        )

    override suspend fun getPopularVideos(keywords: List<String>?, pageNumber: Int, pageSize: Int): ResponseGetPagingVideoDto =
        videoApi.getPopularVideos(keywords, pageNumber, pageSize)

    override suspend fun getUserVideos(otherUserId: Long, cursorId: Long, size: Int): ResponseGetSliceVideoDto =
        videoApi.getUserVideos(otherUserId, cursorId, size)

    override suspend fun getFollowingVideos(userId: Long, cursorId: Long, size: Int): ResponseGetSliceVideoDto =
        videoApi.getFollowingVideos(userId, cursorId, size)

    override suspend fun getBookmarkVideos(cursorId: Long, size: Int): ResponseGetSliceVideoDto = videoApi.getBookmarkVideos(cursorId, size)

    override suspend fun bookmark(recordId: Long): Boolean = videoApi.postBookmark(recordId)
}
