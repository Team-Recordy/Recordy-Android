package com.record.setting.profileedit

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

data class ProfileEditState(
    val id: Int = 0,
    val username: String = "",
    val profileImgUrl: String = "",
) : UiState

sealed interface ProfileEditSideEffect : SideEffect
