package com.record.setting.profileedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.record.ui.base.BaseViewModel
import com.record.user.repository.UserRepository
import com.record.video.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val videoRepository: VideoRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProfileEditState, ProfileEditSideEffect>(ProfileEditState()) {
    init {
        getProfile()
    }

    fun getProfile() = viewModelScope.launch {
        userRepository.getUserId().onSuccess {
            userRepository.getUserProfile(it)
        }
//        userRepository.getUserProfile(uiState.value.id).onSuccess { response ->
//            intent {
//
//                copy(
//                    id = response.id.toLong(),
//                    followerCount = response.followerCount,
//                    followingCount = response.followingCount,
//                    isFollowing = response.isFollowing,
//                    nickname = response.nickname,
//                    profileImageUrl = response.profileImageUrl,
//                    recordCount = response.recordCount,
//                )
//            }
//        }.onFailure {
//            when (it) {
//                is ApiError -> {
//                    Log.e("ProfileEditViewModel", it.message)
//                }
//            }
//        }
    }
    fun updateUserProfile(nickname: String, imgUrl: String) = viewModelScope.launch {
        userRepository.updateUser(nickname, imgUrl)
    }
    fun setUserProfileImg() {
    }
}
