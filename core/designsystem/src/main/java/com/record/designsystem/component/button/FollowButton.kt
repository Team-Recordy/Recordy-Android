package com.record.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.RecordyTheme

@Composable
fun FollowButton(
    initialText: String
) {
    var buttonText by remember { mutableStateOf(initialText) }
    val colors = getButtonAndTextColor(buttonText)

    Button(
        onClick = {
            buttonText = if (buttonText == "팔로우") "팔로잉" else "팔로우"
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.first
        ),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
    ) {
        Text(
            text = buttonText,
            color = colors.second,
            style = RecordyTheme.typography.button2,
        )
    }
}

@Composable
fun getButtonAndTextColor(buttonText: String): Pair<Color, Color> {
    return if (buttonText == "팔로잉") {
        Pair(RecordyTheme.colors.gray08, RecordyTheme.colors.white)
    } else {
        Pair(RecordyTheme.colors.white, RecordyTheme.colors.gray08)
    }
}

@Preview(showBackground = true)
@Composable
fun FollowButtonPreview() {
    RecordyTheme {
        FollowButton(initialText = "팔로잉")
    }
}
