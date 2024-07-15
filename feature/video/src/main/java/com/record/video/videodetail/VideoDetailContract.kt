package com.record.video.videodetail

import com.record.model.VideoData
import com.record.model.VideoType
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.video.SampleData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class VideoDetailState(
    val videos: ImmutableList<VideoData> = SampleData.sampleVideos.toImmutableList(),
    val videoType: VideoType = VideoType.MY,
    val isPlaying: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val deleteVideoId: Int = 0,
    val observingId: Int = 0,
) : UiState

sealed interface VideoDetailSideEffect : SideEffect {
    data class ShowNetworkErrorSnackbar(val msg: String) : VideoDetailSideEffect
    data object NavigateToMypage : VideoDetailSideEffect
    data class NavigateToUserProfile(val id: Int) : VideoDetailSideEffect
}
