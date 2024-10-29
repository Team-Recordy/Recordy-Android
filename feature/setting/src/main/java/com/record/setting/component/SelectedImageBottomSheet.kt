package com.record.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.VideoFrameDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.record.designsystem.component.bottomsheet.RecordyBottomSheet
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.Background
import com.record.designsystem.theme.Gray03
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable
import com.record.ui.scroll.OnBottomReached
import com.record.upload.model.GalleryImage
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedImageBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    isSheetOpen: Boolean,
    onDismissRequest: () -> Unit,
    galleyImages: ImmutableList<GalleryImage>,
    isSelectedImage: (GalleryImage) -> Unit,
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
                TopNavigationBar(title = "사진 선택", enableGradation = true)
                Text(
                    text = "사진을 선택해주세요.",
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
                    items(galleyImages) { image ->
                        VideoThumbnail(
                            video = image,
                            onVideoSelected = {
                                onDismissRequest()
                                isSelectedImage(image)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VideoThumbnail(
    video: GalleryImage,
    onVideoSelected: (GalleryImage) -> Unit,
) {
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(VideoFrameDecoder.Factory())
            }
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
    }

    val request = remember(video.filepath) {
        ImageRequest.Builder(context)
            .data(video.filepath)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
    }

    Box(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp),
    ) {
        AsyncImage(
            model = request,
            imageLoader = imageLoader,
            contentDescription = "Video Thumbnail",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RectangleShape)
                .customClickable { onVideoSelected(video) },
        )
    }
}
