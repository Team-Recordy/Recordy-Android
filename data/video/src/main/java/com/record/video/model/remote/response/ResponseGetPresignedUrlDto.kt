package com.record.video.model.remote.response

import com.record.upload.model.UploadInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetPresignedUrlDto(
    @SerialName("thumbnailUrl")
    val thumbnailUrl: String,
    @SerialName("videoUrl")
    val videoUrl: String,
)

fun ResponseGetPresignedUrlDto.toCore() = UploadInfo(
    videoUrl = videoUrl,
    imageUrl = thumbnailUrl,
)
