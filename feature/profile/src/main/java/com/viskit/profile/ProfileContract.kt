package com.viskit.profile

import com.viskit.model.VideoType
import com.viskit.ui.base.SideEffect
import com.viskit.ui.base.UiState
import com.viskit.video.model.VideoData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ProfileState(
    val id: Long = 0,
    val followerCount: Int = 0,
    val followingCount: Int = 0,
    val isFollowing: Boolean? = null,
    val nickname: String = "",
    val profileImageUrl: String = "",
    val recordCount: Int = 0,
    val cursorId: Long = 0,
    val userVideos: ImmutableList<VideoData> = emptyList<VideoData>().toImmutableList(),
    val isEnd: Boolean = false,
    val isSelectedVideoSheetOpen: Boolean = false,
) : UiState

sealed class ProfileSideEffect : SideEffect {
    data class navigateToVideoDetail(val type: VideoType, val id: Long, val userId: Long) : ProfileSideEffect()
}
