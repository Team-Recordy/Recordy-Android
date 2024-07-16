package com.record.setting

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState


data class SettingState(
    val dialog: SettingDialog =SettingDialog.NONE,
) : UiState

sealed interface SettingSideEffect : SideEffect {
    data object Restart:SettingSideEffect
}
