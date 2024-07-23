package com.record.workmanager.upload

interface UploadEnqueuer {
    fun enqueueUploadWork(videoPath: String, location: String, keywords: String, content: String)
}
