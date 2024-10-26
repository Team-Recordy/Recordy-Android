package com.record.user.model

data class Profile(
    val id: Int,
    val followerCount: Int,
    val followingCount: Int,
    val isFollowing: Boolean,
    val nickname: String,
    val profileImageUrl: String,
    val recordCount: Int,
)
