package com.record.login

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.theme.Kakao
import com.record.designsystem.theme.RecordyTheme
import dagger.hilt.android.EntryPointAccessors
import kakaoLogo
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    padding: PaddingValues = PaddingValues(vertical = 16.dp),
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current as ComponentActivity
    val entryPoint = EntryPointAccessors.fromActivity<OAuthEntryPoint>(context)
    val oAuthInteractor = entryPoint.getOAuthInteractor()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.StartLogin -> {
                    var result = oAuthInteractor.loginByKakao()
                    result.onSuccess {
                        viewModel.handleLoginSuccess(it)
                    }.onFailure {
                        viewModel.handleLoginError(it.message.toString())
                    }
                }

                is LoginSideEffect.LoginSuccess -> {
                    // 필요 시 LoginSuccess 추가 처리
                }

                is LoginSideEffect.LoginError -> {
                    // 필요 시 LoginError 추가 처리
                }
            }
        }
    }
    LoginScreen(
        padding = padding,
        modifier = modifier,
        uiState = uiState,
        onLogInClick = { viewModel.startKakaoLogin() },
    )
}

@Composable
fun LoginScreen(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    uiState: LoginState,
    onLogInClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                kakaoLogo(),
                null,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .aspectRatio(1f),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "내 취향의 공간 발견하는 곳",
                modifier = Modifier.height(24.dp),
                textAlign = TextAlign.Center,
                style = RecordyTheme.typography.body1M.copy(color = RecordyTheme.colors.main),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onLogInClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Kakao),
            shape = RoundedCornerShape(10.dp),
        ) {
            Image(kakaoLogo(), null, modifier = Modifier.padding(end = 8.dp))
            Text("카카오로 시작하기", style = RecordyTheme.typography.button2.copy(color = RecordyTheme.colors.black))
        }

        Spacer(modifier = Modifier.weight(0.3f))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun LoginView() {
    RecordyTheme {
        LoginRoute(padding = PaddingValues(horizontal = 16.dp), modifier = Modifier.fillMaxSize())
    }
}
