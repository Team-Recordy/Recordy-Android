package com.record.detail.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.record.ui.scroll.OnBottomReached
import com.record.video.model.VideoData
import kotlinx.collections.immutable.ImmutableList


@Composable
fun ReviewScreen(
    videoItems: ImmutableList<VideoData>,
    reviewCount: Int,
    onItemClick: (VideoType, Long) -> Unit,
    onLoadMore: () -> Unit,
    onBookmarkClick: (Long) -> Unit,
    navigateToUpload: () -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    lazyGridState.OnBottomReached(2) {
        onLoadMore()
    }

    if (reviewCount == 0) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    Text(
                        text = buildCountText(reviewCount),
                        style = RecordyTheme.typography.caption1R,
                    )
                }
            }

            item {
                EmptyDataScreen(
                    message = "아직 후기가 없어요.\n첫 번째로 후기를 공유해 보세요!",
                    showButton = true,
                    onButtonClick = { navigateToUpload() },
                )
            }
        }
    } else {
        LazyVerticalGrid(
            state = lazyGridState,
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
                        .padding(top = 30.dp, bottom = 12.dp),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    Text(
                        text = buildCountText(reviewCount),
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
                    onBookmarkClick = { onBookmarkClick(item.id) },
                    location = item.location,
                    onClick = { onItemClick(VideoType.MY, item.id) },
                )
            }
        }
    }
}

@Composable
fun buildCountText(recordCount: Int): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = RecordyTheme.colors.white)) {
            append("• $recordCount")
        }
        withStyle(style = SpanStyle(color = RecordyTheme.colors.gray03)) {
            append(" 개의 전시")
        }
    }
}
