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
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.component.videoplayer.VideoPlayer

@Composable
fun VideoRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: VideoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        VideoScreen(
            state = uiState,
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
        if (page in state.videos.indices) {
            VideoPlayer(state.videos[page].videoUri, pagerState, page)
        }
    }
}
