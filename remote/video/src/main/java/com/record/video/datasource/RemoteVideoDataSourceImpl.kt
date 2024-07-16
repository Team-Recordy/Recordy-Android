package com.record.video.datasource

import com.record.video.api.VideoApi
import com.record.video.model.remote.response.ResponseGetRecentVideoDto
import com.record.video.model.remote.response.ResponseGetVideoDto
import com.record.video.source.remote.RemoteVideoDataSource
import javax.inject.Inject

class RemoteVideoDataSourceImpl @Inject constructor(
    private val videoApi: VideoApi,
) : RemoteVideoDataSource {
    override fun getAllVideos(cursorId: Long, size: Int): List<ResponseGetVideoDto> = videoApi.getAllVideos(cursorId, size)

    override fun getRecentVideos(keywords: List<String>, pageNumber: Int, pageSize: Int): ResponseGetRecentVideoDto = videoApi.getRecentVideos(
        keywords,
        pageNumber,
        pageSize,
    )
}
