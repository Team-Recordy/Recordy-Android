package com.record.user.model.remote.response

import com.record.model.Cursor
import com.record.user.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetFollowerListDto(
    @SerialName("content")
    val content: List<Content>,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("nextCursor")
    val nextCursor: Int,
) {
    @Serializable
    data class Content(
        @SerialName("following")
        val following: Boolean,
        @SerialName("userInfo")
        val userInfo: UserInfo,
    )
}

fun ResponseGetFollowerListDto.toCore() = Cursor(
    hasNext = hasNext,
    nextCursor = nextCursor,
    data = content.map {
        User(
            id = it.userInfo.id,
            nickname = it.userInfo.nickname ?: "",
            isFollowing = it.following,
            profileImageUri = it.userInfo.profileImageUrl ?: "",
        )
    },
)
