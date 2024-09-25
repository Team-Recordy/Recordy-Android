package com.record.upload.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.record.designsystem.R
import com.record.designsystem.component.bottomsheet.RecordyBottomSheet
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.Background
import com.record.designsystem.theme.Gray03
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable
import com.record.ui.scroll.OnBottomReached
import com.record.upload.component.VideoThumbnail
import com.record.upload.model.GalleryVideo
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedVideoBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    isSheetOpen: Boolean,
    onDismissRequest: () -> Unit,
    galleyVideos: ImmutableList<GalleryVideo>,
    isSelectedVideo: (GalleryVideo) -> Unit,
    showSnackBar: () -> Unit,
    onLoadMore: () -> Unit,
    isLoading: Boolean,
) {
    val lazyGridState = rememberLazyGridState()

    lazyGridState.OnBottomReached {
        if (!isLoading) {
            onLoadMore()
        }
    }
    RecordyBottomSheet(
        isSheetOpen = isSheetOpen,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background),
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter),
            ) {
                TopNavigationBar(title = "영상 선택", enableGradation = true)
                Text(
                    text = "ⓘ 1080p 이하의 최대 15초 영상을 올려주세요.",
                    color = Gray03,
                    style = RecordyTheme.typography.caption2R,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 22.dp),
                    textAlign = TextAlign.Center,
                )
                LazyVerticalGrid(
                    state = lazyGridState,
                    columns = GridCells.Fixed(4),
                    horizontalArrangement = Arrangement.spacedBy(1.dp),
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                ) {
                    items(galleyVideos) { video ->
                        VideoThumbnail(
                            video = video,
                            onVideoSelected = {
                                if (video.duration > 15000) {
                                    showSnackBar()
                                } else {
                                    onDismissRequest()
                                    isSelectedVideo(video)
                                }
                            },
                        )
                    }
                    if (isLoading) {
                        item(span = { GridItemSpan(4) }) {
                            LoadingLottie()
                        }
                    }
                }
            }
        }
    }
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
            .fillMaxWidth()
            .height(48.dp)
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
