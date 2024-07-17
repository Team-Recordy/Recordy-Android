package com.record.home

import com.record.model.VideoType
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.video.model.VideoData
import kotlinx.collections.immutable.toImmutableList

data class HomeState(
    val chipList: List<String> = emptyList<String>().toImmutableList(),
    val popularList: List<VideoData> = emptyList<VideoData>().toImmutableList(),
    val recentList: List<VideoData> = emptyList<VideoData>().toImmutableList(),
    val selectedChipIndex: Int? = null,
    val isLoading: Boolean = false,
) : UiState

sealed interface HomeSideEffect : SideEffect {
    data object navigateToUpload : HomeSideEffect
    data class navigateToVideo(val index: Int, val type: VideoType, val keyword: String?) : HomeSideEffect
}
