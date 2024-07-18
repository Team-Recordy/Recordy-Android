package com.record.profile

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.user.model.Profile

data class ProfileState(
    val user: Profile = Profile(
        id = 0,
        followerCount = 0,
        followingCount = 0,
        isFollowing = false,
        nickname = "",
        profileImageUrl = "",
        recordCount = 0,
    ),
) : UiState

sealed class ProfileSideEffect : SideEffect
