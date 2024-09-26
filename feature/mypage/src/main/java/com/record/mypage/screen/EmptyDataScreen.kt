package com.record.mypage.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.record.designsystem.component.button.RecordyButton
import com.record.designsystem.theme.RecordyTheme
import com.record.mypage.MypageTab

@Composable
fun EmptyDataScreen(
    imageRes: Int,
    message: String,
    showButton: Boolean,
    selectedTab: MypageTab,
    onButtonClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.weight(1f))
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
                style = RecordyTheme.typography.title2,
                color = RecordyTheme.colors.gray01,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(24.dp))
            if (showButton) {
                val buttonText = when (selectedTab) {
                    MypageTab.RECORD -> "영상 업로드하기"
                    MypageTab.BOOKMARK -> "영상 둘러보기"
                }

                RecordyButton(
                    modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth(0.33f),
                    text = buttonText,
                    textStyle = RecordyTheme.typography.body2B,
                    textColor = RecordyTheme.colors.background,
                    backgroundColor = RecordyTheme.colors.viskitYellow400,
                    shape = RoundedCornerShape(30.dp),
                    onClick = onButtonClick,
                )
            }
            Spacer(modifier = Modifier.weight(2.2f))
        }
    }
}

