package com.record.video.model.remote.response

import com.record.video.model.VideoData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetVideoDto(
    @SerialName("bookmarkCount")
    val bookmarkCount: Int,
    @SerialName("content")
    val content: String,
    @SerialName("fileUrl")
    val fileUrl: FileUrl,
    @SerialName("id")
    val id: Long,
    @SerialName("uploaderId")
    val uploaderId: Long,
    @SerialName("uploaderNickname")
    val uploaderNickname: String,
    @SerialName("isMine")
    val isMine: Boolean,
    @SerialName("isBookmarked")
    val isBookmarked: Boolean,
)

fun ResponseGetVideoDto.toDomain() = VideoData(
    bookmarkId = 0,
    id = id,
    isBookmark = isBookmarked,
    bookmarkCount = bookmarkCount,
    content = content,
    videoUrl = fileUrl.videoUrl,
    previewUrl = fileUrl.thumbnailUrl,
    location = "",
    uploaderId = uploaderId,
    nickname = uploaderNickname,
    isMine = isMine,
)
