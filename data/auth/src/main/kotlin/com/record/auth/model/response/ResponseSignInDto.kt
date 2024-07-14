package com.record.auth.model.response

import com.recordy.auth.model.AuthToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSignInDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("isSignedUp")
    val isSignedUp: Boolean,
) {
    fun toDomain() = AuthToken(accessToken = accessToken, refreshToken = refreshToken, isSignedUp = isSignedUp )
}
