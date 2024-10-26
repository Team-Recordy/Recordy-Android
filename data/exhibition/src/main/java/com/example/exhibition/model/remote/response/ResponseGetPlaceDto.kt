package com.example.exhibition.model.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetPlaceDto(
    @SerialName("address")
    val address: String?,
    @SerialName("exhibitionSize")
    val exhibitionSize: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("location")
    val location: Location,
    @SerialName("name")
    val name: String,
    @SerialName("platformId")
    val platformId: String?,
    @SerialName("recordSize")
    val recordSize: Int
)
