package com.record.video.videodetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.record.model.VideoType
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun VideoDetailRoute(
    padding: PaddingValues,
    modifier: Modifier,
    viewModel: VideoDetailViewModel = hiltViewModel(),
    onShowSnackbar: (String, SnackBarType) -> Unit,
    navigateToUserProfile: (Int) -> Unit,
    navigateToMypage: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is VideoDetailSideEffect.NavigateToUserProfile -> { navigateToUserProfile(sideEffect.id) }
                is VideoDetailSideEffect.NavigateToMypage -> { navigateToMypage() }
                is VideoDetailSideEffect.ShowNetworkErrorSnackbar -> { onShowSnackbar(sideEffect.msg, SnackBarType.WARNING) }
            }
        }
    }
    VideoDetailScreen(
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
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoDetailScreen(
    modifier: Modifier = Modifier,
    state: VideoDetailState,
    onDeleteClick: (Int) -> Unit,
    onBookmarkClick: (Int) -> Unit,
    onDeleteDialogDismissRequest: () -> Unit,
    onNickNameClick: (Int) -> Unit,
    onError: (String) -> Unit,
    onPlayVideo: (Int) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.videos.size },
    )
    Box(
        modifier = modifier,
    ) {
        VerticalPager(
            state = pagerState,
            beyondBoundsPageCount = 1,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            Box {
                if (page in state.videos.indices) {
                    state.videos[page].run {
                        VideoPlayer(id.toInt(), videoUri, pagerState, page, onError = onError, onPlayVideo = onPlayVideo)
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
                            nickname = userName,
                            content = content,
                            isBookmark = isBookmark,
                            bookmarkCount = 123,
                            isMyVideo = state.videoType == VideoType.MY,
                            onBookmarkClick = { onBookmarkClick(id.toInt()) },
                            onDeleteClick = { onDeleteClick(id.toInt()) },
                            onNicknameClick = { onNickNameClick(id.toInt()) },
                        )
                    }
                }
            }
        }
        if (state.showDeleteDialog) {
            RecordyDialog(
                graphicAsset = R.drawable.img_trashcan,
                title = "정말로 삭제하시겠어요?",
                subTitle = "해당 영상은 영구 삭제되며, 복구가 불가능해요.",
                negativeButtonLabel = "취소",
                positiveButtonLabel = "삭제",
                onDismissRequest = { onDeleteDialogDismissRequest() },
                onPositiveButtonClick = {},
            )
        }
    }
}
