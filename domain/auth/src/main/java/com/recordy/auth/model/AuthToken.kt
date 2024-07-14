package com.recordy.auth.model

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
    val isSignedUp: Boolean,
)
