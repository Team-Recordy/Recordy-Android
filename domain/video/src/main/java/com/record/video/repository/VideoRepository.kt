package com.record.video.repository

import com.record.model.Cursor
import com.record.video.model.VideoData

interface VideoRepository {
    fun getAllVideos(cursorId: Long, pageSize: Int): Result<List<VideoData>>
    fun getRecentVideos(keywords: List<String>, pageNumber: Int, pageSize: Int): Result<Cursor<VideoData>>
}
