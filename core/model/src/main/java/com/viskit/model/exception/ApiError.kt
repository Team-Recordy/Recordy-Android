package com.viskit.model.exception

data class ApiError(
    override val message: String,
) : Exception()
