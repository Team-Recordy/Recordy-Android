package com.viskit.video.model.local

import com.record.upload.model.GalleryImage

data class LocalImageInfo(
    val id: Long,
    val filepath: String,
    val uri: String,
    val name: String,
    val date: String,
    val size: Int,
    val duration: Long,
)

fun LocalImageInfo.toDomain() = GalleryImage(
    id = id,
    filepath = filepath,
    uri = uri,
    name = name,
    date = date,
    size = size,
    duration = duration,
)
