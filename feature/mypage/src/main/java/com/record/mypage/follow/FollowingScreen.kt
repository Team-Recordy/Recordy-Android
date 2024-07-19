package com.record.mypage.follow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FollowingRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: FollowViewModel = hiltViewModel(),
    navigateToProfile: (Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.getFollowingList()
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is FollowSideEffect.NavigateToUserProfile -> {
                    navigateToProfile(sideEffect.id)
                }
            }
        }
    }

    val sortedFollowingList = uiState.followingList.sortedByDescending { it.nickname == "유영" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RecordyTheme.colors.background),
    ) {
        TopNavigationBar(
            title = "팔로잉",
            modifier = Modifier.fillMaxWidth(),
        )

        FollowScreen(
            modifier = Modifier
                .fillMaxSize(),
            followList = sortedFollowingList.toImmutableList(),
            onClick = { user ->
                viewModel.toggleFollow(true, user)
            },
            navigateToProfile = viewModel::navigateToProfile,
            loadMoreFollow = viewModel::loadMoreFollowing,
        )
    }
}
