package com.record.upload

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

data class UploadState(
    val showShouldShowRationaleDialog: Boolean = false,
) : UiState

sealed interface UploadSideEffect : SideEffect {
}
