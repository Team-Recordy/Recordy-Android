package com.record.mypage.follow

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.record.designsystem.component.container.UserDataContainer
import com.record.designsystem.theme.RecordyTheme
import com.record.model.UserData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun FollowScreen(
    followingList: ImmutableList<UserData>? = null,
    followerList: ImmutableList<UserData>? = null,
    onClick: (UserData) -> Unit,
) {
    val userList = followingList?.toList() ?: followerList?.toList() ?: emptyList()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(userList) { user ->
            UserDataContainer(
                user = user,
                onClick = onClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFollowScreen() {
    val sampleUserList = listOf(
        UserData(id = 1, profileImage = "https://via.placeholder.com/150", name = "John Doe", isFollowing = false),
        UserData(id = 2, profileImage = "https://via.placeholder.com/150", name = "Jane Smith", isFollowing = true),
    ).toPersistentList()
    RecordyTheme {
        FollowScreen(
            followingList = sampleUserList,
            onClick = {},
        )
    }
}
