package com.record.login.singup

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.component.button.RecordyButton
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.component.progressbar.RecordyProgressBar
import com.record.designsystem.theme.RecordyTheme
import com.record.login.singup.screen.NamingScreen
import com.record.login.singup.screen.PolicyScreen
import com.record.login.singup.screen.SignUpSuccessScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpRoute(
    padding: PaddingValues = PaddingValues(horizontal = 16.dp),
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    var columnSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    BackHandler(enabled = pagerState.currentPage >= 1 && pagerState.currentPage != 2) {
        coroutineScope.launch {
            viewModel.navScreen(pagerState.currentPage - 1)
            pagerState.animateScrollToPage(
                pagerState.currentPage - 1,
                animationSpec = tween(
                    durationMillis = 500,
                    delayMillis = 300,
                ),
            )
            viewModel.navScreen(pagerState.currentPage - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                columnSize = layoutCoordinates.size
            }
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0x339babfb), Color(0x00000000)),
                    startY = columnSize.height.toFloat() * 0f,
                    endY = columnSize.height.toFloat() * 0.3f,
                ),
            ),
    ) {
        TopNavigationBar(
            title = uiState.title,
        )
        Spacer(modifier = Modifier.height(12.dp))
        RecordyProgressBar(
            completionRatioNumerator = pagerState.currentPage + 1,
            completionRatioDenominator = pagerState.pageCount,
        )

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxSize(),
        ) { page ->

            when (SignUpScreen.fromScreenNumber(page)) {
                SignUpScreen.Policy -> PolicyScreen(
                    padding = padding,
                    uiState = uiState,
                    onCheckAllClick = viewModel::allCheckEvent,
                    onCheckServiceClick = viewModel::checkServiceEvent,
                    onCheckPolicyClick = viewModel::checkPrivacyPolicyEvent,
                    onCheckAgeClick = viewModel::checkAgeEvent,
                )

                SignUpScreen.Naming -> NamingScreen(
                    uiState = uiState,
                    onTextChangeEvent = viewModel::updateNickName,
                    onInputComplete = viewModel::checkValidateNickName,
                )

                SignUpScreen.Success -> SignUpSuccessScreen()
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(alignment = Alignment.BottomCenter)) {
            RecordyButton(
                text = "다음",
                enabled = uiState.btnEnable,
                clickable = uiState.btnEnable,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage + 1,
                            animationSpec = tween(
                                durationMillis = 500,
                                delayMillis = 0,
                            ),
                        )
                    }
                    if (pagerState.currentPage == 2) navigateToHome()
                    focusManager.clearFocus()
                    viewModel.navScreen(pagerState.currentPage + 1)
                },
                modifier = Modifier
                    .imePadding()
                    .padding(horizontal = 16.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
fun PreviewSignUp(
    padding: PaddingValues = PaddingValues(horizontal = 16.dp),
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    RecordyTheme {
        Box(modifier = Modifier.background(color = RecordyTheme.colors.background)) {
            SignUpRoute(padding = padding, viewModel = viewModel, navigateToHome = {})
        }
    }
}

enum class SignUpScreen(val screenNumber: Int) {
    Policy(0),
    Naming(1),
    Success(2),
    ;

    companion object {
        fun fromScreenNumber(screenNumber: Int): SignUpScreen {
            return entries.firstOrNull { it.screenNumber == screenNumber } ?: Success
        }
    }
}
