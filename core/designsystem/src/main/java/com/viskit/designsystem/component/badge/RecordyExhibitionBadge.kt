package com.viskit.designsystem.component.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viskit.designsystem.R
import com.viskit.designsystem.theme.RecordyTheme

@Composable
fun RecordyExhibitionBadge(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    location: String? = null,
    isTransparent: Boolean = false,
) {
    Row(
        modifier = modifier
            .background(
                color = if (isTransparent) Color.Transparent else RecordyTheme.colors.black50,
                shape = shape,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    top = 5.dp,
                    bottom = 5.dp,
                    end = 4.dp,
                ),
            painter = painterResource(id = R.drawable.ic_cursor_16),
            contentDescription = "cursor",
            tint = RecordyTheme.colors.gray01,
        )
        if (location != null) {
            Text(
                modifier = Modifier
                    .padding(
                        top = 4.dp,
                        bottom = 4.dp,
                        end = 12.dp,
                    ),
                text = location,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = RecordyTheme.typography.caption2M,
                color = RecordyTheme.colors.white,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
fun PreviewExhibitionBadge() {
    RecordyTheme {
        RecordyExhibitionBadge(location = "강남구 테헤란로 123")
    }
}
