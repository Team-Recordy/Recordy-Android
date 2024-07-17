package com.record.datastore.user

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val userid: Long = 0,
)
