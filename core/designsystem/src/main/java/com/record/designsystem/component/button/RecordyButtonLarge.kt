package com.record.designsystem.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.RecordyTheme

@Composable
fun RecordyButtonLarge(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        enabled = enabled,

        colors = ButtonDefaults.buttonColors(
            containerColor = RecordyTheme.colors.main,
            contentColor = RecordyTheme.colors.gray09,
            disabledContainerColor = RecordyTheme.colors.gray08,
            disabledContentColor = RecordyTheme.colors.gray04,
        ),

        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = text,
            style = RecordyTheme.typography.button1,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun RecordyButtonPreview() {
    RecordyTheme {
        RecordyButtonLarge(
            text = "Click Me",
            enabled = true,
            onClick = {},
        )
    }
}
