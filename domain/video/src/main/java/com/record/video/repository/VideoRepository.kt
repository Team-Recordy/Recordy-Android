package com.record.video.repository

import com.record.model.Cursor
import com.record.model.Page
import com.record.video.model.VideoData

interface VideoRepository {
    suspend fun getAllVideos(cursorId: Long, pageSize: Int): Result<List<VideoData>>
    suspend fun getRecentVideos(keywords: List<String>?, pageNumber: Int, pageSize: Int): Result<Cursor<VideoData>>
    suspend fun getPopularVideos(keywords: List<String>?, pageNumber: Int, pageSize: Int): Result<Page<VideoData>>
    suspend fun getUserVideos(otherUserId: Long, cursorId: Long, size: Int): Result<Cursor<VideoData>>
    suspend fun getFollowingVideos(userId: Long, cursorId: Long, size: Int): Result<Cursor<VideoData>>
    suspend fun getBookmarkVideos(cursorId: Long, size: Int): Result<Cursor<VideoData>>
    suspend fun bookmark(videoId: Long): Result<Boolean>
}
