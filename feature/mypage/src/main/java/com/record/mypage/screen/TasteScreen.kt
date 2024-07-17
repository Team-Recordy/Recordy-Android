package com.record.mypage.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.RecordyTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.filter

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun BoxScope.TasteScreen(dataAvailable: ImmutableList<Pair<String, Int>> = emptyList<Pair<String, Int>>().toImmutableList()) {
    Log.d("TasteScreen", "Data available: $dataAvailable")
    val circleCoords = listOf(
        Pair(258, 327),
        Pair(456, 170),
        Pair(420, 515),
    )
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    var imageWidth by remember { mutableStateOf(0f) }
    var imageHeight by remember { mutableStateOf(0f) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center),
    ) {
        LaunchedEffect(Unit) {
            snapshotFlow { Pair(imageWidth, imageHeight) }
                .filter { (width, height) -> width > 0 && height > 0 }
        }
        if (dataAvailable.size < 3) {
            EmptyDataScreen(
                imageRes = com.record.designsystem.R.drawable.img_question,
                message = "다양한 영상을 기록하면\n취향을 분석해 드려요",
                showButton = true,
                onButtonClick = {
                    // 기록 모달창 띄우기
                },
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0x00000000), Color(0x339BABFB)),

                        ),
                    ),

            ) {
                Image(
                    painter = painterResource(id = com.record.designsystem.R.drawable.img_bg_bubble),
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
                dataAvailable.forEachIndexed { index, pair ->
                    var columnSize by remember { mutableStateOf(IntSize.Zero) }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopStart)
                            .onGloballyPositioned { layoutCoordinates ->
                                columnSize = layoutCoordinates.size
                            }
                            .offset(
                                x = (circleCoords[index].first * screenWidth / 655 - (screenWidth / 2)).dp,
                                y = (circleCoords[index].second * screenWidth / 655 - (screenWidth / 2)).dp,
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = pair.first,
                            style =
                            when (index) {
                                0 -> RecordyTheme.typography.keyword1
                                1 -> RecordyTheme.typography.keyword2
                                else -> RecordyTheme.typography.keyword3
                            },
                            color = RecordyTheme.colors.gray01,
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${pair.second}%",
                            style = when (index) {
                                0 -> RecordyTheme.typography.number1
                                1 -> RecordyTheme.typography.number2
                                else -> RecordyTheme.typography.number3
                            },
                            color = RecordyTheme.colors.gray01,
                        )
                    }
                }
            }
        }
    }
}
