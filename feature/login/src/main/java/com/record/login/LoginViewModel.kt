package com.record.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.ui.base.BaseViewModel
import com.recordy.auth.model.AuthAgreementEntity
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

    fun signUp(socialToken: String) {
        viewModelScope.launch {
            authRepository.signUp(
                "Bearer $socialToken",
                AuthAgreementEntity(
                    "1_2",
                    AuthAgreementEntity.TermsAgreement(
                        useTerm = true,
                        personalInfoTerm = true,
                        ageTerm = true,
                    ),
                ),
            ).onSuccess {
                    AuthToken ->
                AuthToken
            }.onFailure {
                Log.d("login - signup", "signUp: ${it.message}")
            }
        }
    }

    fun signIn(socialToken: String) {
        viewModelScope.launch {
            authRepository.signIn(socialToken).onSuccess {
                postSideEffect(LoginSideEffect.LoginSuccess(socialToken))
            }.onFailure {
                Log.d("login - signIn", "signp: ${it.message}")
                postSideEffect(LoginSideEffect.LoginError(errorMessage = it.message.toString()))
            }
        }
    }
}
