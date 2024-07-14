package com.record.model

data class UserData(
    val id: Int,
    val profileImage: String? = null,
    val profileImageResId: Int? = null,
    val name: String,
    val isFollowing: Boolean,
)
