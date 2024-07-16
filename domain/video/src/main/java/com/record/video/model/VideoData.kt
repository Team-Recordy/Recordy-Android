package com.record.video.model

data class VideoData(
    val id: Int,
    val isBookmark: Boolean,
    val bookmarkCount: Int,
    val content: String,
    val videoUrl: String,
    val previewUrl: String,
    val location: String,
    val uploaderId: Int,
    val nickname: String,
    val isMine: Boolean
)
