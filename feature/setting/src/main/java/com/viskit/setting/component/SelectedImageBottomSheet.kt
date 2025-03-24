package com.viskit.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.VideoFrameDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.viskit.designsystem.R
import com.viskit.designsystem.component.bottomsheet.RecordyBottomSheet
import com.viskit.designsystem.theme.Background
import com.viskit.designsystem.theme.Gray03
import com.viskit.designsystem.theme.RecordyTheme
import com.viskit.ui.extension.customClickable
import com.viskit.ui.scroll.OnBottomReached
import com.viskit.upload.model.GalleryImage
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
    var selectedImage by remember { mutableStateOf<GalleryImage?>(null) }

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
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .background(RecordyTheme.colors.background)
                            .fillMaxWidth()
                            .padding(
                                top = 45.dp,
                                bottom = 15.dp,
                            ),
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "사진 선택",
                            color = RecordyTheme.colors.gray01,
                            style = RecordyTheme.typography.title3,
                        )
                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd),
                        ) {
                            Text(
                                modifier = Modifier.customClickable {
                                    if (selectedImage != null) {
                                        onDismissRequest()
                                        isSelectedImage(selectedImage!!)
                                    }
                                },
                                text = "완료",
                                color = if (selectedImage == null) RecordyTheme.colors.gray08 else RecordyTheme.colors.gray01,
                                style = RecordyTheme.typography.title3,
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                }
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
                            isSelected = selectedImage == image,
                            onVideoSelected = {
                                selectedImage = if (selectedImage == image) null else image
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
    isSelected: Boolean,
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
            .height(100.dp)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) RecordyTheme.colors.viskitYellow200 else androidx.compose.ui.graphics.Color.Transparent,
                shape = RectangleShape,
            ),

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
        Image(
            painter = painterResource(if (isSelected) R.drawable.img_selection_check else R.drawable.img_selection),
            contentDescription = "Selected Icon",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 6.dp, end = 6.dp)
                .size(16.dp),
        )
    }
}
