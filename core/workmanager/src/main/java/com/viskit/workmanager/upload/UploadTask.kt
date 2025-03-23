package com.viskit.workmanager.upload

interface UploadTask {
    suspend fun upload(videoPath: String, content: String, placeId: Long, exhibitionName: String): Result<Unit>
}
