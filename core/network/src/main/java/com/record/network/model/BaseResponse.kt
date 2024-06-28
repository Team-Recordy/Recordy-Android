package com.record.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("code")
    val code: Int,
    @SerialName("data")
    val data: T? = null,
    @SerialName("message")
    val message: String,
)