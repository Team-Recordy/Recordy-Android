package com.viskit.upload.model

data class GalleryImage(
    val id: Long,
    val filepath: String,
    val uri: String,
    val name: String,
    val date: String,
    val size: Int,
    val duration: Long,
)
