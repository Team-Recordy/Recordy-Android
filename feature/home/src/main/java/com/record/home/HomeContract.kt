package com.record.home

import com.record.model.VideoData
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import kotlinx.collections.immutable.toImmutableList

data class HomeState(
    val chipList: List<String> = listOf("감각적인", "강렬한", "귀여운", "깔끔한", "덕후몰이", "아늑한", "이색적인", "재밌는", "조용한", "집중하기 좋은", "클래식한", "트렌디한").toImmutableList(),
    val popularList: List<VideoData> = SampleData.sampleVideos.toImmutableList(),
    val recentList: List<VideoData> = SampleData.sampleVideos.toImmutableList(),
    val selectedChip: String = "",
    val isLoading: Boolean = false,
) : UiState

sealed interface HomeSideEffect : SideEffect {
    data object navigateToUpload : HomeSideEffect
    data class navigateToVideo(val id: Int, val type: VideoType) : HomeSideEffect
}
