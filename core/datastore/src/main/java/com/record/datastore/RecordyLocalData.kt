package org.sopt.datastore

import kotlinx.serialization.Serializable

@Serializable
data class RecordyLocalData(
    val a: Int = 0,
    val b: String = ""
)
