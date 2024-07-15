package com.record.login

import androidx.lifecycle.viewModelScope
import com.record.model.AuthEntity
import com.record.ui.base.BaseViewModel
import com.recordy.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel<LoginState, LoginSideEffect>(LoginState()) {

    fun startKakaoLogin() {
        postSideEffect(LoginSideEffect.StartLogin)
    }

    fun signIn(socialToken: String) {
        viewModelScope.launch {
            authRepository.getLocalData().onSuccess {
                if (it.isSignedUp) postSideEffect(LoginSideEffect.LoginSuccess)
            }
            authRepository.signIn(socialToken)
                .onSuccess {
                    authRepository.saveLocalData(AuthEntity(it.accessToken, it.refreshToken, it.isSignedUp))
                    postSideEffect(LoginSideEffect.LoginToSignUp)
                }.onFailure {
                    postSideEffect(LoginSideEffect.LoginError(errorMessage = it.message.toString()))
                }
        }
    }
}
