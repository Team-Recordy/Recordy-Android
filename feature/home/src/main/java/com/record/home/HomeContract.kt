package com.record.home

import com.record.model.VideoType
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.video.model.VideoData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class HomeState(
    val chipList: ImmutableList<String> = emptyList<String>().toImmutableList(),
    val popularList: ImmutableList<VideoData> = emptyList<VideoData>().toImmutableList(),
    val recentList: ImmutableList<VideoData> = emptyList<VideoData>().toImmutableList(),
    val selectedChipIndex: Int? = null,
    val isLoading: Boolean = false,
) : UiState

sealed interface HomeSideEffect : SideEffect {
    data object navigateToUpload : HomeSideEffect
    data class navigateToVideo(val id: Long, val type: VideoType, val keyword: String?) : HomeSideEffect
}
