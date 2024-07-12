package com.record.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.record.designsystem.component.RecordyVideoThumbnail
import com.record.designsystem.component.container.UserDataContainer
import com.record.designsystem.theme.RecordyTheme
import com.record.video.SampleData

@Composable
fun ProfileRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    ProfileScreen(
        padding = padding,
        modifier = modifier,
    )
}

@Composable
fun ProfileScreen(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        UserDataContainer(
            user = uiState.user,
            onClick = { user ->
                viewModel.toggleFollow(user)
            },
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .padding(bottom = padding.calculateBottomPadding()),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(SampleData.sampleVideos) { item ->
                RecordyVideoThumbnail(
                    imageUri = item.previewUri,
                    isBookmarkable = true,
                    isBookmark = false,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    RecordyTheme {
        ProfileScreen(padding = PaddingValues(0.dp))
    }
}
