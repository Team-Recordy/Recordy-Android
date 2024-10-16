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

    private fun showDialog() {
        when (uiState.value.dialog) {
            SettingDialog.NONE -> {
            }

            SettingDialog.DELETE -> {
                intent {
                    copy(
                        dialog = SettingDialog.DELETE,
                        dialogTitle = "정말 탈퇴하시겠어요?",
                        dialogSubTitle = "소중한 기록들이 모두 사라져요.",
                        negativeButtonLabel = "취소",
                        positiveButtonLabel = "탈퇴",
                    )
                }
            }

            SettingDialog.LOGOUT -> {
                intent {
                    copy(
                        dialog = SettingDialog.LOGOUT,
                        dialogTitle = "로그아웃 하시겠어요?",
                        dialogSubTitle = "버튼을 누르면 로그인 페이지로 이동합니다.",
                        negativeButtonLabel = "취소",
                        positiveButtonLabel = "로그아웃",
                    )
                }
            }
        }
    }

    fun logout() {
        intent {
            copy(dialog = SettingDialog.LOGOUT)
        }
        showDialog()
    }

    fun navigateProfileEdit() {
        viewModelScope.launch {
            postSideEffect(SettingSideEffect.ProfileEdit)
        }
    }

    fun delete() {
        intent {
            copy(dialog = SettingDialog.DELETE)
        }
        showDialog()
    }

    fun dismissDialog() {
        intent {
            copy(dialog = SettingDialog.NONE)
        }
    }

    fun eventDialog() {
        viewModelScope.launch {
            if (uiState.value.dialog == SettingDialog.DELETE) authRepository.delete()
            if (uiState.value.dialog == SettingDialog.LOGOUT) authRepository.logout()
            authRepository.saveLocalData(AuthEntity("", "", false))
            postSideEffect(SettingSideEffect.Restart)
        }
        dismissDialog()
    }
}
