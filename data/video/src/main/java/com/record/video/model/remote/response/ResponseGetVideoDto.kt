package com.record.video.model.remote.response

import com.record.video.model.VideoData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetVideoDto(
    @SerialName("isBookmark")
    val isBookmark: Boolean,
    @SerialName("recordInfo")
    val recordInfo: RecordInfo,
)

fun ResponseGetVideoDto.toDomain() = VideoData(
    id = recordInfo.id,
    isBookmark = isBookmark,
    bookmarkCount = recordInfo.bookmarkCount,
    content = recordInfo.content,
    videoUrl = recordInfo.fileUrl.videoUrl,
    previewUrl = recordInfo.fileUrl.thumbnailUrl,
    location = recordInfo.location,
    uploaderId = recordInfo.uploaderId,
    nickname = recordInfo.uploaderNickname,
)
