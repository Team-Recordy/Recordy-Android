package com.viskit.login

import com.viskit.ui.base.SideEffect
import com.viskit.ui.base.UiState

data class LoginState(
    var splash: Boolean = true,
    val isLoading: Boolean = false,
) : UiState

sealed interface LoginSideEffect : SideEffect {
    data object StartLogin : LoginSideEffect
    data object LoginSuccess : LoginSideEffect
    data object LoginToSignUp : LoginSideEffect
    data class LoginError(val errorMessage: String) : LoginSideEffect
}
