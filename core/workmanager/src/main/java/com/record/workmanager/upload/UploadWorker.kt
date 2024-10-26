package com.record.workmanager.upload

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val uploadTask: UploadTask,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val videoPath = inputData.getString(KEY_VIDEO_PATH) ?: return Result.failure()
        val placeId = inputData.getString(KEY_PLACE) ?: return Result.failure()
        val content = inputData.getString(KEY_CONTENT) ?: return Result.failure()
        val result = uploadTask.upload(videoPath, content, placeId = placeId.toLong())

        return result.fold(
            onSuccess = {
                Result.success()
            },
            onFailure = {
                Result.failure()
            },
        )
    }

    companion object {
        const val KEY_VIDEO_PATH = "videoPath"
        const val KEY_PLACE = "placeId"
        const val KEY_CONTENT = "content"
        const val UNIQUE_UPLOAD_WORK = "upload-work"
    }
}
