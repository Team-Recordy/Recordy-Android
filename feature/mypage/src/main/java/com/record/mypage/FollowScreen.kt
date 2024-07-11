package com.record.mypage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.theme.RecordyTheme
import com.record.model.UserData
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FollowRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: FollowViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is FollowSideEffect.Following -> {
                    Log.d("FollowRoute", "$sideEffect")
                }

                is FollowSideEffect.UnFollowing -> {
                    Log.d("FollowRoute", "$sideEffect")
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
            state = uiState,
            onClick = { user -> viewModel.toggleFollow(user) },
        )
    }
}

@Composable
fun FollowScreen(state: FollowState, onClick: (UserData) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(state.userList) { user ->
            UserDataContainer(
                user = user,
                onClick = onClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewFollowScreen() {
    val sampleState = FollowState()
    RecordyTheme {
        FollowScreen(state = sampleState, onClick = {})
    }
}
