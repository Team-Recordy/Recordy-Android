package com.recordy.auth.model

data class AuthEntity(
    val accessToken: String,
    val refreshToken: String,
    val isSignedUp: Boolean,
)
