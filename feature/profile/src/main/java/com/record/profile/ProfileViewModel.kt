package com.record.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.ui.base.BaseViewModel
import com.record.user.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseViewModel<ProfileState, ProfileSideEffect>(ProfileState()){
    fun toggleFollow(user: User) {
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
