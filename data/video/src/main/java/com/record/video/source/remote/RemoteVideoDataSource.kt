package com.record.video.source.remote

import com.record.video.model.remote.response.ResponseGetRecentVideoDto
import com.record.video.model.remote.response.ResponseGetVideoDto

interface RemoteVideoDataSource {
    fun getAllVideos(cursorId: Long, size: Int): List<ResponseGetVideoDto>
    fun getRecentVideos(keywords: List<String>, pageNumber: Int, pageSize: Int): ResponseGetRecentVideoDto
}
