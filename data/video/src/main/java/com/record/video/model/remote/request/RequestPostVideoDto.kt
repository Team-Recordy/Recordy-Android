package com.record.video.model.remote.request

import com.record.network.model.FileUrl
import com.record.upload.model.VideoInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostVideoDto(
    @SerialName("content")
    val content: String,
    @SerialName("fileUrl")
    val fileUrl: FileUrl,
    @SerialName("keywords")
    val keywords: List<String>,
    @SerialName("location")
    val location: String,
)

fun VideoInfo.toData() = RequestPostVideoDto(
    content = content,
    fileUrl = FileUrl(
        videoUrl = videoUrl,
        thumbnailUrl = previewUrl,
    ),
    keywords = keywords,
    location = location,
)
