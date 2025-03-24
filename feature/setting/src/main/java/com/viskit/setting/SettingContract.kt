package com.viskit.setting

import com.viskit.ui.base.SideEffect
import com.viskit.ui.base.UiState

data class SettingState(
    val dialog: SettingDialog = SettingDialog.NONE,
    val dialogTitle: String = "",
    val dialogSubTitle: String = "",
    val negativeButtonLabel: String = "",
    val positiveButtonLabel: String = "",
) : UiState

sealed interface SettingSideEffect : SideEffect {
    data object Restart : SettingSideEffect
    data object ProfileEdit : SettingSideEffect
}
