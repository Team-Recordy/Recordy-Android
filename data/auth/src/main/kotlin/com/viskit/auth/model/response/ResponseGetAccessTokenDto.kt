package com.viskit.auth.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ResponseGetAccessTokenDto(
    @SerialName("accessToken")
    val accessToken: String
)
