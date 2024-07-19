package com.record.setting

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

data class SettingState(
    val dialog: SettingDialog = SettingDialog.NONE,
    val dialogTitle: String = "",
    val dialogSubTitle: String = "",
    val negativeButtonLabel: String = "",
    val positiveButtonLabel: String = "",
) : UiState

sealed interface SettingSideEffect : SideEffect {
    data object Restart : SettingSideEffect
}
