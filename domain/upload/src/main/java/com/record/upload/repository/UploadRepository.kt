package com.record.upload.repository

import com.record.upload.model.UploadInfo
import com.record.upload.model.VideoInfo
import java.io.File

interface UploadRepository {
    suspend fun getPresignedUrl(): Result<UploadInfo>
    suspend fun uploadRecord(videoInfo: VideoInfo): Result<Unit>
    suspend fun uploadVideoToS3Bucket(url:String,file: File):Result<Unit>
}
