package com.record.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.record.designsystem.component.RecordyVideoThumbnail
import com.record.designsystem.component.button.FollowButton
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.RecordyTheme
import com.record.model.SampleData

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RecordyTheme.colors.background)
    ) {
        TopNavigationBar(
            title = "프로필",
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(vertical = 10.dp)
                .padding(bottom = 24.dp)
        ) {
            AsyncImage(
                model = uiState.user.profileImage,
                contentDescription = "profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = uiState.user.name,
                    style = RecordyTheme.typography.subtitle,
                    color = RecordyTheme.colors.white,
                )
                Spacer(modifier = Modifier.height(4.dp))

                BuildFollowerFollowingRow(
                    followerNum = uiState.followerNum,
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            FollowButton(
                isFollowing = uiState.user.isFollowing,
                onClick = { viewModel.toggleFollow(user = uiState.user) },
            )
            Spacer(modifier = Modifier.size(16.dp))
        }

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

@Composable
private fun BuildFollowerFollowingRow(
    followerNum: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row {
            Text(
                text = formatNumber(followerNum),
                style = RecordyTheme.typography.body2M,
                color = RecordyTheme.colors.white,
            )
            Text(
                text = " 명의 팔로워",
                style = RecordyTheme.typography.body2M,
                color = RecordyTheme.colors.gray03,
            )
        }
    }
}

fun formatNumber(number: Int): String {
    return when {
        number >= 10000 -> "${number / 10000}.${(number % 10000) / 1000}만"
        number >= 1000 -> "${number / 1000}.${(number % 1000) / 100}천"
        else -> number.toString()
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    RecordyTheme {
        ProfileScreen(padding = PaddingValues(0.dp))
    }
}
