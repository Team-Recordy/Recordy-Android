package com.record.video.videodetail

import com.record.model.VideoType
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.video.model.VideoData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class VideoDetailState(
    val videos: ImmutableList<VideoData> = emptyList<VideoData>().toImmutableList(),
    val videoType: VideoType = VideoType.MY,
    val isPlaying: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val deleteVideoId: Long = 0,
    val observingIndex: Int = 0,
    val observingId: Long = 0,
    val page: Int = 0,
    val keyword: String = "",
    val cursor: Long = 0,
    val userId: Long = 0,
    val isEnd: Boolean = false,
    val isInit: Boolean = false,
) : UiState

sealed interface VideoDetailSideEffect : SideEffect {
    data class ShowNetworkErrorSnackbar(val msg: String) : VideoDetailSideEffect
    data object NavigateToMypage : VideoDetailSideEffect
    data class NavigateToUserProfile(val id: Long) : VideoDetailSideEffect
    data class InitialPagerState(val index: Int) : VideoDetailSideEffect
    data class MovePage(val index: Int) : VideoDetailSideEffect
    data object NavigateToBack : VideoDetailSideEffect
}
