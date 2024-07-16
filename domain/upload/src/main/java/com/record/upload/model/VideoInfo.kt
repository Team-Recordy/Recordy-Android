package com.record.upload.model

data class VideoInfo(
    val location: String,
    val content: String,
    val keywords: List<String>,
    val videoUrl: String,
    val previewUrl: String,
)
