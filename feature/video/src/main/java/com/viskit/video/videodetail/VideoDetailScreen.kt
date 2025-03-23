package com.viskit.video.videodetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.Cache
import com.record.designsystem.R
import com.record.designsystem.component.badge.RecordyExhibitionBadge
import com.record.designsystem.component.badge.RecordyLocationBadge
import com.record.designsystem.component.dialog.RecordyDialog
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.designsystem.component.videoplayer.RecordyVideoText
import com.record.designsystem.component.videoplayer.VideoPlayer
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import com.record.ui.scroll.onBottomReached
import com.record.video.component.ReportBottomSheet
import kotlinx.coroutines.flow.collectLatest

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun VideoDetailRoute(
    padding: PaddingValues,
    modifier: Modifier,
    viewModel: VideoDetailViewModel = hiltViewModel(),
    onShowSnackbar: (String, SnackBarType) -> Unit,
    navigateToUserProfile: (Long) -> Unit,
    navigateToMypage: () -> Unit,
    navigateToPlaceDetail: (Long) -> Unit,
    popBackStack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.videos.size },
    )
    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is VideoDetailSideEffect.NavigateToUserProfile -> {
                    navigateToUserProfile(sideEffect.id)
                }

                is VideoDetailSideEffect.NavigateToMypage -> {
                    navigateToMypage()
                }

                is VideoDetailSideEffect.ShowNetworkErrorSnackbar -> {
                    onShowSnackbar(sideEffect.msg, SnackBarType.WARNING)
                }

                is VideoDetailSideEffect.InitialPagerState -> {
                    pagerState.scrollToPage(sideEffect.index)
                }

                is VideoDetailSideEffect.MovePage -> {
                    pagerState.scrollToPage(pagerState.currentPage - sideEffect.index)
                }

                VideoDetailSideEffect.NavigateToBack -> {
                    popBackStack()
                }

                is VideoDetailSideEffect.NavigateToPlaceDetail -> {
                    navigateToPlaceDetail(sideEffect.id)
                }

                is VideoDetailSideEffect.ShowReportSnackbar -> {
                    onShowSnackbar(sideEffect.msg, SnackBarType.CHECK)
                }
            }
        }
    }
    VideoDetailScreen(
        pagerState = pagerState,
        modifier = modifier
            .padding(bottom = padding.calculateBottomPadding())
            .fillMaxSize(),
        state = state,
        onDeleteClick = viewModel::showDeleteDialog,
        onBookmarkClick = viewModel::bookmark,
        onDeleteDialogDismissRequest = viewModel::dismissDeleteDialog,
        onError = viewModel::showNetworkErrorSnackbar,
        onPlayVideo = viewModel::watchVideo,
        onNickNameClick = viewModel::navigateToProfile,
        loadMoreVideos = viewModel::getVideos,
        onBackButtonClick = viewModel::navigateToBack,
        onDialogDeleteButtonClick = viewModel::deleteVideo,
        onMoreClick = viewModel::showReportBottomSheet,
        onBottomSheetDismiss = viewModel::hideReportBottomSheet,
        onPlaceButtonClick = viewModel::navigateToPlaceDetail,
        reportVideo = viewModel::reportVideo,
        simpleCache = viewModel.simpleCache,
    )
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun VideoDetailScreen(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    state: VideoDetailState,
    onDeleteClick: (Long) -> Unit,
    onBookmarkClick: (Long) -> Unit,
    onDeleteDialogDismissRequest: () -> Unit,
    onNickNameClick: (Long, Boolean) -> Unit,
    onError: (String) -> Unit,
    onPlayVideo: (Long) -> Unit,
    loadMoreVideos: () -> Unit,
    onBackButtonClick: () -> Unit,
    onDialogDeleteButtonClick: () -> Unit,
    onMoreClick: (Long, Boolean) -> Unit,
    onBottomSheetDismiss: () -> Unit,
    onPlaceButtonClick: (Long) -> Unit,
    reportVideo: (Long, String, String) -> Unit,
    simpleCache: Cache,
) {
    pagerState.onBottomReached(
        buffer = 3,
        onLoadMore = { loadMoreVideos() },
    )
    Box(
        modifier = modifier,
    ) {
        VerticalPager(
            state = pagerState,
            beyondViewportPageCount = 0,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            Box {
                if (page in state.videos.indices) {
                    state.videos[page].run {
                        VideoPlayer(id, videoUrl, pagerState, page, onError = onError, onPlayVideo = onPlayVideo, simpleCache)
                        if (location.isNotEmpty()) {
                            RecordyLocationBadge(
                                modifier = Modifier
                                    .zIndex(1.0f)
                                    .align(Alignment.TopStart)
                                    .padding(
                                        top = 102.dp,
                                        start = 16.dp,
                                    )
                                    .customClickable {
                                        onPlaceButtonClick(placeId)
                                    },
                                location = location,
                            )
                        }
                        if (exhibitionName.isNotEmpty()) {
                            RecordyExhibitionBadge(
                                modifier = Modifier
                                    .zIndex(1.0f)
                                    .align(Alignment.BottomStart)
                                    .padding(
                                        bottom = 16.dp,
                                        start = 16.dp,
                                    ),
                                location = exhibitionName,
                            )
                        }
                        RecordyVideoText(
                            nickname = nickname,
                            content = content,
                            isBookmark = isBookmark,
                            bookmarkCount = bookmarkCount,
                            onBookmarkClick = { onBookmarkClick(id) },
                            onNicknameClick = { onNickNameClick(uploaderId, isMine) },
                            onMoreClick = { onMoreClick(id, isMine) },
                        )
                    }
                }
            }
        }
        Icon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 46.dp, end = 16.dp, bottom = 16.dp)
                .customClickable(rippleEnabled = false) { onBackButtonClick() },
            painter = painterResource(id = R.drawable.ic_angle_left_24),
            contentDescription = "back",
            tint = RecordyTheme.colors.white,
        )
        if (state.showDeleteDialog) {
            RecordyDialog(
                graphicAsset = R.drawable.ic_alert_warning_80,
                title = "정말로 삭제하시겠어요?",
                subTitle = "해당 영상은 영구 삭제되며, 복구가 불가능해요.",
                negativeButtonLabel = "취소",
                positiveButtonLabel = "삭제",
                onDismissRequest = { onDeleteDialogDismissRequest() },
                onPositiveButtonClick = { onDialogDeleteButtonClick() },
            )
        }

        ReportBottomSheet(
            isMine = state.selectedVideoIsMine,
            id = state.selectedVideoId,
            onClickLinkCopy = { /*TODO*/ },
            onClickReport = reportVideo,
            onClickDelete = onDeleteClick,
            isShowBottomSheet = state.showReportBottomSheet,
            onDismiss = onBottomSheetDismiss,
        )
    }
}
