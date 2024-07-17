package com.record.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.model.AuthEntity
import com.record.model.exception.ApiError
import com.record.ui.base.BaseViewModel
import com.record.user.repository.UserRepository
import com.recordy.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : BaseViewModel<LoginState, LoginSideEffect>(LoginState()) {

    fun splashScreen() {
        intent { copy(splash = false) }
    }
    fun startKakaoLogin() {
        postSideEffect(LoginSideEffect.StartLogin)
    }

    fun autoLoginCheck() {
        viewModelScope.launch {
            authRepository.getLocalData().onSuccess {
                if (it.accessToken.isNotBlank() && it.isSignedUp) postSideEffect(LoginSideEffect.LoginSuccess)
            }
        }
    }

    fun signIn(socialToken: String) {
        viewModelScope.launch {
            authRepository.getLocalData().onSuccess {
                if (it.isSignedUp) postSideEffect(LoginSideEffect.LoginSuccess)
            }
            authRepository.saveLocalData(AuthEntity(socialToken, "", false))
            authRepository.signIn()
                .onSuccess {
                    userRepository.saveUserId(it.userid)
                    authRepository.saveLocalData(AuthEntity(it.accessToken, it.refreshToken, it.isSignedUp))
                    if (it.isSignedUp) {
                        postSideEffect(LoginSideEffect.LoginSuccess)
                    } else {
                        postSideEffect(LoginSideEffect.LoginToSignUp)
                    }
                }.onFailure {
                    when (it) {
                        is ApiError -> Log.e("실패", it.message)
                        else -> Log.e("실패", it.message.toString())
                    }
                    postSideEffect(LoginSideEffect.LoginError(errorMessage = it.message.toString()))
                }
        }
    }
}
