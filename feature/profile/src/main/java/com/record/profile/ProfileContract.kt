package com.record.profile

import com.record.model.VideoType
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.video.model.VideoData
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
) : UiState

sealed class ProfileSideEffect : SideEffect {
    data class navigateToVideoDetail(val type: VideoType, val id: Long, val userId: Long) : ProfileSideEffect()
}
