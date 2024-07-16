package com.record.mypage.follow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.theme.RecordyTheme
import com.record.model.UserData
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FollowingRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: FollowViewModel = hiltViewModel(),
    navigateToProfile: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val defaultUser = UserData(
        id = 0,
        profileImageResId = com.record.designsystem.R.drawable.img_profile,
        name = "유영",
        isFollowing = false,
    )
    val updatedFollowingList = listOf(defaultUser) + (uiState.followingList)

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is FollowSideEffect.NavigateToUserProfile -> {
                    navigateToProfile(sideEffect.id)
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
        FollowScreen(
            followingList = updatedFollowingList.toPersistentList(),
            onClick = { user ->
                viewModel.toggleFollow(true, user)
            },
            navigateToProfile = viewModel::navigateToProfile
        )
    }
}

@Preview
@Composable
fun FollowingRoutePreview() {
    RecordyTheme {
        FollowingRoute(
            padding = PaddingValues(),
            navigateToProfile = { },
        )
    }
}
