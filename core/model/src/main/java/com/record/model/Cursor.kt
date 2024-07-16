package com.record.model

data class Cursor<T>(
    val hasNext: Boolean,
    val nextCursor: Int,
    val data: List<T>,
)
