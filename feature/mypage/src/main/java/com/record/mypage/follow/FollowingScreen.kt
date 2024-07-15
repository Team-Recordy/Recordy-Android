package com.record.mypage.follow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.component.container.UserDataContainer
import com.record.designsystem.theme.RecordyTheme
import com.record.model.UserData
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FollowingRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: FollowViewModel = hiltViewModel(),
) {
    val followState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is FollowSideEffect.Following -> {
                }

                is FollowSideEffect.UnFollowing -> {
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(RecordyTheme.colors.background)
            .padding(padding),
    ) {
        DefaultProfileScreen(followState, viewModel::toggleFollow)
    }
}

@Composable
fun DefaultProfileScreen(followState: FollowState, onclickEvent: (UserData) -> Unit) {
    val defaultUser =
        UserData(
            id = 0,
            profileImageResId = com.record.designsystem.R.drawable.img_profile,
            name = "유영",
            isFollowing = false,
        )

    val sortedList = listOf(defaultUser) + followState.followingList

    LazyColumn {
        items(sortedList) { user ->
            UserDataContainer(
                user = user,
                onClick = { onclickEvent(user) },
                showFollowButton = user.name != "유영",
            )
        }
    }
}

@Preview
@Composable
fun DefaultProfileScreenPreview() {
    RecordyTheme {
        DefaultProfileScreen(FollowState(), onclickEvent = {})
    }
}
