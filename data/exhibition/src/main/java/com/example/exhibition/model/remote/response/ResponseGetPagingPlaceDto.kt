package com.example.exhibition.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetPagingPlaceDto(
    @SerialName("content")
    val content: List<ResponseGetPlaceDto>,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("pageNumber")
    val pageNumber: Int,
)
