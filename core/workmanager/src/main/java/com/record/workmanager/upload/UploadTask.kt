package com.record.workmanager.upload

interface UploadTask {
    suspend fun upload(videoPath: String, content: String, placeId: Long, exhibitionName: String): Result<Unit>
}
