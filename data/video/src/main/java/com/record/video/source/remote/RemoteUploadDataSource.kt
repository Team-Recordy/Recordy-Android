package com.record.video.source.remote

import com.record.common.util.ProgressListener
import com.record.video.model.remote.request.RequestPostVideoDto
import com.record.video.model.remote.response.ResponseGetPresignedUrlDto
import java.io.File

interface RemoteUploadDataSource {
    suspend fun getUploadUrl(): ResponseGetPresignedUrlDto
    suspend fun uploadRecord(requestPostVideoDto: RequestPostVideoDto)
    suspend fun uploadVideoToS3Bucket(url: String, file: File, listener: ProgressListener): String
    suspend fun uploadThumbnailToS3Bucket(url: String, file: File): String
}
