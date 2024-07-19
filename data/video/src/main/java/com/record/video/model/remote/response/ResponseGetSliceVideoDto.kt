package com.record.video.model.remote.response

import com.record.model.Cursor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetSliceVideoDto(
    @SerialName("content")
    val content: List<ResponseGetVideoDto>,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("nextCursor")
    val nextCursor: Int?,
)

fun ResponseGetSliceVideoDto.toCore() = Cursor(
    hasNext = hasNext,
    nextCursor = nextCursor,
    data = content.map { content ->
        content.toDomain()
    },
)
