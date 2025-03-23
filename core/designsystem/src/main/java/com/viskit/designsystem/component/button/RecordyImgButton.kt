package com.viskit.designsystem.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viskit.designsystem.R
import com.viskit.designsystem.theme.Black
import com.viskit.designsystem.theme.RecordyTheme
import com.viskit.ui.extension.customClickable
import timber.log.Timber

@Composable
fun RecordyImgButton(
    modifier: Modifier = Modifier,
    text: String,
    placeName: String = "",
    textStyle: TextStyle = RecordyTheme.typography.body2M,
    shape: Shape = RoundedCornerShape(8.dp),
    onClick: () -> Unit = {},
    backgroundColor: Color = RecordyTheme.colors.gray10,
    textColor: Color = RecordyTheme.colors.gray06,
    @DrawableRes icon: Int?,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(color = backgroundColor)
            .border(width = 1.dp, color = backgroundColor, shape = shape)
            .padding(vertical = 14.dp, horizontal = 18.dp)
            .customClickable(rippleEnabled = false, onClick = { onClick() }),
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
        ) {
            Text(
                text = placeName,
                style = textStyle,
                color = if (placeName.isNotEmpty()) RecordyTheme.colors.gray01 else textColor,
                modifier = Modifier.padding(end = 4.dp),
            )
            if (icon != null) {
                Image(
                    modifier = Modifier
                        .padding(end = 4.dp),
                    painter = painterResource(id = icon),
                    contentDescription = null,
                )
            }
        }
        Text(
            text = text,
            style = textStyle,
            color = if (placeName.isNotEmpty()) RecordyTheme.colors.gray01 else textColor,
            modifier = Modifier.align(Alignment.CenterStart),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun RecordyImgButtonPreview() {
    RecordyTheme {
        RecordyTheme {
            Column(
                modifier = Modifier
                    .background(Black)
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                RecordyImgButton(
                    icon = R.drawable.ic_move_18,
                    text = "장소",
                    placeName = "as",
                    onClick = { Timber.d("basic key word") },
                )
                RecordyImgButton(
                    icon = R.drawable.ic_move_18,
                    text = "장소",
                    onClick = { Timber.d("basic key word") },
                )
            }
        }
    }
}
