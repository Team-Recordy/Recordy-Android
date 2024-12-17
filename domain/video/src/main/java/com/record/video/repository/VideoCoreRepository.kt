package com.record.video.repository

interface VideoCoreRepository {
    suspend fun deleteVideo(id: Long): Result<Unit>
    suspend fun watchVideo(id: Long): Result<Unit>
    suspend fun postReport(id: Long, reason: String, content: String): Result<Unit>
}
