package com.record.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.record.designsystem.R
import com.record.designsystem.component.badge.RecordyLocationBadge
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.icon.ShadowIcon

@Composable
fun RecordyVideoThumbnail(
    modifier: Modifier = Modifier,
    imageUri: String = "",
    onClick: () -> Unit = {},
    onBookmarkClick: () -> Unit = {},
    isBookmarkable: Boolean = false,
    isBookmark: Boolean = false,
    location: String = "",
) {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(imageUri)
        .memoryCacheKey(imageUri)
        .diskCacheKey(imageUri)
        .build()
    var boxSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(9f / 16f)
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable {
                onClick()
            },
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = "thumbnail image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { layoutCoordinates ->
                    boxSize = layoutCoordinates.size
                }
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0x00FFFFFF), Color(0xFF000000)),
                        startY = boxSize.height.toFloat() * 0.8f,
                        endY = boxSize.height.toFloat() * 0.95f,
                    ),
                ),
        )
        if (isBookmarkable) {
            ShadowIcon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .clickable {
                        onBookmarkClick()
                    },
                resourceId = if (isBookmark) R.drawable.ic_bookmark_variant_24 else R.drawable.ic_bookmark_24,
                color = RecordyTheme.colors.gray01,
                contentDescription = "bookmark",
            )
        }
        RecordyLocationBadge(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 12.dp)
                .align(Alignment.BottomStart),
            location = location,
            isTransparent = true,
        )
    }
}

@Preview
@Composable
fun PreviewRecordyVideoThumbnail() {
    RecordyTheme {
        RecordyVideoThumbnail(imageUri = "https://mixkit.imgix.net/videos/preview/mixkit-red-frog-on-a-log-1487-0.jpg")
    }
}
