package com.record.mypage.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.RecordyTheme
import com.record.mypage.R
import kotlinx.coroutines.flow.filter

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun TasteScreen(dataAvailable: List<Pair<String, Int>> = emptyList(), recordCount: Int) {
    val circleCoords = listOf(
        Pair(258, 327),
        Pair(456, 170),
        Pair(420, 515)
    )

    var imageWidth by remember { mutableStateOf(0f) }
    var imageHeight by remember { mutableStateOf(0f) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
    ) {
        val density = LocalDensity.current

        LaunchedEffect(Unit) {
            // This ensures that the coordinates calculation runs only after the image dimensions are set
            snapshotFlow { Pair(imageWidth, imageHeight) }
                .filter { (width, height) -> width > 0 && height > 0 }
                .collect { (width, height) ->
                    // Do nothing, just trigger recomposition
                }
        }

        val screenCoords = circleCoords.map { (originalX, originalY) ->
            val screenX = if (imageWidth > 0) (originalX.toFloat() / 655f) * imageWidth else 0f
            val screenY = if (imageHeight > 0) (originalY.toFloat() / 655f) * imageHeight else 0f
            Pair(screenX, screenY)
        }

        if (dataAvailable.isEmpty()) {
            EmptyDataScreen(
                imageRes = R.drawable.img_for_empty,
                message = "키워드를 3개 이상 기록하면\n취향을 분석해 드려요",
                recordCount = recordCount,
                showButton = true,
                showRecordCount = false,
                onButtonClick = {
                    // TasteScreen의 버튼 클릭 처리
                }
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_aft_bubble),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .onGloballyPositioned { coordinates ->
                            imageWidth = coordinates.size.width.toFloat()
                            imageHeight = coordinates.size.height.toFloat()
                        },
                    contentScale = ContentScale.FillWidth,
                )
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
