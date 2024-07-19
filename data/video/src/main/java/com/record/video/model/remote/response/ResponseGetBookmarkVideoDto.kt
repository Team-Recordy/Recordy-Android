package com.record.video.model.remote.response

import com.record.video.model.VideoData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetBookmarkVideoDto(
    @SerialName("bookmarkId")
    val bookmarkId: Long,
    @SerialName("isBookmark")
    val isBookmark: Boolean,
    @SerialName("recordInfo")
    val recordInfo: RecordInfo,
)

fun ResponseGetBookmarkVideoDto.toDomain() = VideoData(
    id = recordInfo.id,
    bookmarkId = bookmarkId,
    isBookmark = isBookmark,
    bookmarkCount = recordInfo.bookmarkCount,
    content = recordInfo.content,
    videoUrl = recordInfo.fileUrl.videoUrl,
    previewUrl = recordInfo.fileUrl.thumbnailUrl,
    location = recordInfo.location,
    uploaderId = recordInfo.uploaderId,
    nickname = recordInfo.uploaderNickname,
    isMine = recordInfo.isMine,
)
