package com.record.user.model.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestUpdateProfileDto(
    @SerialName("nickname")
    var nickname: String,
    @SerialName("profileImageUrl")
    var profileImageUrl: String,
)
