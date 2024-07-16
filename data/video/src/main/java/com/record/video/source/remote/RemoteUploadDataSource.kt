package com.record.video.source.remote

import com.record.video.model.remote.request.RequestPostVideoDto
import com.record.video.model.remote.response.ResponseGetPresignedUrlDto

interface RemoteUploadDataSource {
    fun getUploadUrl(): ResponseGetPresignedUrlDto
    fun uploadRecord(requestPostVideoDto: RequestPostVideoDto)
}
