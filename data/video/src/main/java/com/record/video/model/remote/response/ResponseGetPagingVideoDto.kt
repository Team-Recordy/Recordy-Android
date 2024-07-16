package com.record.video.model.remote.response

import com.record.model.Page
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetPagingVideoDto(
    @SerialName("content")
    val content: List<ResponseGetVideoDto>,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("pageNumber")
    val pageNumber: Int,
)

fun ResponseGetPagingVideoDto.toCore() = Page(
    hasNext = hasNext,
    page = pageNumber,
    data = content.map {
        it.toDomain()
    },
)
