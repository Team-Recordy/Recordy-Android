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
fun RecordyLocationBadge(
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
        if (location != null) {
            Text(
                modifier = Modifier
                    .padding(
                        top = 7.dp,
                        bottom = 7.dp,
                        start = 12.dp,
                    ),
                text = location,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = RecordyTheme.typography.caption1R,
                color = RecordyTheme.colors.white,
                textAlign = TextAlign.Center,
            )
        }
        Icon(
            modifier = Modifier
                .padding(
                    start = 4.dp,
                    top = 5.dp,
                    bottom = 5.dp,
                    end = 8.dp,
                ),
            painter = painterResource(id = R.drawable.ic_angle_16),
            contentDescription = "cursor",
            tint = RecordyTheme.colors.gray01,
        )
    }
}

@Preview
@Composable
fun PreviewLocationBadge() {
    RecordyTheme {
        RecordyLocationBadge(location = "강남구 테헤란로 123")
    }
}
