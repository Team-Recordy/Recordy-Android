package com.record.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePostAuthRefreshDto(
    @SerialName("accessToken")
    val accessToken: String,
)
