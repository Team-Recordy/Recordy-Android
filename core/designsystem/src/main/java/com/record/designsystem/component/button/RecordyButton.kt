package com.record.designsystem.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.RecordyTheme

@Composable
fun RecordyButton(
    text: String,
    containerColor: Color = RecordyTheme.colors.main,
    contentColor: Color = RecordyTheme.colors.gray09,
    textStyle: TextStyle = RecordyTheme.typography.button1,
    cornerShape: Dp = 12.dp,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = RecordyTheme.colors.gray08,
            disabledContentColor = RecordyTheme.colors.gray04,
        ),
        shape = RoundedCornerShape(cornerShape),
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = text,
            style = textStyle,
        )
    }
}
