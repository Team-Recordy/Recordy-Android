package com.record.setting.profileedit

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.model.ValidateResult
import com.record.model.exception.ApiError
import com.record.ui.base.BaseViewModel
import com.record.user.repository.UserRepository
import com.recordy.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) : BaseViewModel<ProfileEditState, ProfileEditSideEffect>(ProfileEditState()) {
    init {
        getProfile()
    }

    private fun getProfile() = viewModelScope.launch {
        userRepository.getUserId().onSuccess {
            userRepository.getUserProfile(it).onSuccess { response ->
                intent {
                    copy(
                        username = response.nickname,
                        profileImgUrl = response.profileImageUrl,
                    )
                }
            }.onFailure {
                when (it) {
                    is ApiError -> {
                        Log.e("ProfileEditViewModel", it.message)
                    }
                }
            }
        }
    }

    fun checkValidateNickName() {
        viewModelScope.launch {
            authRepository.checkNickname(uiState.value.username).onSuccess {
                if (uiState.value.username.isBlank()) {
                    intent { copy(nicknameValidate = ValidateResult.Inputting, btnEnable = false) }
                } else if (!nickNameRegex(uiState.value.username) && !uiState.value.username.contains(" ")) {
                    intent { copy(nicknameValidate = ValidateResult.ValidationError, btnEnable = false) }
                } else {
                    intent { copy(nicknameValidate = ValidateResult.Success, btnEnable = true) }
                }
            }.onFailure {
                intent { copy(nicknameValidate = ValidateResult.OverlapError, btnEnable = false) }
            }
        }
    }

    fun updateUserProfile() = viewModelScope.launch {
        userRepository.updateUser(uiState.value.username, uiState.value.profileImgUrl ?: "")
    }

    fun updateImgUrl(url: String?) = intent { copy(profileImgUrl = url) }

    fun updateName(name: String) = intent { copy(username = name) }

    private fun nickNameRegex(nickname: String): Boolean = NICKNAME_PATTERN.matches(nickname)

    companion object {
        val NICKNAME_PATTERN = Regex("^[가-힣ㅏ-ㅣ0-9_]+$")
    }
}
