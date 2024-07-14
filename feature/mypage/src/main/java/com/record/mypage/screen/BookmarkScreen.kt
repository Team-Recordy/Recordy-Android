package com.record.mypage.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.component.RecordyVideoThumbnail
import com.record.designsystem.theme.RecordyTheme
import com.record.model.VideoData
import com.record.mypage.R

@Composable
fun BookmarkScreen(videoItems: List<VideoData>, recordCount: Int) {
    if (videoItems.isEmpty()) {
        EmptyDataScreen(
            imageRes = com.record.designsystem.R.drawable.img_bookmark,
            message = "자유롭게 취향을 북마크해 보세요",
            showButton = false,
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.TopEnd,
            ) {
                Text(
                    text = buildRecordCountText(recordCount),
                    style = RecordyTheme.typography.body2M,
                    color = RecordyTheme.colors.gray01,
                )
            }

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 43.dp)
                    .align(Alignment.BottomStart),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(SampleData.sampleVideos) { item ->
                    RecordyVideoThumbnail(
                        imageUri = item.previewUri,
                        isBookmarkable = false,
                        isBookmark = false,
                        location = item.location,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBookmarkScreenWithSampleList() {
    RecordyTheme {
        BookmarkScreen(
            SampleData.sampleVideos,
            SampleData.sampleVideos.size,
        )
    }
}

@Preview
@Composable
fun PreviewBookmarkScreeEmptyList() {
    RecordyTheme {
        BookmarkScreen(
            emptyList(),
            0,
        )
    }
}
