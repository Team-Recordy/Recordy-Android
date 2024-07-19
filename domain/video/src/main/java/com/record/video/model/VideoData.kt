package com.record.video.model

data class VideoData(
    val bookmarkId: Long,
    val id: Long,
    val isBookmark: Boolean,
    val bookmarkCount: Int,
    val content: String,
    val videoUrl: String,
    val previewUrl: String,
    val location: String,
    val uploaderId: Long,
    val nickname: String,
    val isMine: Boolean,
)
