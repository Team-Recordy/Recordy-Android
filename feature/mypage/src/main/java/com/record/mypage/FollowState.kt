package com.record.mypage

import com.record.model.UserData
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FollowState(
    val followingList: ImmutableList<UserData> = persistentListOf(
        UserData(id = 1, profileImage = "https://via.placeholder.com/150", name = "John Doe", isFollowing = true),
        UserData(id = 2, profileImage = "https://via.placeholder.com/150", name = "Jane Smith", isFollowing = true),
        UserData(id = 3, profileImage = "https://via.placeholder.com/150", name = "Alice Johnson", isFollowing = true),
        UserData(id = 4, profileImage = "https://via.placeholder.com/150", name = "Bob Brown", isFollowing = true),
    ),
    val followerList: ImmutableList<UserData> = persistentListOf(
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
