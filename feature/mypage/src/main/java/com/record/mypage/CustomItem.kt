package com.record.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.record.designsystem.component.button.FollowButton
import com.record.designsystem.theme.RecordyTheme
import com.record.model.UserData

@Composable
fun CustomItem(user: UserData) {
    Row(
        modifier = Modifier
            .background(RecordyTheme.colors.background)
            .fillMaxWidth()
            .padding(vertical = 19.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = user.profileImage),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(54.dp)
                .clip(CircleShape)
                .background(RecordyTheme.colors.gray08)
        )
        Text(
            text = user.name,
            color = RecordyTheme.colors.gray01
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 19.dp)
        )
        FollowButton(
            initialText = "팔로잉"
        )
        Spacer(modifier = Modifier.padding(end = 16.dp))
    }
}

@Preview
@Composable
fun CustomItemPreview() {
    RecordyTheme {
        CustomItem(
            user = UserData(
                id = 0,
                profileImage = "https://via.placeholder.com/150",
                name = "test"
            )
        )
    }
}
