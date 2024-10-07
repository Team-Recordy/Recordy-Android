package com.record.login.singup

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.component.button.RecordyButton
import com.record.designsystem.theme.RecordyTheme
import com.record.login.singup.screen.NamingScreen
import com.record.login.singup.screen.PolicyScreen
import com.record.login.singup.screen.SignUpSuccessScreen
import com.record.ui.extension.customClickable
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpRoute(
    padding: PaddingValues,
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateLogin: () -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var columnSize by remember { mutableStateOf(IntSize.Zero) }

    BackHandler(enabled = pagerState.currentPage >= 1 && pagerState.currentPage != 2) {
        coroutineScope.launch {
            viewModel.navScreen(pagerState.currentPage - 1)
            pagerState.animateScrollToPage(
                pagerState.currentPage - 1,
                animationSpec = tween(
                    durationMillis = 200,
                    delayMillis = 100,
                ),
            )
            viewModel.navScreen(pagerState.currentPage - 1)
        }
    }

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                SignUpEffect.ClearFocus -> {
                    focusManager.clearFocus()
                }

                SignUpEffect.NavigateToHome -> {
                    navigateToHome()
                }

                SignUpEffect.NavigateToLogin -> {
                    navigateLogin()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                columnSize = layoutCoordinates.size
            }
            .background(
                color = RecordyTheme.colors.background,
            )
            .customClickable(rippleEnabled = false) { viewModel.clearFocus() },
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.Transparent)
                .fillMaxWidth(),
        ) {
            if (pagerState.currentPage != 2) {
                Icon(
                    ImageVector.vectorResource(id = com.record.designsystem.R.drawable.ic_angle_left_24),
                    contentDescription = "뒤로가기",
                    tint = RecordyTheme.colors.gray01,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .customClickable {
                            coroutineScope.launch {
                                if (pagerState.currentPage > 0) {
                                    viewModel.navScreen(pagerState.currentPage - 1)
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                } else {
                                    viewModel.navigateToLogin()
                                }
                            }
                        },
                )
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = uiState.title,
                color = Color.White,
                style = RecordyTheme.typography.title3,
            )
        }
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
                    onMoreClick = { url ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    },
                )

                SignUpScreen.Naming -> NamingScreen(
                    uiState = uiState,
                    onTextChangeEvent = viewModel::updateNickName,
                    onInputComplete = viewModel::checkValidateNickName,
                )

                SignUpScreen.Success -> SignUpSuccessScreen(name = uiState.nicknameText)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(alignment = Alignment.BottomCenter)) {
            RecordyHorizontalPagerIndicator(modifier = Modifier.fillMaxWidth(), pagerState = pagerState)
            RecordyButton(
                text = if (pagerState.currentPage == 3) "완료" else "다음",
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
                    if (pagerState.currentPage == 2) viewModel.navigateToHome()
                    viewModel.clearFocus()
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
) {
    RecordyTheme {
        Box(
            modifier = Modifier
                .background(color = RecordyTheme.colors.background)
                .fillMaxSize(),
        ) {
//                SignUpRoute(padding = padding, viewModel = viewModel, navigateToHome = {}, navigateLogin = {})
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecordyHorizontalPagerIndicator(
    pagerState: PagerState,
    pageCount: Int = pagerState.pageCount,
    modifier: Modifier = Modifier,
    activeColor: Color = RecordyTheme.colors.viskitYellow500,
    inactiveColor: Color = RecordyTheme.colors.gray08,
    indicatorWidth: Dp = 6.dp,
    indicatorHeight: Dp = indicatorWidth,
    indicatorNotSelectedWidth: Dp = 6.dp,
    indicatorNotSelectedHeight: Dp = indicatorNotSelectedWidth,
    spacing: Dp = indicatorWidth,
    indicatorShape: RoundedCornerShape = CircleShape,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        repeat(pageCount) { page ->
            val isSelected = page == pagerState.currentPage
            Box(
                modifier = Modifier
                    .size(
                        width = if (isSelected) indicatorWidth else indicatorNotSelectedWidth,
                        height = if (isSelected) indicatorHeight else indicatorNotSelectedHeight,
                    )
                    .clip(indicatorShape)
                    .background(if (isSelected) activeColor else inactiveColor),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}
