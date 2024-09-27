package com.record.detail.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.RecordyTheme
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.TopEnd,
            ) {
                Text(
                    text = buildCountText(exhibitionCount),
                    style = RecordyTheme.typography.caption1R,
                )
            }

            EmptyDataScreen(
                message = "\n진행 중인 전시가 없어요.",
                showButton = false,
            )
        }

    } else {

    }
}
