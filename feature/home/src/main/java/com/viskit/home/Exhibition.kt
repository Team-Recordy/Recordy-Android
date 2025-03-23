package com.viskit.home

import com.record.video.model.VideoData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class Exhibition(
    val location: String,
    val name: String,
    val exhibitionCount: Int,
    val userVideo: ImmutableList<VideoData> = emptyList<VideoData>().toImmutableList(),
)
