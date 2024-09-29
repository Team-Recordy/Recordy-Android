package com.record.detail

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.component.button.BasicButton
import com.record.designsystem.theme.RecordyTheme
import com.record.detail.screen.ChipTab
import com.record.detail.screen.ListScreen
import com.record.detail.screen.ReviewScreen
import com.record.model.VideoType
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun DetailRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: DetailpageViewModel = hiltViewModel(),
    navigateToUplaod: () -> Unit,
    navigateToVideo: (VideoType, Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.fetchPlaceInfo()
        viewModel.initialData()
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is DetailpageSideEffect.NavigateToVideoDetail -> {
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
        DetailpageScreen(
            state = uiState,
            onTabSelected = { viewModel.selectTab(it) },
            navigateToVideo = viewModel::navigateToVideoDetail,
            onLoadMoreReviews = viewModel::loadMoreReviewVideos,
            onBookmarkClick = viewModel::bookmark,
            navigateToUpload = navigateToUplaod,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailpageScreen(
    state: DetailpageState,
    onTabSelected: (DetailpageTab) -> Unit,
    navigateToVideo: (VideoType, Long) -> Unit,
    navigateToUpload: () -> Unit,
    onLoadMoreReviews: () -> Unit,
    onBookmarkClick: (Long) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = state.detailpageTab.ordinal,
        pageCount = {2},
    )
    val coroutineScope = rememberCoroutineScope()

    val selectedChipState = remember { mutableStateOf(ChipTab.ALL) }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(94.dp))

        Column(
            modifier = Modifier.fillMaxHeight(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(
                        text = state.placeName,
                        style = RecordyTheme.typography.title1,
                        color = RecordyTheme.colors.white,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = state.placeAddress,
                        style = RecordyTheme.typography.body2M,
                        color = RecordyTheme.colors.gray03,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Row {
                        BasicButton(
                            text = "길찾기",
                            textStyle = RecordyTheme.typography.body2SB,
                            textColor = RecordyTheme.colors.background,
                            backgroundColor = RecordyTheme.colors.gray01,
                            shape = RoundedCornerShape(8.dp),
                            onClick = { },
                            padding = PaddingValues(horizontal = 19.dp, vertical = 8.dp),
                            modifier = Modifier,
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        BasicButton(
                            text = "구글 리뷰",
                            textStyle = RecordyTheme.typography.body2SB,
                            textColor = RecordyTheme.colors.background,
                            backgroundColor = RecordyTheme.colors.gray01,
                            shape = RoundedCornerShape(8.dp),
                            onClick = {  },
                            padding = PaddingValues(horizontal = 19.dp, vertical = 8.dp),
                            modifier = Modifier,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(54.dp))

            CustomTabRow(
                selectedTabIndex = state.detailpageTab.ordinal,
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
                    DetailpageTab.LIST.ordinal -> {
                        ListScreen(
                            exhibitionItems = state.exhibitionList,
                            exhibitionCount = state.exhibitionCount,
                            selectedChip = selectedChipState.value,
                            onItemClick = {},
                            onChipSelected = { selectedChip ->
                                selectedChipState.value = selectedChip
                            }
                        )
                    }

                    DetailpageTab.REVIEW.ordinal -> {
                        ReviewScreen(
                            videoItems = state.reviewList,
                            reviewCount = state.reviewVideoCount,
                            onItemClick = navigateToVideo,
                            onLoadMore = onLoadMoreReviews,
                            onBookmarkClick = onBookmarkClick,
                            navigateToUpload = navigateToUpload,
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
    onTabSelected: (DetailpageTab) -> Unit,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {
    var tabWidth by remember { mutableStateOf(0.dp) }
    var indicatorOffset by remember { mutableStateOf(0.dp) }

    val animatedIndicatorOffset by animateDpAsState(
        targetValue = indicatorOffset,
        animationSpec = tween(200),
    )

    val animatedIndicatorWidth by animateDpAsState(
        targetValue = tabWidth - 12.dp,
        animationSpec = tween(200),
    )

    val density = LocalDensity.current

    LaunchedEffect(selectedTabIndex) {
        if (tabWidth > 0.dp) {
            indicatorOffset = (tabWidth * selectedTabIndex) + 6.dp
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
            DetailpageTab.entries.take(2).forEachIndexed { index, tab ->
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
                    contentAlignment = Alignment.Center,
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
