package com.record.upload

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.component.textfield.RecordyBasicTextField
import com.record.designsystem.theme.Background
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable

@Composable
fun VideoPickerRoute(
    paddingValues: PaddingValues,
    navigateSelectedKeyword: () -> Unit,
) {
    VideoPickerScreen(
        navigateSelectedKeyword = navigateSelectedKeyword,
        onClickPickVideo = {},
        onClickKeyword = {}
    )
}

@Composable
fun VideoPickerScreen(
    navigateSelectedKeyword: () -> Unit,
    onClickPickVideo: () -> Unit = {},
    onClickKeyword: () -> Unit = {},
) {
    var normalValue by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState()),
        ) {
            TopNavigationBar(title = "영상 선택", enableGradation = true)
            Text(
                text = "ⓘ 최대 1분의 1080p 영상을 올려주세요.",
                color = RecordyTheme.colors.gray03,
                style = RecordyTheme.typography.caption2,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "영상",
                color = RecordyTheme.colors.white,
                style = RecordyTheme.typography.subtitle,
                modifier = Modifier.padding(top = 20.dp, bottom = 9.dp)
            )
            Box(
                modifier = Modifier
                    .background(RecordyTheme.colors.gray08, shape = RoundedCornerShape(16.dp))
                    .customClickable(onClick = onClickPickVideo),
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 115.dp, horizontal = 57.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus_25),
                        contentDescription = null,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Text(
                        text = "영상 선택",
                        color = RecordyTheme.colors.white,
                        style = RecordyTheme.typography.subtitle,
                    )
                }
            }
            Text(
                text = "키워드",
                color = RecordyTheme.colors.white,
                style = RecordyTheme.typography.subtitle,
                modifier = Modifier.padding(top = 22.dp, bottom = 12.dp)
            )

            Row(
                modifier = Modifier
                    .background(RecordyTheme.colors.gray08, shape = RoundedCornerShape(30.dp))
                    .customClickable(onClick = onClickKeyword),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_plus_25),
                    contentDescription = null,
                )
                Text(
                    text = "키워드",
                    color = RecordyTheme.colors.gray03,
                    style = RecordyTheme.typography.body2M,
                )
            }
            Text(
                text = "위치",
                color = RecordyTheme.colors.white,
                style = RecordyTheme.typography.subtitle,
                modifier = Modifier.padding(top = 20.dp, bottom = 12.dp)
            )
            RecordyBasicTextField(
                placeholder = "영상 속 위치는 어디인가요?",
                maxLines = 1,
                maxLength = 10,
                value = normalValue,
                onValueChange = { normalValue = it },
            )
            Text(
                text = "내용",
                color = RecordyTheme.colors.white,
                style = RecordyTheme.typography.subtitle,
                modifier = Modifier.padding(top = 10.dp, bottom = 12.dp)
            )
            RecordyBasicTextField(
                placeholder = "공간에 대한 나의 생각을 자유롭게 적어주세요!",
                maxLines = 20,
                maxLength = 300,
                value = normalValue,
                onValueChange = { normalValue = it },
            )
        }
    }
}
