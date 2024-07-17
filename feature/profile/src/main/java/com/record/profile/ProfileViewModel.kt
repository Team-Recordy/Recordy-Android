package com.record.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.model.UserData
import com.record.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseViewModel<ProfileState, ProfileSideEffect>(ProfileState()){
    fun toggleFollow(user: UserData) {
        viewModelScope.launch {
            if (user.isFollowing) {
                Log.d("FollowViewModel", "unfollow")
            } else {
                Log.d("FollowViewModel", "follow")
            }

            val updatedUser = user.copy(isFollowing = !user.isFollowing)

            intent {
                copy(user = updatedUser)
            }
        }
    }
}
