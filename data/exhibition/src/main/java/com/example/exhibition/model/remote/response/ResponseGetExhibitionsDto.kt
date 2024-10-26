package com.example.exhibition.model.remote.response

import com.record.exhibition.model.Exhibition
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetExhibitionsDto(
    @SerialName("id")
    val id: Int,
    @SerialName("isFree")
    val isFree: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String,
)

fun ResponseGetExhibitionsDto.toDomain() = Exhibition(
    id = this.id,
    isFree = this.isFree,
    name = this.name,
    startDate = this.startDate,
    endDate = this.endDate,
)
