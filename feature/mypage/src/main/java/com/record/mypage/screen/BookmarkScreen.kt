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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.record.designsystem.component.RecordyVideoThumbnail
import com.record.designsystem.theme.RecordyTheme
import com.record.model.VideoType
import com.record.ui.scroll.OnBottomReached
import com.record.video.model.VideoData
import kotlinx.collections.immutable.ImmutableList

@Composable
fun BookmarkScreen(
    videoItems: ImmutableList<VideoData>,
    recordCount: Int,
    onItemClick: (VideoType, Long) -> Unit,
    onLoadMore: () -> Unit,
    onBookmarkClick: (Long) -> Unit,
) {
    val videos = remember { mutableStateOf(videoItems) }
    val lazyGridState = rememberLazyGridState()
    lazyGridState.OnBottomReached(2) {
        onLoadMore()
    }
    if (videos.value.isEmpty()) {
        EmptyDataScreen(
            imageRes = com.record.designsystem.R.drawable.img_bookmark,
            message = "자유롭게 취향을 북마크해 보세요",
            showButton = false,
        )
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
                        onBookmarkClick(item.id)
                    },
                    location = item.location,
                    onClick = {
                        onItemClick(VideoType.BOOKMARK, item.bookmarkId)
                    },
                )
            }
        }
    }
}
