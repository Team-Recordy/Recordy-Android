package com.record.mypage

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.RecordyTheme
import com.record.model.VideoType
import com.record.mypage.screen.BookmarkScreen
import com.record.mypage.screen.RecordScreen
import com.record.ui.extension.customClickable
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MypageRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: MypageViewModel = hiltViewModel(),
    navigateToSetting: () -> Unit,
    navigateToFollower: () -> Unit,
    navigateToFollowing: () -> Unit,
    navigateToUplaod: () -> Unit,
    navigateToVideo: (VideoType, Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.fetchUserProfile()
        viewModel.initialData()
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                MypageSideEffect.NavigateToFollower -> {
                    navigateToFollower()
                }

                MypageSideEffect.NavigateToFollowing -> {
                    navigateToFollowing()
                }

                MypageSideEffect.NavigateToSettings -> {
                    navigateToSetting()
                }

                is MypageSideEffect.NavigateToVideoDetail -> {
                    navigateToVideo(sideEffect.type, sideEffect.videoId)
                }
            }
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = RecordyTheme.colors.black)
            .padding(bottom = padding.calculateBottomPadding()),
    ) {
        MypageScreen(
            state = uiState,
            onTabSelected = { viewModel.selectTab(it) },
            onFollowingClick = viewModel::navigateToFollowing,
            onFollowerClick = viewModel::navigateToFollower,
            navigateToSetting = viewModel::navigateToSetting,
            navigateToVideo = viewModel::navigateToVideoDetail,
            onLoadMoreBookmarks = viewModel::loadMoreBookmarkVideos,
            onLoadMoreRecords = viewModel::loadMoreUserVideos,
            onBookmarkClick = viewModel::bookmark,
            navigateToUpload = navigateToUplaod,
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
    navigateToVideo: (VideoType, Long) -> Unit,
    navigateToUpload: () -> Unit,
    onLoadMoreRecords: () -> Unit,
    onLoadMoreBookmarks: () -> Unit,
    onBookmarkClick: (Long) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = state.mypageTab.ordinal,
        pageCount = { 2 },
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
                    .padding(horizontal = 10.dp),
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
                        BuildFollowerFollowingRow(
                            followerNum = state.followerNum,
                            followingNum = state.followingNum,
                            onFollowerClick = onFollowerClick,
                            onFollowingClick = onFollowingClick,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            CustomTabRow(
                selectedTabIndex = state.mypageTab.ordinal,
                onTabSelected = onTabSelected,
                pagerState = pagerState,
                coroutineScope = coroutineScope,
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
                userScrollEnabled = false,
            ) { page ->
                when (page) {
                    MypageTab.RECORD.ordinal -> {
                        RecordScreen(
                            videoItems = state.myRecordList,
                            recordCount = state.recordVideoCount,
                            onItemClick = navigateToVideo,
                            onLoadMore = onLoadMoreRecords,
                            onBookmarkClick = onBookmarkClick,
                            navigateToUpload = navigateToUpload,
                        )
                    }

                    MypageTab.BOOKMARK.ordinal -> {
                        BookmarkScreen(
                            videoItems = state.myBookmarkList,
                            recordCount = state.bookmarkVideoCount,
                            onItemClick = navigateToVideo,
                            onLoadMore = onLoadMoreBookmarks,
                            onBookmarkClick = onBookmarkClick,
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
    var tabWidth by remember { mutableStateOf(0.dp) }
    var indicatorOffset by remember { mutableStateOf(0.dp) }

    val animatedIndicatorOffset by animateDpAsState(
        targetValue = indicatorOffset,
        animationSpec = tween(200)
    )

    val animatedIndicatorWidth by animateDpAsState(
        targetValue = tabWidth - 12.dp,
        animationSpec = tween(200)
    )

    val density = LocalDensity.current

    LaunchedEffect(selectedTabIndex) {
        if (tabWidth > 0.dp) {
            indicatorOffset = ( tabWidth * selectedTabIndex ) + 6.dp
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
            MypageTab.entries.take(2).forEachIndexed { index, tab ->
                val selected = index == selectedTabIndex
                val textColor = if (selected) RecordyTheme.colors.gray01 else RecordyTheme.colors.gray05
                val textStyle = if (selected) RecordyTheme.typography.body2B else RecordyTheme.typography.body2M

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable {
                            onTabSelected(tab)
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                        .onGloballyPositioned { layoutCoordinates ->
                            tabWidth = with(density) { layoutCoordinates.size.width.toDp() }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab.displayName,
                        color = textColor,
                        style = textStyle,
                    )
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

fun formatNumber(number: Int): String {
    return when {
        number >= 10000 -> "${number / 10000}.${(number % 10000) / 1000}만"
        number >= 1000 -> "${number / 1000}.${(number % 1000) / 100}천"
        else -> number.toString()
    }
}

@Composable
private fun BuildFollowerFollowingRow(
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
            text = " | ",
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
