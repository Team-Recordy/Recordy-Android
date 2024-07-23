package com.record.video.repository

import com.record.upload.model.RecordInfo
import com.record.upload.repository.UploadRepository
import com.record.workmanager.upload.UploadEnqueuer
import javax.inject.Inject

class UploadRepositoryImpl @Inject constructor(
    private val uploadEnqueuer: UploadEnqueuer,
) : UploadRepository {
    override suspend fun upload(recordInfo: RecordInfo) {
        uploadEnqueuer.enqueueUploadWork(recordInfo.videoPath, recordInfo.location, recordInfo.keywords, recordInfo.content)
    }
}
