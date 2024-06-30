package com.record.model.exception

data class ApiError(
    override val message: String,
) : Exception()
