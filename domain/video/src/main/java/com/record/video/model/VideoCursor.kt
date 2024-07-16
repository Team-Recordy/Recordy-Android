package com.record.video.model

data class VideoCursor(
    val hasNext: Boolean,
    val nextCursor: Int,
    val videos: List<VideoData>,
)
