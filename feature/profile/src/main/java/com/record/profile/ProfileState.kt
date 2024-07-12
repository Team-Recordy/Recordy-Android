package com.record.profile

import com.record.model.UserData
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState


data class ProfileState(
    val user: UserData = UserData(
        id = 1,
        profileImage = "https://picsum.photos/id/200/60",
        name = "John Doe",
        isFollowing = true,
    )
) : UiState

sealed class ProfileSideEffect : SideEffect {
    data object Following : ProfileSideEffect()
    data object Unfollowing : ProfileSideEffect()
}
