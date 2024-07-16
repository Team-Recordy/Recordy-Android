package com.record.mypage.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.record.designsystem.component.button.BasicButton
import com.record.designsystem.theme.RecordyTheme
import com.record.mypage.R

@Composable
fun EmptyDataScreen(imageRes: Int, message: String, showButton: Boolean, onButtonClick: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                Row {
                    Spacer(modifier = Modifier.weight(13f))
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .weight(10f)
                            .aspectRatio(1f),
                    )
                    Spacer(modifier = Modifier.weight(13f))
                }
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = message,
                    style = RecordyTheme.typography.title3,
                    color = RecordyTheme.colors.gray01,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(35.dp))
                if (showButton) {
                    BasicButton(
                        modifier = Modifier
                            .height(44.dp)
                            .fillMaxWidth(0.33f),
                        text = "기록하러 가기",
                        textStyle = RecordyTheme.typography.button2,
                        textColor = RecordyTheme.colors.gray06,
                        backgroundColor = RecordyTheme.colors.gray01,
                        padding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                        shape = RoundedCornerShape(30.dp),
                        onClick = onButtonClick,
                    )
                }
            }
        }

        if (imageRes == com.record.designsystem.R.drawable.img_question) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Text(
                    text = "서로 다른 키워드 3개를 입력하면 그래프가 보여요",
                    color = RecordyTheme.colors.gray03,
                    style = RecordyTheme.typography.caption1,
                    modifier = Modifier.padding(bottom = 22.dp),
                )
            }
        }
    }
}
