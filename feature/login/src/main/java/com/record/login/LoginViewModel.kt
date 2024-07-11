package com.record.login

import androidx.lifecycle.viewModelScope
import com.record.ui.base.BaseViewModel
import com.recordy.oauth.model.KakaoToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel<LoginState, LoginSideEffect>(LoginState()) {

    fun startKakaoLogin() {
        postSideEffect(LoginSideEffect.StartLogin)
    }

    fun handleLoginSuccess(KakaoToken: KakaoToken) { // 로그인 성공시 작업
        intent {
            copy(autoLogin = true)
        }
        viewModelScope.launch {
            postSideEffect(LoginSideEffect.LoginSuccess(KakaoToken.accessToken))
        }
    }

    fun handleLoginError(KakaoToken: String) { // 로그인 실패시 작업
        intent {
            copy(autoLogin = false)
        }
        viewModelScope.launch {
            postSideEffect(LoginSideEffect.LoginError(KakaoToken))
        }
    }
}
