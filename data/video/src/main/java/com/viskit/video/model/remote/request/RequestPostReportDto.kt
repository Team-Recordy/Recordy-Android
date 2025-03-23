package com.viskit.video.model.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostReportDto(
    @SerialName("recordId")
    val recordId: Long,
    @SerialName("reason")
    val reason: String,
    @SerialName("content")
    val content: String,
)
