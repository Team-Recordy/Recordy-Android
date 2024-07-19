package com.record.user.model

data class User(
    val id: Long,
    val nickname: String,
    val isFollowing: Boolean,
    val profileImageUri: String,
)
