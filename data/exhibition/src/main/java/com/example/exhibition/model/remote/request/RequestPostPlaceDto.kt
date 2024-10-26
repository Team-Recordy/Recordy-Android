package com.example.exhibition.model.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostPlaceDto(
    @SerialName("address")
    val address: String,
    @SerialName("id")
    val id: String,
    @SerialName("latitude")
    val latitude: Int,
    @SerialName("longitude")
    val longitude: Int,
    @SerialName("name")
    val name: String,
)
