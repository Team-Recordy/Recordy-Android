package com.record.model

data class AlertInfo(
    val showDialog: Boolean = false,
    val title: String = "",
    val subTitle: String = "",
    val negativeButtonLabel: String = "",
    val positiveButtonLabel: String = "",
)
