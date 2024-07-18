package com.record.mypage.follow

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.user.model.User
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

data class FollowState(
    val followingList: ImmutableList<User> = emptyList<User>().toPersistentList(),
    val followerList: ImmutableList<User> = emptyList<User>().toPersistentList(),
    val followingCursor: Long = 0,
    val followerCursor: Long = 0,
    val isAll: Boolean = true,
) : UiState

sealed interface FollowSideEffect : SideEffect {
    data class NavigateToUserProfile(val id: Long) : FollowSideEffect
}
