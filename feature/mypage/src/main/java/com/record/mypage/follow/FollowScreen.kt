package com.record.mypage.follow

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.record.designsystem.component.container.UserDataContainer
import com.record.ui.scroll.OnBottomReached
import com.record.user.model.User
import kotlinx.collections.immutable.ImmutableList

@Composable
fun FollowScreen(
    modifier: Modifier = Modifier,
    followList: ImmutableList<User>? = null,
    onClick: (User) -> Unit,
    navigateToProfile: (Long) -> Unit,
    loadMoreFollow: () -> Unit,
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        if (followList != null) {
            items(followList.size) { index ->
                UserDataContainer(
                    user = followList[index],
                    onClick = onClick,
                    navigateToProfile = navigateToProfile,
                )
            }
        }
    }

    listState.OnBottomReached(
        buffer = 10,
        onLoadMore = loadMoreFollow,
    )
}
