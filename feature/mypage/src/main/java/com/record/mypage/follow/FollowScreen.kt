package com.record.mypage.follow

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.component.container.UserDataContainer
import com.record.model.UserData
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FollowScreen(
    followingList: ImmutableList<UserData>? = null,
    followerList: ImmutableList<UserData>? = null,
    onClick: (UserData) -> Unit,
    navigateToProfile: (Int) -> Unit,
) {
    val userList = followingList?.toList() ?: followerList?.toList() ?: emptyList()

    LazyColumn(
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
}
