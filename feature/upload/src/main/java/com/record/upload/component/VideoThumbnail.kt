package com.record.upload.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import com.record.designsystem.theme.White
import com.record.ui.extension.customClickable
import com.record.upload.extension.GalleryVideo
import com.record.upload.extension.formatDuration

@Composable
fun VideoThumbnail(
    video: GalleryVideo,
    setVideo: (GalleryVideo) -> Unit,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(VideoFrameDecoder.Factory())
        }
        .crossfade(true)
        .build()

    val painter = rememberAsyncImagePainter(
        model = video.filepath,
        imageLoader = imageLoader,
    )
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp),
    ) {
        Image(
            painter = painter,
            contentDescription = "Video Thumbnail",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RectangleShape)
                .customClickable { setVideo(video) },
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
