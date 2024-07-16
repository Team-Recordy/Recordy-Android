package com.record.video.source.remote

interface RemoteVideoCoreDataSource {
    fun deleteVideo(recordId: Long)
    fun watchVideo(recordId: Long)
}
