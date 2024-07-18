package com.record.mypage.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.record.designsystem.component.RecordyVideoThumbnail
import com.record.designsystem.theme.RecordyTheme
import com.record.model.VideoType
import com.record.video.model.VideoData
import kotlinx.collections.immutable.ImmutableList

@Composable
fun RecordScreen(
    videoItems: ImmutableList<VideoData>,
    recordCount: Int,
    onItemClick: (VideoType, Long) -> Unit,
) {
    val videos = remember { mutableStateOf(videoItems) }

    if (videos.value.isEmpty()) {
        EmptyDataScreen(
            imageRes = com.record.designsystem.R.drawable.img_camera,
            message = "내 첫 번째 공간 기록을\n작성해 보세요",
            showButton = true,
            onButtonClick = {
                // 기록 모달로 넘어가는 부분
            },
        )
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    Text(
                        text = buildRecordCountText(recordCount),
                        style = RecordyTheme.typography.body2M,
                        color = RecordyTheme.colors.gray01,
                    )
                }
            }
            items(videoItems) { item ->
                RecordyVideoThumbnail(
                    imageUri = item.previewUrl,
                    isBookmarkable = true,
                    isBookmark = item.isBookmark,
                    onBookmarkClick = {
                        //
                    },
                    location = item.location,
                    onClick = {
                        onItemClick(VideoType.MY, item.id)
                    },
                )
            }
        }
    }
}

@Composable
fun buildRecordCountText(recordCount: Int): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = RecordyTheme.colors.white)) {
            append("• $recordCount")
        }
        withStyle(style = SpanStyle(color = RecordyTheme.colors.gray03)) {
            append(" 개의 기록")
        }
    }
}
