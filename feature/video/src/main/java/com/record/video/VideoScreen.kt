package com.record.video

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.R
import com.record.designsystem.component.badge.RecordyLocationBadge
import com.record.designsystem.component.dialog.RecordyDialog
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.designsystem.component.videoplayer.RecordyVideoText
import com.record.designsystem.component.videoplayer.VideoPlayer
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import com.record.ui.scroll.onBottomReached
import com.record.video.component.VideoTypeToggle
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: VideoViewModel = hiltViewModel(),
    onShowSnackbar: (String, SnackBarType) -> Unit,
    navigateToMypage: () -> Unit,
    navigateToProfile: (Long) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.videos.size },
    )
    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is VideoSideEffect.NavigateToUserProfile -> {
                    navigateToProfile(sideEffect.id)
                }

                VideoSideEffect.NavigateToMypage -> {
                    navigateToMypage()
                }

                is VideoSideEffect.ShowNetworkErrorSnackbar -> {
                    onShowSnackbar(sideEffect.msg, SnackBarType.WARNING)
                }

                is VideoSideEffect.MovePage -> {
                    pagerState.scrollToPage(pagerState.currentPage - sideEffect.index)
                }
            }
        }
    }
    VideoScreen(
        pagerState = pagerState,
        modifier = modifier
            .padding(bottom = padding.calculateBottomPadding())
            .fillMaxSize(),
        state = state,
        onToggleClick = viewModel::updateToggleState,
        onDeleteClick = viewModel::showDeleteDialog,
        onBookmarkClick = viewModel::bookmark,
        onDeleteDialogDismissRequest = viewModel::dismissDeleteDialog,
        onError = viewModel::showNetworkErrorSnackbar,
        onPlayVideo = viewModel::watchVideo,
        onNicknameClick = viewModel::navigateToProfile,
        loadMoreVideos = viewModel::loadMoreVideos,
        onDialogDeleteButtonClick = viewModel::deleteVideo,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoScreen(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    state: VideoState,
    onToggleClick: () -> Unit,
    onDeleteClick: (Long) -> Unit,
    onBookmarkClick: (Long) -> Unit,
    onNicknameClick: (Long) -> Unit,
    onDeleteDialogDismissRequest: () -> Unit,
    onError: (String) -> Unit,
    onPlayVideo: (Long) -> Unit,
    loadMoreVideos: () -> Unit,
    onDialogDeleteButtonClick: (Long) -> Unit,
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
            beyondBoundsPageCount = 0,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            Box {
                if (page in state.videos.indices) {
                    state.videos[page].run {
                        VideoPlayer(id, videoUrl, pagerState, page, onError = onError, onPlayVideo = onPlayVideo)
                        RecordyLocationBadge(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(
                                    top = 102.dp,
                                    start = 16.dp,
                                ),
                            location = location,
                        )
                        RecordyVideoText(
                            nickname = nickname,
                            content = content,
                            isBookmark = isBookmark,
                            bookmarkCount = bookmarkCount,
                            isMyVideo = isMine,
                            onBookmarkClick = { onBookmarkClick(id) },
                            onDeleteClick = { onDeleteClick(id) },
                            onNicknameClick = { onNicknameClick(uploaderId) },
                        )
                    }
                }
            }
        }
        VideoTypeToggle(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp),
            isAll = state.isAll,
            onClick = onToggleClick,
        )
        if (state.showDeleteDialog) {
            RecordyDialog(
                graphicAsset = R.drawable.img_trashcan,
                title = "정말로 삭제하시겠어요?",
                subTitle = "해당 영상은 영구 삭제되며, 복구가 불가능해요.",
                negativeButtonLabel = "취소",
                positiveButtonLabel = "삭제",
                onDismissRequest = { onDeleteDialogDismissRequest() },
                onPositiveButtonClick = { onDialogDeleteButtonClick(state.deleteVideoId) },
            )
        }
    }
}
