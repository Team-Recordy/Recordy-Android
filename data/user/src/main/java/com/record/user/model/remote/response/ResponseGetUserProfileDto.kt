package com.record.user.model.remote.response

import com.record.user.model.Profile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetUserProfileDto(
    @SerialName("followerCount")
    val followerCount: Int,
    @SerialName("followingCount")
    val followingCount: Int,
    @SerialName("bookmarkCount")
    val bookmarkCount: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("isFollowing")
    val isFollowing: Boolean,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("recordCount")
    val recordCount: Int,
)

fun ResponseGetUserProfileDto.toDomain() = Profile(
    id = id,
    followerCount = followerCount,
    followingCount = followingCount,
    isFollowing = isFollowing,
    nickname = nickname,
    profileImageUrl = profileImageUrl,
    recordCount = recordCount,
    bookmarkCount = bookmarkCount,
)
