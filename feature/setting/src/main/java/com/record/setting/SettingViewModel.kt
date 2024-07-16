package com.record.setting

import androidx.lifecycle.viewModelScope
import com.record.model.AuthEntity
import com.record.ui.base.BaseViewModel
import com.recordy.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel<SettingState, SettingSideEffect>(SettingState()) {

    fun logout() {
        intent {
            copy(dialog = SettingDialog.LOGOUT)
        }
    }

    fun delete() {
        intent {
            copy(dialog = SettingDialog.DELETE)
        }
    }

    fun dismissDialog() {
        intent {
            copy(dialog = SettingDialog.NONE)
        }
    }

    fun logoutInDialog() {
        viewModelScope.launch {
            authRepository.logout().onSuccess {
                authRepository.saveLocalData(AuthEntity("", "", false))
                postSideEffect(SettingSideEffect.Restart)
            }.onFailure {
                // logout fail 재시작 로직
            }
        }
    }

    fun deleteInDialog() {
        viewModelScope.launch {
            authRepository.delete().onSuccess {
                authRepository.saveLocalData(AuthEntity("", "", false))
                postSideEffect(SettingSideEffect.Restart)
            }.onFailure {
            }
        }
    }
}
