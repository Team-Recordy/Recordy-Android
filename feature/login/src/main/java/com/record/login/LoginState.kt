package com.record.login

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

data class LoginState(
    var autoLogin: Boolean = false,
) : UiState

sealed interface LoginSideEffect : SideEffect {
    data object StartLogin : LoginSideEffect
    data class LoginSuccess(val accessToken: String) : LoginSideEffect
    data class LoginError(val errorMessage: String) : LoginSideEffect
}
