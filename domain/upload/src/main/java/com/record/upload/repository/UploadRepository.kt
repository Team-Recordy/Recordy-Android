package com.record.upload.repository

import com.record.upload.model.UploadInfo
import com.record.upload.model.VideoInfo

interface UploadRepository {
    fun getPresignedUrl(): Result<UploadInfo>
    fun uploadRecord(videoInfo: VideoInfo): Result<Unit>
}
