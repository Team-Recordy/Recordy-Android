package com.record.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.theme.RecordyTheme
import com.record.model.UserData
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FollowingRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: FollowViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
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
        FollowScreen(
            followingList = uiState.followingList,
            onClick = { user -> viewModel.toggleFollow(user) },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFollowingScreen() {
    val sampleState = FollowState(
        followingList = listOf(
            UserData(id = 1, profileImage = "https://via.placeholder.com/150", name = "John Doe", isFollowing = true),
            UserData(id = 2, profileImage = "https://via.placeholder.com/150", name = "Jane Smith", isFollowing = true)
        ).toPersistentList()
    )
    RecordyTheme {
        FollowScreen(
            followingList = sampleState.followingList,
            onClick = {}
        )
    }
}
