package com.record.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.record.designsystem.R
import com.record.designsystem.component.RecordyVideoThumbnail
import com.record.designsystem.component.button.RecordyChipButton
import com.record.designsystem.theme.RecordyTheme
import com.record.home.component.UploadFloatingButton
import com.record.model.VideoType
import com.record.ui.extension.customClickable
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import com.record.video.model.VideoData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.ExperimentalToolbarApi
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun HomeRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToVideoDetail: (VideoType, Long, String?, Long) -> Unit,
    navigateToUpload: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.getVideos()
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                HomeSideEffect.navigateToUpload -> navigateToUpload()
                is HomeSideEffect.navigateToVideo -> {
                    navigateToVideoDetail(sideEffect.type, sideEffect.id, sideEffect.keyword, 0)
                }
            }
        }
    }
    HomeScreen(
        modifier = modifier.padding(bottom = padding.calculateBottomPadding()),
        state = state,
        onUploadButtonClick = viewModel::navigateToUpload,
        onChipButtonClick = viewModel::selectCategory,
        onVideoClick = viewModel::navigateToVideo,
        onBookmarkClick = viewModel::bookmark,
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onUploadButtonClick: () -> Unit,
    onChipButtonClick: (Int) -> Unit,
    onVideoClick: (Long, VideoType) -> Unit,
    onBookmarkClick: (Long) -> Unit,
) {
    var boxSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                boxSize = layoutCoordinates.size
            }
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0x339babfb), Color(0x00000000)),
                    startY = boxSize.height.toFloat() * 0.0f,
                    endY = boxSize.height.toFloat() * 0.3f,
                ),
            ),
    ) {
        BackgroundAnimation()
        CollapsingToolbar(
            state = state,
            onChipButtonClick = onChipButtonClick,
            onVideoClick = onVideoClick,
            onBookmarkClick = onBookmarkClick,
        )
        UploadFloatingButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 15.dp, end = 16.dp),
            onClick = onUploadButtonClick,
        )
        if (state.isLoading) {
            LoadingLottie()
        }
    }
}

@Composable
fun BoxScope.BackgroundAnimation() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.bubble))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f,
    )
    LottieAnimation(
        composition,
        { progress },
        modifier = Modifier
            .align(Alignment.TopCenter),
    )
}

@Composable
fun LoadingLottie() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_lotties))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 4.0f,
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .customClickable(rippleEnabled = false) {}
            .background(color = RecordyTheme.colors.black50),
    ) {
        LottieAnimation(
            composition,
            { progress },
            modifier = Modifier
                .align(Alignment.Center),
        )
    }
}

@Composable
fun CollapsingToolbar(
    state: HomeState,
    onChipButtonClick: (Int) -> Unit,
    onVideoClick: (Long, VideoType) -> Unit,
    onBookmarkClick: (Long) -> Unit,
) {
    val toolbarState = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarScaffold(
        modifier = Modifier
            .fillMaxSize(),
        state = toolbarState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        enabled = true,
        toolbar = {
            ToolbarContent(toolbarState)
        },
    ) {
        ChipRow(toolbarState, state.chipList, state.selectedChipIndex, onChipButtonClick)
        Content(
            state = state,
            onVideoClick = onVideoClick,
            onBookmarkClick = onBookmarkClick,
        )
    }
}

@Composable
fun CollapsingToolbarScope.ToolbarContent(toolbarState: CollapsingToolbarScaffoldState) {
    val topPadding = (32 + 12 * toolbarState.toolbarState.progress).dp
    val alpha = toolbarState.toolbarState.progress * 2 - 0.5f
    Image(
        painter = painterResource(R.drawable.ic_recordy_logo),
        contentDescription = "logo",
        modifier = Modifier
            .padding(start = 16.dp, top = topPadding, bottom = 12.dp)
            .pin(),
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(246.dp)
            .road(Alignment.CenterEnd, Alignment.BottomStart)
            .alpha(alpha)
            .padding(start = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = "오늘은 어떤 키워드로\n공간을 둘러볼까요?",
                modifier = Modifier
                    .weight(192f)
                    .padding(bottom = 28.dp),
                style = RecordyTheme.typography.title1,
                color = RecordyTheme.colors.white,
            )

            Image(
                modifier = Modifier
                    .weight(140f)
                    .padding(end = 12.dp),
                painter = painterResource(R.drawable.img_home_graphic),
                contentDescription = "home",
            )
        }
    }
}

@OptIn(ExperimentalToolbarApi::class)
@Composable
fun ChipRow(
    state: CollapsingToolbarScaffoldState,
    chipList: List<String>,
    selectedChip: Int?,
    onChipButtonClick: (Int) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        modifier = Modifier
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item { Spacer(modifier = Modifier.width(8.dp)) }
        itemsIndexed(chipList) { i, item ->
            RecordyChipButton(
                text = item,
                isActive = selectedChip == i,
                onClick = {
                    onChipButtonClick(i)
                    coroutineScope.launch {
                        state.toolbarState.collapse(200)
                    }
                },
            )
        }
        item { Spacer(modifier = Modifier.width(8.dp)) }
    }
}

@Composable
fun Content(
    state: HomeState,
    onVideoClick: (Long, VideoType) -> Unit,
    onBookmarkClick: (Long) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 44.dp),
    ) {
        item {
            Section(
                title = "이번 주 인기 기록",
                videoList = state.popularList,
                screenWidth = screenWidth,
                onVideoClick = onVideoClick,
                onBookmarkClick = onBookmarkClick,
                videoType = VideoType.POPULAR,
            )
            Section(
                title = "방금 막 올라왔어요",
                videoList = state.recentList,
                screenWidth = screenWidth,
                onVideoClick = onVideoClick,
                onBookmarkClick = onBookmarkClick,
                videoType = VideoType.RECENT,
            )
            Spacer(modifier = Modifier.height(56.dp))
        }
    }
}

@Composable
fun Section(
    title: String,
    videoList: List<VideoData>,
    screenWidth: Dp,
    onVideoClick: (Long, VideoType) -> Unit,
    onBookmarkClick: (Long) -> Unit,
    videoType: VideoType,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = title,
            style = RecordyTheme.typography.subtitle,
            color = RecordyTheme.colors.white,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 12.dp),
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item { Spacer(modifier = Modifier.width(4.dp)) }
            itemsIndexed(videoList) { index, videoData ->
                RecordyVideoThumbnail(
                    modifier = Modifier.width(screenWidth / 8 * 3),
                    imageUri = videoData.previewUrl,
                    location = videoData.location,
                    isBookmarkable = true,
                    isBookmark = videoData.isBookmark,
                    onClick = { onVideoClick(videoData.id, videoType) },
                    onBookmarkClick = { onBookmarkClick(videoData.id) },
                )
            }
            item { Spacer(modifier = Modifier.width(4.dp)) }
        }
    }
}

@Preview
@Composable
fun PreviewHome() {
    RecordyTheme {
    }
}
