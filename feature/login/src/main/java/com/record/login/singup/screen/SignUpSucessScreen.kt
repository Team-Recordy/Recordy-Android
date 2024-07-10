package com.record.login.singup.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.R.drawable.ic_signup
import com.record.designsystem.theme.RecordyTheme

@Composable
fun SignUpSucessScreen() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(174.dp))
        Image(
            painter = painterResource(id = ic_signup),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .aspectRatio(1f),
        )
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            "회원가입이 완료되었어요!",
            style = RecordyTheme.typography.title1,
            color = RecordyTheme.colors.gray01,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "지금 영상을 둘러보고 나만의 공간 취향을 발견해 보세요.",
            style = RecordyTheme.typography.caption2,
            color = RecordyTheme.colors.gray03,
        )
    }
}

@Preview
@Composable
fun PreviewSignUpSucessScreen() {
    RecordyTheme {
        SignUpSucessScreen()
    }
}
