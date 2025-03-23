package com.viskit.user.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    @SerialName("id")
    val id: Long,
    @SerialName("nickname")
    val nickname: String?,
    @SerialName("profileImageUrl")
    val profileImageUrl: String?,
    @SerialName("isFollowing")
    val isFollowing: Boolean?,
)
