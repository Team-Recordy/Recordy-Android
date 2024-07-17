package com.record.login

import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.R
import com.record.designsystem.theme.Kakao
import com.record.designsystem.theme.RecordyTheme
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current as ComponentActivity
    val entryPoint = EntryPointAccessors.fromActivity<OAuthEntryPoint>(context)
    val oAuthInteractor = entryPoint.getOAuthInteractor()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.StartLogin -> {
                    val result = oAuthInteractor.loginByKakao()
                    result.onSuccess {
                        viewModel.signIn(it.accessToken)
                    }.onFailure {
                        // 카카오 로그인 실패 재시도 요청?
                    }
                }

                is LoginSideEffect.LoginToSignUp -> {
                    navigateToSignUp()
                }

                is LoginSideEffect.LoginSuccess -> {
                    navigateToHome()
                }

                is LoginSideEffect.LoginError -> {
                    // 필요 시 LoginError 추가 처리
                }
            }
        }
    }
    if (uiState.splash) {
        var startAnimation by remember { mutableStateOf(false) }
        val offsetY by animateFloatAsState(
            targetValue = if (startAnimation) 0f else 200f,
            animationSpec = tween(durationMillis = 2000),
        )
        val alpha by animateFloatAsState(
            targetValue = if (startAnimation) 1f else 0f,
            animationSpec = tween(durationMillis = 2000),
        )
        SplashScreen(padding, offsetY, alpha)
        LaunchedEffect(Unit) {
            startAnimation = true
            delay(2500)
            viewModel.splashScreen()
        }
    } else {
        viewModel.autoLoginCheck()
        LoginScreen(
            padding = padding,
            modifier = modifier,
            onLogInClick = { viewModel.startKakaoLogin() },
        )
    }
}

@Composable
fun SplashScreen(
    padding: PaddingValues,
    offsetY: Float,
    alpha: Float,
) {
    var columnSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0x339babfb), Color(0x00000000)),
                    startY = columnSize.height.toFloat() * 0.1f,
                    endY = columnSize.height.toFloat() * 0.6f,
                ),
            ),
    ) {
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = offsetY.dp)
            .onGloballyPositioned { layoutCoordinates ->
                columnSize = layoutCoordinates.size
            }
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painterResource(id = R.drawable.ic_recordy_logo),
            null,
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .aspectRatio(1f)
                .alpha(alpha),

        )
        Spacer(modifier = Modifier.height(40.dp))
        Spacer(modifier = Modifier.weight(1.0f))
        Spacer(modifier = Modifier.height(48.dp))
        Spacer(modifier = Modifier.weight(0.3f))
    }
}

@Composable
fun LoginScreen(
    padding: PaddingValues = PaddingValues(horizontal = 16.dp),
    modifier: Modifier = Modifier,
    onLogInClick: () -> Unit,
) {
    var columnSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                columnSize = layoutCoordinates.size
            }
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0x339babfb), Color(0x00000000)),
                    startY = columnSize.height.toFloat() * 0.1f,
                    endY = columnSize.height.toFloat() * 0.6f,
                ),
            )
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painterResource(id = R.drawable.ic_recordy_logo),
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
                style = RecordyTheme.typography.body1M,
                color = RecordyTheme.colors.main,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onLogInClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Kakao),
            shape = RoundedCornerShape(10.dp),
        ) {
            Image(painterResource(id = R.drawable.ic_kakao_16), null, modifier = Modifier.padding(end = 8.dp))
            Text("카카오로 시작하기", style = RecordyTheme.typography.button2.copy(color = RecordyTheme.colors.black))
        }

        Spacer(modifier = Modifier.weight(0.3f))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun LoginView() {
    RecordyTheme {
        LoginRoute(padding = PaddingValues(horizontal = 16.dp), modifier = Modifier.fillMaxSize(), navigateToHome = {}, navigateToSignUp = {})
    }
}
