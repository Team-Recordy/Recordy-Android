package com.viskit.video.source.remote

import com.viskit.common.util.ProgressListener
import com.viskit.video.model.remote.request.RequestPostVideoDto
import com.viskit.video.model.remote.response.ResponseGetPresignedUrlDto
import java.io.File

interface RemoteUploadDataSource {
    suspend fun getUploadUrl(): ResponseGetPresignedUrlDto
    suspend fun uploadProfileImgToS3Bucket(url: String, file: File): String
    suspend fun uploadRecord(requestPostVideoDto: com.viskit.video.model.remote.request.RequestPostVideoDto)
    suspend fun uploadVideoToS3Bucket(url: String, file: File, listener: ProgressListener): String
    suspend fun uploadThumbnailToS3Bucket(url: String, file: File): String
}
