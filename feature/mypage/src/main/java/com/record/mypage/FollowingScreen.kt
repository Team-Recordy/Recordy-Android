package com.record.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
        if (uiState.followingList.isEmpty()) {
            EmptyFollowingScreen()
        } else {
            FollowScreen(
                followingList = uiState.followingList,
                onClick = { user ->
                    viewModel.toggleFollow(user)
                },
            )
        }
    }
}

@Composable
fun EmptyFollowingScreen() {
    UserDataContainer(
        user = UserData(
            id = 0,
            profileImageResId = com.record.designsystem.R.drawable.img_profile,
            name = "유영",
            isFollowing = false,
        ),
        onClick = {},
        showFollowButton = false,
    )
}
