package com.viskit.user.model.remote.response

import com.viskit.model.Cursor
import com.viskit.user.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetFollowerListDto(
    @SerialName("content")
    val content: List<UserInfo>,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("nextCursor")
    val nextCursor: Int,
)

fun ResponseGetFollowerListDto.toCore() = Cursor(
    hasNext = hasNext,
    nextCursor = nextCursor,
    data = content.map {
        User(
            id = it.id,
            nickname = it.nickname ?: "",
            isFollowing = it.isFollowing ?: true,
            profileImageUri = it.profileImageUrl ?: "",
        )
    },
)
