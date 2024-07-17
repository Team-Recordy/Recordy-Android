package com.record.profile

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.record.model.exception.ApiError
import com.record.profile.navigation.ProfileRoute
import com.record.ui.base.BaseViewModel
import com.record.user.model.Profile
import com.record.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProfileState, ProfileSideEffect>(ProfileState()) {
    private val userId = savedStateHandle.get<String>(ProfileRoute.PROFILE_ID_ARG_NAME)

    init {
        userId?.let { getProfile(it.toInt()) }
    }

    fun getProfile(userId: Int) {
        viewModelScope.launch {
            userRepository.getUserProfile(userId.toLong()).onSuccess { response ->
                intent {
                    copy(user = response)
                }
            }.onFailure {
                when (it) {
                    is ApiError -> {
                        Log.e("ProfileViewModel", it.message)
                    }
                }
            }
        }
    }

    fun toggleFollow(user: Profile) {
        viewModelScope.launch {
            val updatedUser = user.copy(isFollowing = !user.isFollowing)
            userRepository.postFollow(user.id.toLong()).onSuccess {
                intent {
                    copy(user = updatedUser)
                }
            }.onFailure {
                when (it) {
                    is ApiError -> {
                        Log.e("ProfileViewModel", it.message)
                    }
                }
            }
        }
    }
}
