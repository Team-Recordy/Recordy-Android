package com.record.mypage.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.component.RecordyVideoThumbnail
import com.record.designsystem.theme.RecordyTheme
import com.record.mypage.R

@Composable
fun BookmarkScreen(bookmarks: List<Pair<String, Int>> = emptyList(), recordCount: Int) {
    if (bookmarks.isEmpty()) {
        EmptyDataScreen(
            imageRes = R.drawable.img_for_empty,
            message = "자유롭게 취향을\n북마크해 보세요",
            recordCount = recordCount,
            showButton = false,
            showRecordCount = true,
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.TopEnd,
            ) {
                Text(
                    text = "• $recordCount 개의 기록",
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
                        isBookmarkable = true,
                        isBookmark = false,
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
            listOf(
                Pair("집중하기 좋은", 66),
                Pair("조용한", 22),
                Pair("신나는", 8),
            ),
            recordCount = 3,
        )
    }
}
