package com.record.user.model

data class User(
    val id: Int,
    val nickname: String,
    val isFollowing: Boolean,
    val profileImageUri: String,
)
