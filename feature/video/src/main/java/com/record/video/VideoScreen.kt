package com.record.video

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.component.badge.RecordyLocationBadge
import com.record.designsystem.component.videoplayer.RecordyVideoText
import com.record.designsystem.component.videoplayer.VideoPlayer
import com.record.video.component.VideoTypeToggle

@Composable
fun VideoRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: VideoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isAll by remember {
        mutableStateOf(true)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = padding.calculateBottomPadding()),
    ) {
        VideoScreen(
            state = uiState,
        )
        VideoTypeToggle(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp),
            isAll = isAll,
            onClick = { isAll = !isAll },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoScreen(
    state: VideoState,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.videos.size },
    )

    VerticalPager(
        state = pagerState,
        beyondBoundsPageCount = 1,
        modifier = Modifier.fillMaxSize(),
    ) { page ->
        Box {
            if (page in state.videos.indices) {
                VideoPlayer(state.videos[page].videoUri, pagerState, page)
            }
            RecordyLocationBadge(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        top = 102.dp,
                        start = 16.dp,
                    ),
                location = "서울특별시 강남구 테헤란로 33",
            )
            RecordyVideoText(
                nickname = "우상욱",
                content =
                "아주아주아주아주 긴 글 아주아주아주아주 긴 글 " +
                    "아주아주아주아주 긴 글 아주아주아주아주 긴 글 " +
                    "아주아주아주아주 긴 글 아주아주아주아주 긴 글 " +
                    "아주아주아주아주 긴 글 아주아주아주아주 긴 글 " +
                    "아주아주아주아주 긴 글 아주아주아주아주 긴 글 " +
                    "아주아주아주아주 긴 글 아주아주아주아주 긴 글 " +
                    "아주아주아주아주 긴 글 아주아주아주아주 긴 글 " +
                    "아주아주아주아주 긴 글 아주아주아주아주 긴 글 " +
                    "아주아주아주아주 긴 글 아주아주아주아주 긴 글 " +
                    "아주아주아주아주 긴 글 아주아주아주아주 긴 글 " +
                    "아주아주아주아주 긴 글 아주아주아주아주 긴 글 " +
                    "아주아주아주아주 긴 글 아주아주아주아주 긴 글 " +
                    "아주아주아주아주 긴 글 아주아주아주아주 긴 글 " +
                    "아주아주아주아주 긴 글 아주아주아주아주 긴 글 " +
                    "아주아주아주아주 긴 글 아주아주아주아주 긴 글",
                isBookmark = false,
                bookmarkCount = 123,
                isMyVideo = true,
            )
        }
    }
}
