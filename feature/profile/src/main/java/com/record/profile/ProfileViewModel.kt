package com.record.profile

import androidx.lifecycle.viewModelScope
import com.record.model.UserData
import com.record.ui.base.BaseViewModel
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseViewModel<ProfileState, ProfileSideEffect>(ProfileState()) {

    fun toggleFollow(user: UserData) {
        viewModelScope.launch {
            if (user.isFollowing) {
                // Unfollow
            } else {
                // Follow
            }
        }
    }
}

sealed interface ProfileSideEffect : SideEffect
