package com.viskit.video.datasource

import com.viskit.video.api.VideoCoreApi
import com.viskit.video.model.remote.request.RequestPostReportDto
import com.viskit.video.source.remote.RemoteVideoCoreDataSource
import javax.inject.Inject

class RemoteVideoCoreDataSourceImpl @Inject constructor(
    private val videoCoreApi: VideoCoreApi,
) : RemoteVideoCoreDataSource {
    override suspend fun deleteVideo(recordId: Long) = videoCoreApi.deleteVideo(recordId)

    override suspend fun watchVideo(recordId: Long) = videoCoreApi.postWatchVideo(recordId)

    override suspend fun postReport(recordId: Long, reason: String, content: String) =
        videoCoreApi.postReport(RequestPostReportDto(recordId = recordId, reason = reason, content = content))
}
