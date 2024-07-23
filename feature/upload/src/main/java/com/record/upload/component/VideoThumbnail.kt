package com.record.upload.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.VideoFrameDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.record.designsystem.theme.White
import com.record.ui.extension.customClickable
import com.record.upload.extension.formatDuration
import com.record.upload.model.GalleryVideo

@Composable
fun VideoThumbnail(
    video: GalleryVideo,
    onVideoSelected: (GalleryVideo) -> Unit,
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
        Text(
            text = formatDuration(video.duration),
            fontSize = 12.sp,
            color = White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 4.dp, end = 6.dp),
            textAlign = TextAlign.End,
        )
    }
}
