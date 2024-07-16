package com.record.video.repository

interface VideoCoreRepository {
    fun deleteVideo(id: Long): Result<Unit>
    fun watchVideo(id: Long): Result<Unit>
}
