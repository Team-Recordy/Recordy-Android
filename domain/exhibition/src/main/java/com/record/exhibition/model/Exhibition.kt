package com.record.exhibition.model

data class Exhibition(
    val id: Int,
    val isFree: Boolean,
    val name: String,
    val startDate: String,
    val endDate: String,
)
