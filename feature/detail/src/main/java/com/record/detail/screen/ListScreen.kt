package com.record.detail.screen

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import com.record.model.VideoType
import com.record.ui.scroll.OnBottomReached
import com.record.video.model.VideoData
import kotlinx.collections.immutable.ImmutableList
import java.util.Date

@Composable
fun ListScreen(
    exhibitionItems: ImmutableList<Pair<String, Date>>,
    exhibitionCount: Int,
    onItemClick: (VideoType, Long) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    if (exhibitionCount == 0) {
        EmptyDataScreen(
            message = "진행 중인 전시가 없어요.",
            showButton = false,
        )
    } else {

    }
}
