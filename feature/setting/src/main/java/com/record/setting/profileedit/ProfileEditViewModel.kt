package com.record.setting.profileedit

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.model.AlertInfo
import com.record.model.ValidateResult
import com.record.model.exception.ApiError
import com.record.ui.base.BaseViewModel
import com.record.upload.model.GalleryImage
import com.record.upload.repository.UploadRepository
import com.record.user.repository.UserRepository
import com.recordy.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val uploadRepository: UploadRepository,
) : BaseViewModel<ProfileEditState, ProfileEditSideEffect>(ProfileEditState()) {
    fun onLoadMore() = viewModelScope.launch(Dispatchers.IO) {
        val list = uiState.value.galleryList
        intent {
            copy(isItemLoading = true)
        }
        uploadRepository.getImagesFromGallery(uiState.value.galleryPage, 20, null).onSuccess {
            intent {
                copy(galleryList = (list + it).toImmutableList(), galleryPage = uiState.value.galleryPage + 1, isItemLoading = false)
            }
        }.onFailure {
            intent {
                copy(isItemLoading = false)
            }
        }
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

    fun popBackStack() {
        postSideEffect(ProfileEditSideEffect.BackToSetting)
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
        userRepository.updateUser(uiState.value.username, uiState.value.image?.filepath ?: "").onSuccess {
            postSideEffect(ProfileEditSideEffect.BackToSetting)
        }.onFailure {
            Log.e("실패", it.message.toString())
        }
    }

    fun setImage(galleryImage: GalleryImage) = intent {
        copy(image = galleryImage)
    }

    fun updateImgUrl(url: String?) = intent { copy(profileImgUrl = url) }

    fun updateName(name: String) = intent { copy(username = name) }

    fun showShouldShowRationaleDialog() = intent {
        copy(
            alertInfo = AlertInfo(
                showDialog = true,
                title = "필수 권한을 허용해주세요",
                subTitle = "영상 업로드를 위해 \n사진 라이브러리에 접근하도록 허용해 주세요.",
                negativeButtonLabel = "닫기",
                positiveButtonLabel = "지금 설정",
            ),
        )
    }

    fun hideUploadDialog() = intent {
        copy(
            alertInfo = AlertInfo(
                showDialog = false,
            ),
        )
    }

    fun showIsSelectedImageSheetOpen() = intent {
        copy(isSelectedImageSheetOpen = true)
    }

    fun hideIsSelectedVideoSheetOpen() = intent {
        copy(isSelectedImageSheetOpen = false)
    }

    private fun nickNameRegex(nickname: String): Boolean = NICKNAME_PATTERN.matches(nickname)

    companion object {
        val NICKNAME_PATTERN = Regex("^[가-힣ㅏ-ㅣ0-9_]+$")
    }
}
