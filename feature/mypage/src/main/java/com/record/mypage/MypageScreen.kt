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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
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
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.RecordyTheme
import com.record.mypage.screen.BookmarkScreen
import com.record.mypage.screen.RecordScreen
import com.record.mypage.screen.SampleData
import com.record.mypage.screen.TasteScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class VideoItem(
    val previewUri: String,
    val isBookmarked: Boolean
)

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
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ){
            TopNavigationBar(
                title = "프로필",
                modifier = Modifier.align(Alignment.TopCenter),
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_setting_24),
                contentDescription = null,
                tint = RecordyTheme.colors.white,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 45.dp, end = 16.dp)
                    .size(24.dp)
                    .clickable { }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxHeight(),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Image(
                        painter = rememberImagePainter(data = state.profileImg),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
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

            Spacer(modifier = Modifier.height(16.dp))

            CustomTabRow(
                selectedTabIndex = state.mypageTab.ordinal,
                onTabSelected = onTabSelected,
                pagerState = pagerState,
                coroutineScope = coroutineScope,
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
                userScrollEnabled = false,
            ) { page ->
                when (page) {
                    MypageTab.TASTE.ordinal -> {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            TasteScreen()
                        }
                    }

                    MypageTab.RECORD.ordinal -> {
                        // RecordScreen(videoItems = SampleData.sampleVideos, recordCount = SampleData.sampleVideos.size)
                        RecordScreen(emptyList(), 0)
                    }

                    MypageTab.BOOKMARK.ordinal -> {
                        // BookmarkScreen(videoItems = SampleData.sampleVideos, recordCount = SampleData.sampleVideos.size)
                        BookmarkScreen(emptyList(), 0)
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
        followerNum = 1260,
        followingNum = 96880,
        mypageTab = MypageTab.TASTE,
    )
    RecordyTheme {
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
    coroutineScope: CoroutineScope,
) {
    val tabWidths = remember { mutableStateListOf(0f, 0f, 0f) }
    val tabOffsets = remember { mutableStateListOf(0f, 0f, 0f) }

    var indicatorWidth by remember { mutableStateOf(0.dp) }
    var indicatorOffset by remember { mutableStateOf(0.dp) }

    val animatedIndicatorWidth by animateDpAsState(targetValue = indicatorWidth)
    val animatedIndicatorOffset by animateDpAsState(targetValue = indicatorOffset)

    val density = LocalDensity.current

    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex in tabWidths.indices) {
            indicatorWidth = with(density) { tabWidths[selectedTabIndex].toDp() }
            indicatorOffset = with(density) { tabOffsets[selectedTabIndex].toDp() }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom,
        ) {
            MypageTab.entries.forEachIndexed { index, tab ->
                val selected = index == selectedTabIndex
                val textColor = if (selected) RecordyTheme.colors.gray01 else RecordyTheme.colors.gray04
                val textStyle = if (selected) RecordyTheme.typography.body2L else RecordyTheme.typography.body2M

                Column(
                    modifier = Modifier
                        .clickable {
                            onTabSelected(tab)
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                        .onGloballyPositioned { layoutCoordinates ->
                            val width = layoutCoordinates.size.width.toFloat()
                            val offset = layoutCoordinates.positionInParent().x

                            tabWidths[index] = width
                            tabOffsets[index] = offset
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = tab.displayName,
                        color = textColor,
                        style = textStyle,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
        ) {
            Box(
                modifier = Modifier
                    .width(animatedIndicatorWidth)
                    .height(2.dp)
                    .offset(x = animatedIndicatorOffset)
                    .background(color = RecordyTheme.colors.gray01),
            )
        }
    }
}

@Composable
private fun buildFollowerFollowingText(state: MypageState): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = RecordyTheme.colors.white)) {
            append(formatNumber(state.followerNum))
        }
        withStyle(style = SpanStyle(color = RecordyTheme.colors.gray03)) {
            append(" 명의 팔로워 | ")
        }
        withStyle(style = SpanStyle(color = RecordyTheme.colors.white)) {
            append(formatNumber(state.followingNum))
        }
        withStyle(style = SpanStyle(color = RecordyTheme.colors.gray03)) {
            append(" 명의 팔로잉")
        }
    }
}
fun formatNumber(number: Int): String {
    return when {
        number >= 10000 -> "${number / 10000}.${(number % 10000) / 1000}만"
        number >= 1000 -> "${number / 1000}.${(number % 1000) / 100}천"
        else -> number.toString()
    }
}
