package com.record.video

import com.record.model.VideoData
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class VideoState(
    val videos: ImmutableList<VideoData> = SampleData.sampleVideos.toImmutableList(),
    val isAll: Boolean = true,
    val isPlaying: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val deleteVideoId: Int = 0,
) : UiState

sealed interface VideoSideEffect : SideEffect {
    data class showNetworkErrorSnackbar(val msg: String) : VideoSideEffect
    data class navigateToUserProfile(val id: Int) : VideoSideEffect
}
