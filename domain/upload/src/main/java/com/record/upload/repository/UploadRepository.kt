package com.record.upload.repository

import com.record.upload.model.UploadInfo
import com.record.upload.model.VideoInfo

interface UploadRepository {
    suspend fun getPresignedUrl(): Result<UploadInfo>
    suspend fun uploadRecord(videoInfo: VideoInfo): Result<Unit>
}
