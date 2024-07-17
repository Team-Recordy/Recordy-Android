package com.record.designsystem.component.container

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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.record.designsystem.component.button.FollowButton
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable
import com.record.user.model.User

@Composable
fun UserDataContainer(
    user: User,
    onClick: (User) -> Unit,
    navigateToProfile: (Long) -> Unit,
) {

    Row(
        modifier = Modifier
            .background(RecordyTheme.colors.background)
            .fillMaxWidth()
            .customClickable { navigateToProfile(user.id.toLong()) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = user.profileImageUri,
            contentDescription = "profile",
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 16.dp)
                .size(54.dp)
                .clip(CircleShape),
        )
        Text(
            text = user.nickname,
            style = RecordyTheme.typography.body2B,
            color = RecordyTheme.colors.white,
        )
        Spacer(modifier = Modifier.weight(1f))

        if (user.nickname != "유영") {
            FollowButton(
                isFollowing = user.isFollowing,
                onClick = { onClick(user) },
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}
