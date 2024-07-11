package com.record.model

data class VideoData(
    val id: String,
    val videoUri: String,
    val previewUri: String,
    val location: String,
    val userName: String,
    val content: String,
    val bookmarkCount: Int,
    val isBookmark: Boolean,
)
