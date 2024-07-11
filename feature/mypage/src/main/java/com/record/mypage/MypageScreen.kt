package com.record.mypage

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberImagePainter
import com.record.designsystem.theme.RecordyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MypageRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: MypageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = RecordyTheme.colors.black)
            .padding(bottom = padding.calculateBottomPadding()),
    ) {
        MypageScreen(
            state = uiState,
            onTabSelected = { viewModel.selectTab(it) },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MypageScreen(
    state: MypageState,
    onTabSelected: (MypageTab) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = state.mypageTab.ordinal,
        pageCount = { 3 },
    )
    val coroutineScope = rememberCoroutineScope()

    Column(

    ) {
        // 프로필 , 환경 설정 icon (바로 상위 res/drawable에 위치한 24)
        // "프로필" 부분 삭오빠가 만든 컴포넌트 여기서 활용

        Spacer(modifier = Modifier.height(68.dp)) // 고정 헤더용 임시 spacer
        Spacer(modifier = Modifier.height(16.dp)) // 고정 헤더로부터 16
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(data = state.profileImg),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        )
                        Column {
                            Text(
                                text = state.nickname,
                                style = RecordyTheme.typography.subtitle,
                                color = RecordyTheme.colors.white,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = buildFollowerFollowingText(state),
                                style = RecordyTheme.typography.body2M,
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                CustomTabRow(
                    selectedTabIndex = state.mypageTab.ordinal,
                    onTabSelected = onTabSelected,
                    pagerState = pagerState,
                    coroutineScope = coroutineScope,
                )
            }

            item {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    userScrollEnabled = false,
                ) { page ->
                    when (page) {
                        MypageTab.TASTE.ordinal -> {
                            Text(
                                text = "내 취향",
                                color = RecordyTheme.colors.white,
                            )
                        }

                        MypageTab.RECORD.ordinal -> {
                            Text(
                                text = "내 기록",
                                color = RecordyTheme.colors.white,
                            )
                        }

                        MypageTab.BOOKMARK.ordinal -> {
                            Text(
                                text = "북마크",
                                color = RecordyTheme.colors.white,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x0000)
@Composable
fun MypageScreenPreview() {
    val exampleState = MypageState(
        profileImg = "",
        nickname = "공간수집가열글자아아",
        followerNum = 100,
        followingNum = 50,
        mypageTab = MypageTab.TASTE,
    )
    RecordyTheme{
        MypageScreen(
            state = exampleState,
            onTabSelected = {},
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomTabRow(
    selectedTabIndex: Int,
    onTabSelected: (MypageTab) -> Unit,
    pagerState: PagerState,
    coroutineScope: CoroutineScope
) {
    var t by remember { mutableStateOf(IntSize.Zero) }
    var indicatorWidth by remember { mutableStateOf(0.dp) }
    var indicatorOffset by remember { mutableStateOf(0.dp) }
    val configuration = LocalConfiguration.current
    val s = configuration.screenWidthDp.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            MypageTab.entries.forEachIndexed { index, tab ->
                val selected = index == selectedTabIndex
                val textColor = if (selected) RecordyTheme.colors.gray01 else RecordyTheme.colors.gray04
                val textStyle = if (selected) RecordyTheme.typography.body2L else RecordyTheme.typography.body2M

                val textMeasurer = rememberTextMeasurer()
                val textLayoutResult = textMeasurer.measure(
                    text = AnnotatedString(tab.displayName),
                    style = textStyle
                )

                val textWidth = with(LocalDensity.current) { textLayoutResult.size.width.toDp() }

                if (selected) {
                    val offset = with(LocalDensity.current) {
                        var totalWidth = 0.dp
                        for (i in 0 until index) {
                            val previousTab = MypageTab.entries[i]
                            val previousTextLayoutResult = textMeasurer.measure(
                                text = AnnotatedString(previousTab.displayName),
                                style = RecordyTheme.typography.body2M
                            )
                            totalWidth += with(LocalDensity.current) { previousTextLayoutResult.size.width.toDp() } + 50.dp // 25.dp padding on each side
                        }
                        totalWidth
                    }

                    indicatorWidth = textWidth
                    indicatorOffset = offset
                }

                Column(
                    modifier = Modifier
                        .clickable {
                            onTabSelected(tab)
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                        .onGloballyPositioned { layoutCoordinates ->
                            t = layoutCoordinates.size/5*2
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = tab.displayName,
                        color = textColor,
                        style = textStyle
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ){
            val indicatorIndex by animateDpAsState(
                when(pagerState.currentPage){
                    0 -> {(s - (t.width.dp * 3) - 16.dp)/4}
                    1 -> {(s - (t.width.dp * 3) - 16.dp)/4*2 + t.width.dp}
                    2 -> {(s - (t.width.dp * 3) - 16.dp)/4*3 + t.width.dp * 2}
                    else -> {s - t.width.dp}
                })
            Box(
                modifier = Modifier
                    .width(t.width.dp + indicatorIndex)
                    .height(2.dp)
                    .align(Alignment.TopStart)
                    .padding(start = indicatorIndex)
                    .background(color = RecordyTheme.colors.gray01)
            ){

            }
        }
    }
}


@Composable
private fun buildFollowerFollowingText(state: MypageState): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = RecordyTheme.colors.white)) {
            append("${state.followerNum}")
        }
        withStyle(style = SpanStyle(color = RecordyTheme.colors.gray03)) {
            append(" 명의 팔로워 | ")
        }
        withStyle(style = SpanStyle(color = RecordyTheme.colors.white)) {
            append("${state.followingNum}")
        }
        withStyle(style = SpanStyle(color = RecordyTheme.colors.gray03)) {
            append(" 명의 팔로잉")
        }
    }
}
