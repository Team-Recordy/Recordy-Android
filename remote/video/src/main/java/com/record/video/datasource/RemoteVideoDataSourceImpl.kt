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
    override fun getAllVideos(cursorId: Long, size: Int): List<ResponseGetVideoDto> = videoApi.getAllVideos(cursorId, size)

    override fun getRecentVideos(keywords: List<String>, pageNumber: Int, pageSize: Int): ResponseGetSliceVideoDto = videoApi.getRecentVideos(
        keywords,
        pageNumber,
        pageSize,
    )

    override fun getPopularVideos(keywords: List<String>, pageNumber: Int, pageSize: Int): ResponseGetPagingVideoDto =
        videoApi.getPopularVideos(keywords, pageNumber, pageSize)

    override fun getUserVideos(otherUserId: Long, cursorId: Long, size: Int): ResponseGetSliceVideoDto =
        videoApi.getUserVideos(otherUserId, cursorId, size)

    override fun getFollowingVideos(userId: Long, cursorId: Long, size: Int): ResponseGetSliceVideoDto =
        videoApi.getFollowingVideos(userId, cursorId, size)

    override fun getBookmarkVideos(cursorId: Long, size: Int): ResponseGetSliceVideoDto = videoApi.getBookmarkVideos(cursorId, size)

    override fun bookmark(recordId: Long): Boolean = videoApi.postBookmark(recordId)
}
