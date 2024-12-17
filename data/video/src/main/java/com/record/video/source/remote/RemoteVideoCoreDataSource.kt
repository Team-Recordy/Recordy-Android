package com.record.video.source.remote

interface RemoteVideoCoreDataSource {
    suspend fun deleteVideo(recordId: Long)
    suspend fun watchVideo(recordId: Long)
    suspend fun postReport(recordId: Long, reason: String, content: String)
}
