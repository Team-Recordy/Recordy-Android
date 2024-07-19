package com.record.auth.model.response


import com.record.model.AuthEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSignInDto(
    @SerialName("userId")
    val userId: Long,
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("isSignedUp")
    val isSignedUp: Boolean,
)

fun ResponseSignInDto.toCoreModel() = AuthEntity(accessToken, refreshToken, isSignedUp, userid = userId)
