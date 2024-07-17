package com.record.mypage.follow

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.record.designsystem.component.container.UserDataContainer
import com.record.model.UserData
import kotlinx.collections.immutable.ImmutableList
import com.record.ui.scroll.OnBottomReached

@Composable
fun FollowScreen(
    modifier: Modifier = Modifier,
    followingList: ImmutableList<UserData>? = null,
    followerList: ImmutableList<UserData>? = null,
    onClick: (UserData) -> Unit,
    navigateToProfile: (Int) -> Unit,
    loadMoreFollow: () -> Unit,
) {
    val userList = followingList?.toList() ?: followerList?.toList() ?: emptyList()
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(userList) { user ->
            UserDataContainer(
                user = user,
                onClick = onClick,
                navigateToProfile = navigateToProfile
            )
        }
    }

    listState.OnBottomReached(
        buffer = 10,
        onLoadMore = loadMoreFollow
    )
}
