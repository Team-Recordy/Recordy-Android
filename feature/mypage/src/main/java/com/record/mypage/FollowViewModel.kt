package com.record.mypage

import androidx.lifecycle.viewModelScope
import com.record.model.UserData
import com.record.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor() : BaseViewModel<FollowState, FollowSideEffect>(FollowState()) {

    fun toggleFollow(user: UserData) {
        intent {
            val currentList = uiState.value.userList
            val newList = currentList.toMutableList()
            val index = newList.indexOfFirst { it.id == user.id }
            if (index >= 0) {
                val updatedUser = newList[index].copy(isFollowing = !newList[index].isFollowing)
                newList[index] = updatedUser
            }

            copy(userList = newList.toPersistentList())
        }

        viewModelScope.launch {
            if (user.isFollowing) {
                postSideEffect(FollowSideEffect.UnFollowing)
            } else {
                postSideEffect(FollowSideEffect.Following)
            }
        }
    }
}
