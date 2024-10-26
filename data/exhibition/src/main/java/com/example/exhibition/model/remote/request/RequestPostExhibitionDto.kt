package com.example.exhibition.model.remote.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostExhibitionDto(
    @SerialName("endDate")
    val endDate: String,
    @SerialName("isFree")
    val isFree: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("placeId")
    val placeId: Int,
    @SerialName("startDate")
    val startDate: String
)
