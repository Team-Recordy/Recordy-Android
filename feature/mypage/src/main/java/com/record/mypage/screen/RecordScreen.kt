package com.record.mypage.screen

import android.widget.Space
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
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import com.record.model.VideoData
import com.record.mypage.R
import com.record.mypage.VideoItem

@Composable
fun RecordScreen(videoItems: List<VideoData>, recordCount: Int) {
    if (videoItems.isEmpty()) {
        EmptyDataScreen(
            imageRes = com.record.designsystem.R.drawable.img_camera,
            message = "내 첫 번째 공간 기록을\n작성해 보세요",
            recordCount = recordCount,
            showButton = true,
            showRecordCount = true,
            onButtonClick = {
                // 기록 모달로 넘어가는 부분
            }
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
                        isBookmarkable = false,
                        isBookmark = false,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRecordScreenWithSampleList() {
    RecordyTheme {
        RecordScreen(
            SampleData.sampleVideos, SampleData.sampleVideos.size
        )
    }
}
