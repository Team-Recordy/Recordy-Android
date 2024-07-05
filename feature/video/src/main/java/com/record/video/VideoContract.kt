package com.record.video

import com.record.model.VideoData
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class VideoState(
    val videos: ImmutableList<VideoData> = SampleData.sampleVideos.toImmutableList(),
    val isPlaying: Boolean = false,
) : UiState

sealed interface VideoSideEffect : SideEffect
