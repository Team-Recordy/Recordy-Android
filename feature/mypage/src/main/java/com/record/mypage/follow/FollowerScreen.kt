package com.record.mypage.follow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FollowerRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: FollowViewModel = hiltViewModel(),
    navigateToProfile: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
        if (uiState.followerList.isEmpty()) {
            EmptyFollowerScreen()
        } else {
            FollowScreen(
                followerList = uiState.followerList,
                onClick = { user ->
                    viewModel.toggleFollow(false, user)
                },
                navigateToProfile = viewModel::navigateToProfile
            )
        }
    }
}

@Composable
fun EmptyFollowerScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RecordyTheme.colors.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row {
                Spacer(modifier = Modifier.weight(13f))
                Image(
                    painter = painterResource(com.record.designsystem.R.drawable.img_no_follower),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(10f)
                        .aspectRatio(1f),
                )
                Spacer(modifier = Modifier.weight(13f))
            }

            Spacer(modifier = Modifier.size(32.dp))
            Text(
                text = "아직 팔로워가 없어요",
                style = RecordyTheme.typography.emptybody,
                color = RecordyTheme.colors.white,
            )
        }
    }
}

@Preview
@Composable
fun PreviewEmptyFollowerScreen() {
    RecordyTheme {
        EmptyFollowerScreen()
    }
}
