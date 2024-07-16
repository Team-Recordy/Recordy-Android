package com.record.mypage.follow

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.model.UserData
import com.record.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor() : BaseViewModel<FollowState, FollowSideEffect>(
    FollowState(),
) {
    fun toggleFollow(isFollowingScreen: Boolean, user: UserData) {
        val updatedList = if (isFollowingScreen) {
            updateUserList(uiState.value.followingList, user)
        } else {
            updateUserList(uiState.value.followerList, user)
        }

        intent {
            if (isFollowingScreen) {
                copy(followingList = updatedList)
            } else {
                copy(followerList = updatedList)
            }
        }

        viewModelScope.launch {
            if (user.isFollowing) {
                Log.d("FollowViewModel", "unfollow")
            } else {
                Log.d("FollowViewModel", "follow")
            }
        }
    }

    private fun updateUserList(list: ImmutableList<UserData>, user: UserData): ImmutableList<UserData> {
        val newList = list.toMutableList()
        val index = newList.indexOfFirst { it.id == user.id }
        if (index >= 0) {
            val updatedUser = newList[index].copy(isFollowing = !newList[index].isFollowing)
            newList[index] = updatedUser
        }
        return newList.toImmutableList()
    }
}
