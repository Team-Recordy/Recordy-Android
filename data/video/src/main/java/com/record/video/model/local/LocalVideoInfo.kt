package com.record.video.model.local

import com.record.upload.model.GallercyVideo

data class LocalVideoInfo(
    val id: Long,
    val filepath: String,
    val uri: String,
    val name: String,
    val date: String,
    val size: Int,
    val duration: Long,
)

fun LocalVideoInfo.toDomain() = GallercyVideo(
    id = id,
    filepath = filepath,
    uri = uri,
    name = name,
    date = date,
    size = size,
    duration = duration,
)
