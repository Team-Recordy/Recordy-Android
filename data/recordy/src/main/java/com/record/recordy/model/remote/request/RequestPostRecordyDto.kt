package com.record.recordy.model.remote.request

import com.record.recordy.model.Recordy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostRecordyDto(
    @SerialName("a")
    val a: Int,
    @SerialName("b")
    val b: String,
)

fun Recordy.toData() = RequestPostRecordyDto(
    a = this.a,
    b = this.b,
)
