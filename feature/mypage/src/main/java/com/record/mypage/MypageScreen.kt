package com.record.mypage

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.RecordyTheme
import com.record.model.SampleData
import com.record.model.VideoData
import com.record.model.VideoType
import com.record.mypage.screen.BookmarkScreen
import com.record.mypage.screen.RecordScreen
import com.record.mypage.screen.TasteScreen
import com.record.ui.extension.customClickable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MypageRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: MypageViewModel = hiltViewModel(),
    navigateToSetting: () -> Unit,
    navigateToFollower: () -> Unit,
    navigateToFollowing: () -> Unit,
    navigateToVideo: (VideoType, Int) -> Unit,
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
            onFollowingClick = navigateToFollowing,
            onFollowerClick = navigateToFollower,
            navigateToSetting = navigateToSetting,
            navigateToVideo = navigateToVideo,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MypageScreen(
    state: MypageState,
    onTabSelected: (MypageTab) -> Unit,
    navigateToSetting: () -> Unit,
    onFollowerClick: () -> Unit,
    onFollowingClick: () -> Unit,
    navigateToVideo: (VideoType, Int) -> Unit,
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
                .fillMaxWidth(),
        ) {
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
                    .customClickable { navigateToSetting() },
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
                    AsyncImage(
                        model = state.profileImg,
                        contentDescription = "profile",
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
                        buildFollowerFollowingRow(
                            followerNum = state.followerNum,
                            followingNum = state.followingNum,
                            onFollowerClick = onFollowerClick,
                            onFollowingClick = onFollowingClick,
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
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            TasteScreen(
                                listOf(Pair("신나는", 72), Pair("활동적인", 20), Pair("북적북적한", 5)),
                            )
                        }
                    }

                    MypageTab.RECORD.ordinal -> {
                        RecordScreen(
                            videoItems = SampleData.sampleVideos,
                            recordCount = SampleData.sampleVideos.size,
                            onItemClick = navigateToVideo,
                        )
                    }

                    MypageTab.BOOKMARK.ordinal -> {
                        BookmarkScreen(
                            videoItems = SampleData.sampleVideos,
                            recordCount = SampleData.sampleVideos.size,
                            onItemClick = navigateToVideo,
                        )
                    }
                }
            }
        }
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
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
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
        Spacer(modifier = Modifier.height(18.dp))
    }
}

fun formatNumber(number: Int): String {
    return when {
        number >= 10000 -> "${number / 10000}.${(number % 10000) / 1000}만"
        number >= 1000 -> "${number / 1000}.${(number % 1000) / 100}천"
        else -> number.toString()
    }
}

@Composable
private fun buildFollowerFollowingRow(
    followerNum: Int,
    followingNum: Int,
    onFollowerClick: () -> Unit,
    onFollowingClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            modifier = Modifier
                .customClickable { onFollowerClick() },
        ) {
            Text(
                text = formatNumber(followerNum),
                style = RecordyTheme.typography.body2M,
                color = RecordyTheme.colors.white,
            )
            Text(
                text = " 명의 팔로워",
                style = RecordyTheme.typography.body2M,
                color = RecordyTheme.colors.gray03,
            )
        }
        Text(
            text = "|",
            style = RecordyTheme.typography.body2M,
            color = RecordyTheme.colors.gray03,
        )
        Row(
            modifier = Modifier
                .customClickable { onFollowingClick() },
        ) {
            Text(
                text = formatNumber(followingNum),
                style = RecordyTheme.typography.body2M,
                color = RecordyTheme.colors.white,
            )
            Text(
                text = " 명의 팔로잉",
                style = RecordyTheme.typography.body2M,
                color = RecordyTheme.colors.gray03,
            )
        }
    }
}
