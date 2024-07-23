package com.record.upload.model

data class GalleryVideo(
    val id: Long,
    val filepath: String,
    val uri: String,
    val name: String,
    val date: String,
    val size: Int,
    val duration: Long,
)
