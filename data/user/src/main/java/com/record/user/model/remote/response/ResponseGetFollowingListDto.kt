package com.record.user.model.remote.response

import com.record.model.Cursor
import com.record.user.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetFollowingListDto(
    @SerialName("content")
    val content: List<UserInfo>,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("nextCursor")
    val nextCursor: Int,
)

fun ResponseGetFollowingListDto.toCore() = Cursor(
    hasNext = hasNext,
    nextCursor = nextCursor,
    data = content.map {
        User(
            id = it.id,
            nickname = it.nickname ?: "",
            isFollowing = true,
            profileImageUri = it.profileImageUrl ?: "",
        )
    },
)
