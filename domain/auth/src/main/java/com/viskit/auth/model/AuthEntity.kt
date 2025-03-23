package com.viskit.auth.model

data class AuthEntity(
    val accessToken: String,
    val refreshToken: String,
    val isSignedUp: Boolean,
)
