package com.record.video.model.remote.response


import com.record.network.model.RecordInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetBookmarkVideoDto(
    @SerialName("content")
    val content: List<ResponseGetVideoDto>,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("nextCursor")
    val nextCursor: Int
)
