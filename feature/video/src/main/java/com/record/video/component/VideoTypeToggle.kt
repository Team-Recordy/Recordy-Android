package com.record.video.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.RecordyTheme

@Composable
fun VideoTypeToggle(
    modifier: Modifier = Modifier,
    isAll: Boolean = true,
    onClick: () -> Unit = {},
) {
    val toggleAlignment by animateDpAsState(if (isAll) 0.dp else 56.dp)

    Box(
        modifier = modifier
            .background(
                color = RecordyTheme.colors.black30,
                shape = RoundedCornerShape(40.dp),
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                onClick()
            },
    ) {
        Box(
            modifier = Modifier
                .width(68.dp + toggleAlignment)
                .height(32.dp)
                .padding(start = 2.dp + toggleAlignment, end = 2.dp, top = 2.dp, bottom = 2.dp)
                .background(
                    color = RecordyTheme.colors.gray08,
                    shape = RoundedCornerShape(40.dp),
                ),
        )
        Row(
            modifier = Modifier
                .width(124.dp)
                .height(32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                text = "전체",
                style = RecordyTheme.typography.body2L,
                color = RecordyTheme.colors.white,
                textAlign = TextAlign.Center,
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                text = "팔로잉",
                style = RecordyTheme.typography.body2L,
                color = RecordyTheme.colors.white,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
fun PreviewVideoTypeToggle() {
    RecordyTheme {
        VideoTypeToggle()
    }
}
