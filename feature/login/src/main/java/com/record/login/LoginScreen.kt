package com.record.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.Kakao
import com.record.designsystem.theme.RecordyTheme
import kakaoLogo

@Composable
fun LoginRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
//    viewModel: LoginViewModel = hiltViewModel(),
) {
//    val loginState by viewModel.loginState.collectAsState() // 추후 상태에 따라 이동

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(22f))

        Image(
            kakaoLogo(),
            null,
            modifier = Modifier
                .weight(12f)
                .aspectRatio(1f),
        )

        Spacer(modifier = Modifier.weight(1.6f))

        Text(
            text = "내 취향의 공간 발견하는 곳",
            modifier = Modifier.weight(2.4f),
            textAlign = TextAlign.Center,
            style = RecordyTheme.typography.body1M.copy(color = RecordyTheme.colors.main),
        )

        Spacer(modifier = Modifier.weight(22.3f))

        Button(
            onClick = {
//                    viewModel.loginWithKakao() // 로그인 함수 호출
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(4.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Kakao),
            shape = RoundedCornerShape(10.dp),
        ) {
            Image(kakaoLogo(), null, modifier = Modifier.padding(end = 8.dp))
            Text("카카오로 시작하기", style = RecordyTheme.typography.button2.copy(color = RecordyTheme.colors.black))
        }

        Spacer(modifier = Modifier.weight(9.5f))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun LoginView() {
    RecordyTheme {
        LoginRoute(padding = PaddingValues(horizontal = 16.dp), modifier = Modifier.fillMaxSize())
    }
}
