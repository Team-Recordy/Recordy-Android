package com.viskit.exhibition.model

data class Exhibition(
    val id: Int,
    val isFree: Boolean,
    val name: String,
    val startDate: String,
    val endDate: String,
)

data class PlaceUsingMap(
    val platformPlaceId: String = "",
    val address: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val name: String = "",
)
