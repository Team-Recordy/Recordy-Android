package com.record.model

data class AuthEntity(
    val accessToken: String,
    val refreshToken: String,
    val isSignedUp: Boolean,
)
