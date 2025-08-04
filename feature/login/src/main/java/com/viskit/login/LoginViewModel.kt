package com.viskit.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.amplitude.android.Amplitude
import com.amplitude.android.events.Identify
import com.viskit.auth.repository.AuthRepository
import com.viskit.model.AuthEntity
import com.viskit.model.exception.ApiError
import com.viskit.ui.base.BaseViewModel
import com.viskit.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val amplitude: Amplitude,
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
                updateAmplitudeUserId(it.userid)
                if (it.accessToken.isNotBlank() && it.isSignedUp) postSideEffect(LoginSideEffect.LoginSuccess)
            }
        }
    }

    fun signIn(socialToken: String) {
        viewModelScope.launch {
            intent {
                copy(isLoading = true)
            }
            authRepository.getLocalData().onSuccess {
                if (it.isSignedUp) postSideEffect(LoginSideEffect.LoginSuccess)
            }
            authRepository.saveLocalData(AuthEntity(socialToken, "", false))
            authRepository.signIn()
                .onSuccess {
                    userRepository.saveUserId(it.userid)
                    updateAmplitudeUserId(it.userid)
                    authRepository.saveLocalData(AuthEntity(it.accessToken, it.refreshToken, it.isSignedUp))
                    if (it.isSignedUp) {
                        postSideEffect(LoginSideEffect.LoginSuccess)
                    } else {
                        postSideEffect(LoginSideEffect.LoginToSignUp)
                    }
                    intent {
                        copy(isLoading = false)
                    }
                }.onFailure {
                    when (it) {
                        is ApiError -> Log.e("실패", it.message)
                        else -> Log.e("실패", it.message.toString())
                    }
                    postSideEffect(LoginSideEffect.LoginError(errorMessage = it.message.toString()))
                    intent {
                        copy(isLoading = false)
                    }
                }
        }
    }

    private fun updateAmplitudeUserId(userId: Long) {
        if (userId == 0L) return
        amplitude.setUserId(String.format("%05d", userId))
        val identify = Identify()
        identify.set("user_name", String.format("%05d", userId))
        amplitude.identify(identify)
    }
}
