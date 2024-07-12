package com.record.mypage.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.RecordyTheme
import com.record.mypage.R

@Composable
fun TasteScreen(dataAvailable: List<Pair<String, Int>> = emptyList(), recordCount: Int) {
    if (dataAvailable.isEmpty()) {
        EmptyDataScreen(
            imageRes = R.drawable.img_for_empty,
            message = "키워드를 3개 이상 기록하면\n취향을 분석해 드려요",
            recordCount = recordCount,
            showButton = true,
            showRecordCount = false,
            onButtonClick = {
                // 기록 모달로 넘어가는 부분
            },
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 43.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.img_aft_bubble),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    items(dataAvailable) { item ->
                        Column(
                            modifier = Modifier.padding(vertical = 8.dp),
                        ) {
                            Text(
                                text = "${item.first}: ${item.second}",
                                style = RecordyTheme.typography.body2M,
                                color = RecordyTheme.colors.white,
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewTasteScreenWithEmptyList() {
    RecordyTheme {
        TasteScreen(
            emptyList(),
            recordCount = 0,
        )
    }
}

@Preview
@Composable
fun PreviewTasteScreenWithSampleList() {
    RecordyTheme {
        TasteScreen(
            listOf(
                Pair("집중하기 좋은", 66),
                Pair("조용한", 22),
                Pair("신나는", 8),
            ),
            recordCount = 3,
        )
    }
}

