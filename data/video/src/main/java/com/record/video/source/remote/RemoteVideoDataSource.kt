package com.record.video.source.remote

import com.record.video.model.remote.response.ResponseGetPagingVideoDto
import com.record.video.model.remote.response.ResponseGetSliceVideoDto
import com.record.video.model.remote.response.ResponseGetVideoDto

interface RemoteVideoDataSource {
    fun getAllVideos(cursorId: Long, size: Int): List<ResponseGetVideoDto>
    fun getRecentVideos(keywords: List<String>, pageNumber: Int, pageSize: Int): ResponseGetSliceVideoDto
    fun getPopularVideos(keywords: List<String>, pageNumber: Int, pageSize: Int): ResponseGetPagingVideoDto
    fun getUserVideos(otherUserId: Long, cursorId: Long, size: Int): ResponseGetSliceVideoDto
    fun getFollowingVideos(userId: Long, cursorId: Long, size: Int): ResponseGetSliceVideoDto
    fun getBookmarkVideos(cursorId: Long, size: Int): ResponseGetSliceVideoDto
    fun bookmark(recordId: Long): Boolean
}
