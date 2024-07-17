package com.record.mypage.follow

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.model.exception.ApiError
import com.record.ui.base.BaseViewModel
import com.record.user.model.User
import com.record.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel<FollowState, FollowSideEffect>(
    FollowState(),
) {
    init {
        getFollowingList()
        getFollowerList()
    }

    fun getFollowingList() =
        viewModelScope.launch {
            userRepository.getFollowingList(
                cursorId = uiState.value.followingCursor,
                size = 10,
            ).onSuccess { response ->
                val updatedList = uiState.value.followingList.toList()

                intent {
                    copy(followingList = (updatedList + response.data).toImmutableList())
                }
            }.onFailure {
                when (it) {
                    is ApiError -> {
                        Log.e("FollowViewModel", it.message)
                    }
                }
            }
        }

    fun loadMoreFollowing() {
        if (uiState.value.isAll) {
            intent {
                copy(followingCursor = uiState.value.followingCursor + 1)
            }
            getFollowingList()
        }
    }

    fun loadMoreFollower() {
        if (uiState.value.isAll) {
            intent {
                copy(followerCursor = uiState.value.followerCursor + 1)
            }
            getFollowerList()
        }
    }

    fun getFollowerList() {
        viewModelScope.launch {
            userRepository.getFollowerList(
                cursorId = uiState.value.followerCursor,
                size = 10,
            ).onSuccess { response ->
                val updatedList = uiState.value.followerList.toList()

                intent {
                    copy(followerList = (updatedList + response.data).toImmutableList())
                }
            }.onFailure {
                when (it) {
                    is ApiError -> {
                        Log.e("FollowViewModel", it.message)
                    }
                }
            }
        }
    }

    fun toggleFollow(isFollowingScreen: Boolean, user: User) {
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

    private fun updateUserList(list: ImmutableList<User>, user: User): ImmutableList<User> {
        val newList = list.toMutableList()
        val index = newList.indexOfFirst { it.id == user.id }
        if (index >= 0) {
            val updatedUser = newList[index].copy(isFollowing = !newList[index].isFollowing)
            newList[index] = updatedUser
        }
        return newList.toImmutableList()
    }

    fun navigateToProfile(id: Int) {
        postSideEffect(FollowSideEffect.NavigateToUserProfile(id))
    }
}
