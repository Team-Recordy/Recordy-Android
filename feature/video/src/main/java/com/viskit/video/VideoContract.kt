package com.viskit.video

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.video.model.VideoData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class VideoState(
    val allVideos: ImmutableList<VideoData> = emptyList<VideoData>().toImmutableList(),
    val followingVideos: ImmutableList<VideoData> = emptyList<VideoData>().toImmutableList(),
    val isAll: Boolean = false,
    val isPlaying: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val showReportBottomSheet: Boolean = false,
    val selectedVideoId: Long = 0,
    val selectedVideoIsMine: Boolean = false,
    val deleteVideoId: Long = 0,
    val allIndex: Long = 0,
    val followingCursor: Long = 0,
    val followingIndex: Long = 0,
) : UiState

sealed interface VideoSideEffect : SideEffect {
    data class ShowNetworkErrorSnackbar(val msg: String) : VideoSideEffect
    data class ShowReportSnackbar(val msg: String) : VideoSideEffect
    data object NavigateToMypage : VideoSideEffect
    data class NavigateToUserProfile(val id: Long) : VideoSideEffect
    data class MovePage(val index: Int) : VideoSideEffect
    data class MoveFollowingPage(val index: Int) : VideoSideEffect
    data class NavigateToPlaceDetail(val id: Long) : VideoSideEffect
}
