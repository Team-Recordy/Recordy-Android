package com.record.video.datasource

import com.record.video.api.UploadApi
import com.record.video.model.remote.request.RequestPostVideoDto
import com.record.video.model.remote.response.ResponseGetPresignedUrlDto
import com.record.video.source.remote.RemoteUploadDataSource
import javax.inject.Inject

class RemoteUploadDataSourceImpl @Inject constructor(
    private val uploadApi: UploadApi
): RemoteUploadDataSource {
    override fun getUploadUrl(): ResponseGetPresignedUrlDto = uploadApi.getPresignedUploadUrl()

    override fun uploadRecord(
        requestPostVideoDto: RequestPostVideoDto
    ) = uploadApi.postRecord(requestPostVideoDto)
}
