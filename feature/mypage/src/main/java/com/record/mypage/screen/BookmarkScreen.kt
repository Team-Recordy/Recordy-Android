   package com.record.mypage.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.record.designsystem.component.RecordyVideoThumbnail
import com.record.designsystem.theme.RecordyTheme
import com.record.model.VideoType
import com.record.mypage.MypageTab
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
    val lazyGridState = rememberLazyGridState()
    lazyGridState.OnBottomReached(2) {
        onLoadMore()
    }

    if (recordCount == 0) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    text = buildRecordCountText(recordCount),
                    style = RecordyTheme.typography.caption1R,
                )
            }

            EmptyDataScreen(
                imageRes = com.record.designsystem.R.drawable.img_camera,
                message = "북마크한 영상이 없어요.\n영상을 둘러보고 저장해 보세요!",
                showButton = true,
                selectedTab = MypageTab.BOOKMARK,
                onButtonClick = {
                    // Home? 둘러보기 연결
                },
            )
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
                        .padding(top = 16.dp, bottom = 10.dp),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    Text(
                        text = buildRecordCountText(recordCount),
                        style = RecordyTheme.typography.caption1R,
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
