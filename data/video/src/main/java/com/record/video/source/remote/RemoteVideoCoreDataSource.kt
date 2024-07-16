package com.record.video.source.remote

interface RemoteVideoCoreDataSource {
    suspend fun deleteVideo(recordId: Long)
    suspend fun watchVideo(recordId: Long)
}
