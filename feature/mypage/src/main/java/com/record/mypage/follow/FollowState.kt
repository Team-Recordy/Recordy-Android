package com.record.mypage.follow

import com.record.model.UserData
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

data class FollowState(
//    val followingList: ImmutableList<UserData> = persistentListOf(
//        UserData(id = 1, profileImage = "https://picsum.photos/id/200/60", name = "John Doe", isFollowing = true),
//        UserData(id = 2, profileImage = "https://picsum.photos/id/200/60", name = "Jane Smith", isFollowing = true),
//    ),
    val followingList: ImmutableList<UserData> = emptyList<UserData>().toPersistentList(),
//    val followerList: ImmutableList<UserData> = persistentListOf(
//        UserData(id = 1, profileImage = "https://picsum.photos/id/200/60", name = "John Doe", isFollowing = false),
//        UserData(id = 2, profileImage = "https://picsum.photos/id/200/60", name = "Jane Smith", isFollowing = false),
//    ),
    val followerList: ImmutableList<UserData> = emptyList<UserData>().toPersistentList(),
) : UiState

sealed interface FollowSideEffect : SideEffect {
    data object Following : FollowSideEffect
    data object UnFollowing : FollowSideEffect
}
