package com.record.setting.profileedit

import com.record.model.ValidateResult
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

data class ProfileEditState(
    val username: String = "",
    val profileImgUrl: String? = null,
    val nicknameValidate: ValidateResult = ValidateResult.Inputting,
    val btnEnable: Boolean = false,
) : UiState

sealed interface ProfileEditSideEffect : SideEffect
