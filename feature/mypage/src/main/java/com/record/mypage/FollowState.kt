package com.record.mypage

import com.record.model.UserData
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

data class FollowState(
    val userList: List<UserData> = listOf(
        UserData(id = 1, profileImage = "https://via.placeholder.com/150", name = "John Doe", isFollowing = false),
        UserData(id = 2, profileImage = "https://via.placeholder.com/150", name = "Jane Smith", isFollowing = false),
        UserData(id = 3, profileImage = "https://via.placeholder.com/150", name = "Alice Johnson", isFollowing = false),
        UserData(id = 4, profileImage = "https://via.placeholder.com/150", name = "Bob Brown", isFollowing = false),
    ),
) : UiState

sealed interface FollowSideEffect : SideEffect {
    data object Following : FollowSideEffect
    data object UnFollowing : FollowSideEffect
}
