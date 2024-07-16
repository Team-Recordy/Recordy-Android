package com.record.user.model.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePostFollowDto(
    @SerialName("isFollowing")
    val isFollowing: Boolean
)
