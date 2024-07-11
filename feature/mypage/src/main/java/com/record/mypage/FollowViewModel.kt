package com.record.mypage

import androidx.lifecycle.viewModelScope
import com.record.model.UserData
import com.record.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor() : BaseViewModel<FollowState, FollowSideEffect>(FollowState()) {

    fun toggleFollow(user: UserData) {
        intent {
            val updatedFollowingList = updateUserList(uiState.value.followingList, user)
            val updatedFollowerList = updateUserList(uiState.value.followerList, user)

            copy(
                followingList = updatedFollowingList,
                followerList = updatedFollowerList
            )
        }

        viewModelScope.launch {
            if (user.isFollowing) {
                postSideEffect(FollowSideEffect.UnFollowing)
            } else {
                postSideEffect(FollowSideEffect.Following)
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
        return newList.toPersistentList()
    }

    fun updateFollowerList(newList: List<UserData>) {
        intent {
            copy(followerList = newList.toPersistentList())
        }
    }

    fun updateFollowingList(newList: List<UserData>) {
        intent {
            copy(followingList = newList.toPersistentList())
        }
    }
}
