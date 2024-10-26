package com.example.exhibition.model.remote.response

import com.record.exhibition.model.ResultType
import com.record.exhibition.model.SearchResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetExhibitionSearchDto(
    @SerialName("id")
    val id: Long,
    @SerialName("type")
    val type: String,
    @SerialName("address")
    val address: String,
    @SerialName("name")
    val name: String,
)

fun ResponseGetExhibitionSearchDto.toDomain() = SearchResult(
    id = this.id,
    type = when (this.type) {
        "PLACE" -> ResultType.PLACE
        "EXHIBITION" -> ResultType.EXHIBITION
        else -> ResultType.UNKNOWN
    },
    address = this.address,
    name = this.name,
)
