package com.record.upload

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.record.designsystem.component.button.RecordyButton
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.Background
import com.record.designsystem.theme.Gray03
import com.record.designsystem.theme.RecordyTheme
@Composable
fun SelectedKeywordRoute(
    paddingValues: PaddingValues,
    navigateDefinedContent: () -> Unit,
){
    SelectedKeywordScreen()
}
@Composable
fun SelectedKeywordScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter),
        ) {
            TopNavigationBar(title = "영상 선택", enableGradation = true)
            Text(
                text = "ⓘ 최대 1분의 1080p 영상을 올려주세요.1",
                color = Gray03,
                style = RecordyTheme.typography.caption2,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
        RecordyButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "다음",
            enabled = true,
            onClick = { /*TODO*/ },
        )
    }
}
