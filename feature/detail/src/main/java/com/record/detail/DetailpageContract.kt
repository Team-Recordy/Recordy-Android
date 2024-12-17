package com.record.detail

import com.record.detail.screen.ChipTab
import com.record.exhibition.model.Exhibition
import com.record.model.VideoData
import com.record.model.VideoType
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class DetailpageState(
    val placeId: Long = 0,
    val placeName: String = "",
    val placeAddress: String = "",
    val exhibitionCount: Int = 0,
    val reviewVideoCount: Int = 0,
    val reviewCursor: Long = 0,
    val platformId: Long = 0,
    val reviewIsEnd: Boolean = false,
    val showReportBottomSheet: Boolean = false,
    val detailpageTab: DetailpageTab = DetailpageTab.LIST,
    val selectedChip: ChipTab = ChipTab.ALL,
    val exhibitionList: ImmutableList<Exhibition> = emptyList<Exhibition>().toImmutableList(),
    val reviewList: ImmutableList<VideoData> = emptyList<VideoData>().toImmutableList(),
) : UiState

sealed interface DetailpageSideEffect : SideEffect {
    data class NavigateToVideoDetail(val type: VideoType, val videoId: Long, val placeId: Long) : DetailpageSideEffect
    // data object NavigateToDirection : DetailpageSideEffect
    // data object NavigateToGoogleReview : DetailpageSideEffect
}
