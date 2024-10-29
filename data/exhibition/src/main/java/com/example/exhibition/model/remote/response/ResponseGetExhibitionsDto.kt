package com.example.exhibition.model.remote.response

import com.record.common.util.decodeHtmlEntities
import com.record.exhibition.model.Exhibition
import com.record.exhibition.model.PlaceUsingMap
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

@Serializable
data class ResponseGetPlaceUsingMapDto(
    @SerialName("platformPlaceId")
    val platformPlaceId: String,
    @SerialName("address")
    val address: String,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("name")
    val name: String,
)

fun ResponseGetPlaceUsingMapDto.toDomain() = PlaceUsingMap(
    platformPlaceId = platformPlaceId,
    address = address,
    name = name,
    longitude = longitude,
    latitude = latitude,
)

fun ResponseGetExhibitionsDto.toDomain() = Exhibition(
    id = this.id,
    isFree = this.isFree,
    name = decodeHtmlEntities(this.name),
    startDate = this.startDate,
    endDate = this.endDate,
)
