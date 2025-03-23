package com.viskit.video.model.remote.request

import com.record.upload.model.VideoInfo
import com.record.video.model.remote.response.FileUrl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostVideoDto(
    @SerialName("content")
    val content: String,
    @SerialName("fileUrl")
    val fileUrl: FileUrl,
    @SerialName("placeId")
    val placeId: Long,
    @SerialName("exhibitionName")
    val exhibitionName: String,
)

fun VideoInfo.toData() = com.viskit.video.model.remote.request.RequestPostVideoDto(
    content = content,
    fileUrl = FileUrl(
        videoUrl = videoUrl,
        thumbnailUrl = previewUrl,
    ),
    placeId = placeId,
    exhibitionName = exhibitionName,
)
