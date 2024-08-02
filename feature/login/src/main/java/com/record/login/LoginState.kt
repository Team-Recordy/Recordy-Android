package com.record.login

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

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
