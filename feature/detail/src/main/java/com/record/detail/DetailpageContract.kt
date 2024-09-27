package com.record.detail


import com.record.model.VideoType
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.video.model.VideoData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.util.Date

data class DetailpageState(
    val placeName: String = "국립현대미술관",
    val placeAddress: String = "서울특별시 종로구 삼청로 30",
    val exhibitionCount: Int = 0,
    val reviewVideoCount: Int = 0,
    val reviewCursor: Long = 0,
    val reviewIsEnd: Boolean = false,
    val detailpageTab: DetailpageTab = DetailpageTab.LIST,
    val exhibitionList: ImmutableList<Pair<String, Date>> = emptyList<Pair<String, Date>>().toImmutableList(),
    val reviewList: ImmutableList<VideoData> = emptyList<VideoData>().toImmutableList(),
) : UiState

sealed interface DetailpageSideEffect : SideEffect {
    data class NavigateToVideoDetail(val type: VideoType, val videoId: Long) : DetailpageSideEffect
    // data object NavigateToDirection : DetailpageSideEffect
    // data object NavigateToGoogleReview : DetailpageSideEffect
}
