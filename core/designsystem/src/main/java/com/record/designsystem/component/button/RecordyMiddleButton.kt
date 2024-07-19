package com.record.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.Black
import com.record.designsystem.theme.Main
import com.record.designsystem.theme.RecordyTheme
import timber.log.Timber

@Composable
fun RecordyMiddleButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = RecordyTheme.typography.button1,
    shape: Shape = RoundedCornerShape(8.dp),
    enabled: Boolean = true,
    clickable: Boolean = true,
    onClick: () -> Unit = {},
    backgroundColor: Color = Main,
    textColor: Color = RecordyTheme.colors.gray09,
    borderWidth: Dp = 0.dp,
    borderColor: Color = Color.Transparent,
    rippleColor: Color = White,
) {
    BasicButton(
        modifier = modifier
            .wrapContentWidth(),
        text = text,
        shape = shape,
        onClick = onClick,
        backgroundColor = if (enabled) backgroundColor else RecordyTheme.colors.gray08,
        rippleColor = rippleColor,
        textColor = if (enabled) textColor else RecordyTheme.colors.gray04,
        clickable = clickable,
        padding = PaddingValues(vertical = 12.dp, horizontal = 38.dp),
        textStyle = textStyle,
        borderWidth = borderWidth,
        borderColor = borderColor,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun RecordyMiddleButtonPreview() {
    RecordyTheme {
        RecordyTheme {
            Column(
                modifier = Modifier
                    .background(Black)
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                RecordyMiddleButton(
                    text = "키워드",
                    onClick = { Timber.d("basic key word") },
                )

                RecordyMiddleButton(
                    text = "키워드",
                    enabled = false,
                    onClick = { Timber.d("basic key word") },
                )
            }
        }
    }
}
