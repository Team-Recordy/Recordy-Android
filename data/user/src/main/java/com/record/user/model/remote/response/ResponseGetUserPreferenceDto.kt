package com.record.user.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetUserPreferenceDto(
    @SerialName("preference")
    val preference: List<List<String>>,
)
