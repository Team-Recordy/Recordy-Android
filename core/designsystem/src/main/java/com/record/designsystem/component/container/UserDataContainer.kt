package com.record.designsystem.component.container

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.record.designsystem.component.button.FollowButton
import com.record.designsystem.theme.RecordyTheme
import com.record.model.UserData

@Composable
fun UserDataContainer(
    user: UserData,
    onClick: (UserData) -> Unit,
    showFollowButton: Boolean = true,
) {
    val profileImage = user.profileImage
    val profileImageResId = user.profileImageResId

    val painter = when {
        profileImage != null -> rememberAsyncImagePainter(profileImage)
        profileImageResId != null -> painterResource(id = profileImageResId)
        else -> painterResource(id = com.record.designsystem.R.drawable.img_profile)
    }

    Row(
        modifier = Modifier
            .background(RecordyTheme.colors.background)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 16.dp)
                .size(54.dp)
                .clip(CircleShape),
        )
        Text(
            text = user.name,
            style = RecordyTheme.typography.body2B,
            color = RecordyTheme.colors.white,
        )
        Spacer(modifier = Modifier.weight(1f))

        if (showFollowButton) {
            FollowButton(
                isFollowing = user.isFollowing,
                onClick = { onClick(user) },
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDataContainerPreview() {
    val sampleUser = UserData(
        id = 1,
        profileImage = "https://picsum.photos/id/200/60",
        name = "John Doe",
        isFollowing = false,
    )
    RecordyTheme {
        UserDataContainer(user = sampleUser, onClick = {})
    }
}
