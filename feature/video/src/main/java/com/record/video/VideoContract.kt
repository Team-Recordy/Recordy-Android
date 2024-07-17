package com.record.video

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.video.model.VideoData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class VideoState(
    val videos: ImmutableList<VideoData> = emptyList<VideoData>().toImmutableList(),
    val isAll: Boolean = true,
    val isPlaying: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val deleteVideoId: Int = 0,
    val allCursor: Long = 0,
    val followingCursor: Long = 0,
) : UiState

sealed interface VideoSideEffect : SideEffect {
    data class ShowNetworkErrorSnackbar(val msg: String) : VideoSideEffect
    data object NavigateToMypage : VideoSideEffect
    data class NavigateToUserProfile(val id: Int) : VideoSideEffect
    data class MovePage(val index: Int) : VideoSideEffect
}
