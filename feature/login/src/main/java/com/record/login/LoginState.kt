package com.record.login

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

data class LoginState(
    var token: String? = null,
) : UiState


sealed interface LoginSideEffect : SideEffect
