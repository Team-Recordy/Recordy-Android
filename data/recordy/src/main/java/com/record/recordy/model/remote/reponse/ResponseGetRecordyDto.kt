package com.record.recordy.model.remote.reponse

import com.record.recordy.model.Recordy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetRecordyDto(
    @SerialName("a")
    val a: Int,
    @SerialName("b")
    val b: String
)

fun ResponseGetRecordyDto.toDomain() = Recordy(
    a = this.a,
    b = this.b
)
