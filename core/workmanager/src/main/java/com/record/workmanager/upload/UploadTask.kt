package com.record.workmanager.upload

interface UploadTask {
    suspend fun upload(videoPath: String, location: String, content: String, keywords: String): Result<Unit>
}
