package com.record.designsystem.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.record.designsystem.R
import com.record.designsystem.theme.Black
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable
import timber.log.Timber

@Composable
fun RecordyImgButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = RecordyTheme.typography.body2M,
    shape: Shape = RoundedCornerShape(8.dp),
    onClick: () -> Unit = {},
    backgroundColor: Color = RecordyTheme.colors.gray10,
    textColor: Color = RecordyTheme.colors.gray06,
    @DrawableRes icon: Int,
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
        Image(modifier = Modifier.align(Alignment.CenterEnd).padding(end = 4.dp), painter = painterResource(id = icon), contentDescription = null)
        Text(
            text = text,
            style = textStyle,
            color = textColor,
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
                    text = "키워드",
                    onClick = { Timber.d("basic key word") },
                )
            }
        }
    }
}
