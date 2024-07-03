package com.record.model

sealed class ValidateResult {
    data object OverlapError : ValidateResult()
    data object ValidationError : ValidateResult()
    data object Success : ValidateResult()
    data object Inputting : ValidateResult()
}
