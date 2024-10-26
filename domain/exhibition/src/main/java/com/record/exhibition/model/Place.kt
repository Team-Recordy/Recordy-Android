package com.record.exhibition.model

import com.record.model.VideoData

data class Place(
    val placeId: Int,
    val address: String,
    val name: String,
    val exhibitionCount: Int,
    val recordCount: Int,
    val exhibitionRecord: List<VideoData>?
)
