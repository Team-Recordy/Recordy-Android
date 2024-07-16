package com.record.video.datasource

import com.record.video.api.BucketApi
import com.record.video.api.UploadApi
import com.record.video.model.remote.request.RequestPostVideoDto
import com.record.video.model.remote.response.ResponseGetPresignedUrlDto
import com.record.video.source.remote.RemoteUploadDataSource
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import javax.inject.Inject

class RemoteUploadDataSourceImpl @Inject constructor(
    private val uploadApi: UploadApi,
    private val bucketApi: BucketApi,
) : RemoteUploadDataSource {
    override suspend fun getUploadUrl(): ResponseGetPresignedUrlDto = uploadApi.getPresignedUploadUrl()

    override suspend fun uploadRecord(
        requestPostVideoDto: RequestPostVideoDto,
    ) = uploadApi.postRecord(requestPostVideoDto)

    override suspend fun uploadVideoToS3Bucket(url: String, multipart: MultipartBody.Part) = bucketApi.uploadVideoWithS3Bucket(url = url,multipart)


}
