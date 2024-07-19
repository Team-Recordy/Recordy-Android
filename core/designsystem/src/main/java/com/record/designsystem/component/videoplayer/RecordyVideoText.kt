package com.record.designsystem.component.videoplayer

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable

@Composable
fun RecordyVideoText(
    modifier: Modifier = Modifier,
    nickname: String = "",
    content: String = "",
    isBookmark: Boolean = false,
    bookmarkCount: Int = 0,
    isMyVideo: Boolean = false,
    onNicknameClick: () -> Unit = {},
    onBookmarkClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val boxWidth = screenWidth * 2 / 3

    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    boxSize = layoutCoordinates.size
                }
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0x00FFFFFF), Color(0xFF000000)),
                        startY = boxSize.height.toFloat() * 0.1f,
                        endY = boxSize.height.toFloat() * 0.5f,
                    ),
                ),
        ) {
            Column(
                modifier = Modifier
                    .animateContentSize()
                    .width(boxWidth)
                    .padding(start = 16.dp),
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    modifier = Modifier
                        .customClickable { onNicknameClick() },
                    text = nickname,
                    style = RecordyTheme.typography.subtitle,
                    color = RecordyTheme.colors.white,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                        .height(if (expanded) 160.dp else 40.dp)
                        .verticalScroll(scrollState)
                        .clickable {
                            expanded = !expanded
                        },
                ) {
                    Text(
                        text = content,
                        maxLines = if (expanded) Int.MAX_VALUE else 2,
                        overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                        style = RecordyTheme.typography.body2L,
                        color = RecordyTheme.colors.gray01,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier
                    .customClickable(rippleEnabled = false) { onBookmarkClick() },
                painter = painterResource(
                    id = if (isBookmark) R.drawable.ic_bookmark_variant_40 else R.drawable.ic_bookmark_40,
                ),
                contentDescription = "bookmark",
                tint = RecordyTheme.colors.gray01,
            )
            Text(
                text = bookmarkCount.toString(),
                style = RecordyTheme.typography.body2M,
                color = RecordyTheme.colors.gray01,
            )
            Spacer(modifier = Modifier.height(if (isMyVideo) 16.dp else 20.dp))
            if (isMyVideo) {
                Icon(
                    modifier = Modifier
                        .customClickable(rippleEnabled = false) { onDeleteClick() },
                    painter = painterResource(
                        id = R.drawable.ic_cursor_delete_40,
                    ),
                    contentDescription = "delete",
                    tint = RecordyTheme.colors.gray01,
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
