package com.record.workmanager.upload.impl

import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.record.workmanager.upload.UploadEnqueuer
import com.record.workmanager.upload.UploadWorker
import javax.inject.Inject

class UploadEnqueuerImpl @Inject constructor(
    private val workManager: WorkManager,
) : UploadEnqueuer {
    override fun enqueueUploadWork(videoPath: String, location: String, keywords: String, content: String) {
        val data = Data.Builder()
            .putString(UploadWorker.KEY_VIDEO_PATH, videoPath)
            .putString(UploadWorker.KEY_LOCATION, location)
            .putString(UploadWorker.KEY_KEYWORDS, keywords)
            .putString(UploadWorker.KEY_CONTENT, content)
            .build()
        val request = OneTimeWorkRequestBuilder<UploadWorker>()
            .addTag("upload")
            .setInputData(data)
            .build()
        workManager.enqueueUniqueWork(
            UploadWorker.UNIQUE_UPLOAD_WORK,
            ExistingWorkPolicy.KEEP,
            request,
        )
    }
}
