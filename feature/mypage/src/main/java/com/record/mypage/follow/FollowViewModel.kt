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
    fun getFollowingList() =
        viewModelScope.launch {
            if (uiState.value.isEnd) return@launch
            userRepository.getFollowingList(
                cursorId = uiState.value.followingCursor,
                size = 10,
            ).onSuccess { response ->
                val updatedList = uiState.value.followingList.toList()
                val addedList = response.data.filter { it.nickname != "" }

                intent {
                    copy(followingList = (updatedList + addedList).toImmutableList())
                }
                if (!response.hasNext) {
                    intent {
                        copy(isEnd = true)
                    }
                }
            }.onFailure {
                when (it) {
                    is ApiError -> {
                    }

                    else -> {
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
            userRepository.postFollow(user.id.toLong())
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

    fun navigateToProfile(id: Long) {
        postSideEffect(FollowSideEffect.NavigateToUserProfile(id))
    }
}
