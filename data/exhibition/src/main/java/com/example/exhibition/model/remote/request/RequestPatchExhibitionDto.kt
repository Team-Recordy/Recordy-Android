package com.example.exhibition.model.remote.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPatchExhibitionDto(
    @SerialName("endDate")
    val endDate: String,
    @SerialName("id")
    val id: Int,
    @SerialName("isFree")
    val isFree: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("startDate")
    val startDate: String
)
