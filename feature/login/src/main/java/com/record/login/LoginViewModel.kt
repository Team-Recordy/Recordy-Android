package com.record.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recordy.oauth.model.KakaoToken
import com.recordy.oauth.repository.OAuthInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoAuthManager: OAuthInteractor,
) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<KakaoToken>?>(null)
    val loginState: StateFlow<Result<KakaoToken>?> get() = _loginState

    fun loginWithKakao() {
        viewModelScope.launch {
            val result = kakaoAuthManager.loginByKakao()
            Timber.tag("로그인").d("loginWithKakao: " + result)
            _loginState.value = result
        }
    }
}
