package com.record.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.RecordyTheme

@Composable
fun FollowButton(
    isFollowing: Boolean,
    onClick: () -> Unit
) {
    val colors = getButtonAndTextColor(isFollowing)

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.first
        ),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
    ) {
        Text(
            text = if (isFollowing) "팔로잉" else "팔로우",
            color = colors.second,
            style = RecordyTheme.typography.button2,
        )
    }
}

@Composable
fun getButtonAndTextColor(isFollowed: Boolean): Pair<Color, Color> {
    return if (isFollowed) {
        Pair(RecordyTheme.colors.gray08, RecordyTheme.colors.white)
    } else {
        Pair(RecordyTheme.colors.white, RecordyTheme.colors.gray08)
    }
}

@Preview(showBackground = true)
@Composable
fun FollowButtonPreview() {
    RecordyTheme {
        FollowButton(
            isFollowing = false,
            onClick = {}
        )
    }
}
