package com.record.video.repository

import com.record.model.Cursor
import com.record.model.Page
import com.record.video.model.VideoData

interface VideoRepository {
    fun getAllVideos(cursorId: Long, pageSize: Int): Result<List<VideoData>>
    fun getRecentVideos(keywords: List<String>, pageNumber: Int, pageSize: Int): Result<Cursor<VideoData>>
    fun getPopularVideos(keywords: List<String>, pageNumber: Int, pageSize: Int): Result<Page<VideoData>>
    fun getUserVideos(otherUserId: Long, cursorId: Long, size: Int): Result<Cursor<VideoData>>
    fun getFollowingVideos(userId: Long, cursorId: Long, size: Int): Result<Cursor<VideoData>>
    fun getBookmarkVideos(cursorId: Long, size: Int): Result<Cursor<VideoData>>
    fun bookmark(videoId: Long): Result<Boolean>
}
