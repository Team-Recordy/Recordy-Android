package com.record.video.source.remote

import com.record.video.model.remote.response.ResponseGetBookmarkSliceVideoDto
import com.record.video.model.remote.response.ResponseGetPagingVideoDto
import com.record.video.model.remote.response.ResponseGetSliceVideoDto
import com.record.video.model.remote.response.ResponseGetVideoDto

interface RemoteVideoDataSource {
    suspend fun getAllVideos(cursorId: Long, size: Int): List<ResponseGetVideoDto>
    suspend fun getRecentVideos(keywords: List<String>?, cursor: Long, pageSize: Int): ResponseGetSliceVideoDto
    suspend fun getPopularVideos(keywords: List<String>?, pageNumber: Int, pageSize: Int): ResponseGetPagingVideoDto
    suspend fun getUserVideos(otherUserId: Long, cursorId: Long, size: Int): ResponseGetSliceVideoDto
    suspend fun getFollowingVideos(cursorId: Long, size: Int): ResponseGetSliceVideoDto
    suspend fun getBookmarkVideos(cursorId: Long, size: Int): ResponseGetBookmarkSliceVideoDto
    suspend fun bookmark(recordId: Long): Boolean
}
