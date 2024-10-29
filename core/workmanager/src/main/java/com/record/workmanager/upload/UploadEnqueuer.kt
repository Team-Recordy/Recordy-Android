package com.record.workmanager.upload

interface UploadEnqueuer {
    fun enqueueUploadWork(videoPath: String, content: String, placeId: Long, exhibitionName: String)
}
