package com.record.video.datasource

import android.content.Context
import android.util.Log
import com.record.common.util.getVideoFrameAt1Sec
import com.record.video.api.BucketApi
import com.record.video.api.UploadApi
import com.record.video.model.remote.request.RequestPostVideoDto
import com.record.video.model.remote.response.ResponseGetPresignedUrlDto
import com.record.video.source.remote.RemoteUploadDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File
import java.net.URL
import javax.inject.Inject

class RemoteUploadDataSourceImpl @Inject constructor(
    private val uploadApi: UploadApi,
    private val bucketApi: BucketApi,
    @ApplicationContext private val context: Context,
) : RemoteUploadDataSource {
    override suspend fun getUploadUrl(): ResponseGetPresignedUrlDto =
        uploadApi.getPresignedUploadUrl()

    override suspend fun uploadRecord(
        requestPostVideoDto: RequestPostVideoDto,
    ) = uploadApi.postRecord(requestPostVideoDto)

    override suspend fun uploadVideoToS3Bucket(url: String, file: File): String {
        val videoPath = file.absolutePath
        val mediaType = "application/octet-stream".toMediaTypeOrNull()
        val requestBody = RequestBody.create(mediaType, videoPath)
        val url = URL(bucketApi.uploadVideoWithS3Video(url, requestBody).raw().request.url.toString())
        Log.d("testUrl", "$url")
        return URL(url.protocol, url.host, url.port, url.path).toString()
    }

    override suspend fun uploadThumbnailToS3Bucket(url: String, file: File): String {
        val videoPath = file.absolutePath
        val outputImagePath = File(context.cacheDir, file.name)
        getVideoFrameAt1Sec(videoPath, outputImagePath.absolutePath)
        val mediaType = "application/octet-stream".toMediaTypeOrNull()
        val requestBody = RequestBody.create(mediaType, outputImagePath)
        val url = URL(bucketApi.uploadVideoWithS3Video(url, requestBody).raw().request.url.toString())
        Log.d("testUrl2", "$url")
        return URL(url.protocol, url.host, url.port, url.path).toString()
    }
}
