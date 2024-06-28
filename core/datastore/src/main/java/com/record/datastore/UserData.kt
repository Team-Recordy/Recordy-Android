package org.sopt.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val name: String? = null,
    val isAutoLogin: Boolean = false,
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
