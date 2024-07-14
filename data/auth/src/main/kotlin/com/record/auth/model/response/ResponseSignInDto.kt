package com.record.auth.model.response

import com.recordy.auth.model.AuthEntity
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
    fun toDomain() = AuthEntity(accessToken = accessToken, refreshToken = refreshToken, isSignedUp = isSignedUp )
}
