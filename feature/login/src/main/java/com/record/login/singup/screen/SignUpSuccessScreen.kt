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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.theme.RecordyTheme

@Composable
fun SignUpSuccessScreen(name: String) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(188.dp))
        Image(
            painter = painterResource(id = R.drawable.img_bubble_checked),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "${name}님,\n" +
                "가입이 완료되었어요!",
            style = RecordyTheme.typography.title1,
            color = RecordyTheme.colors.gray01,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(188.dp))
    }
}

@Preview
@Composable
fun PreviewSignUpSuccessScreen() {
    RecordyTheme {
        SignUpSuccessScreen(name = "test")
    }
}
