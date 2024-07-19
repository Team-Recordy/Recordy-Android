package com.record.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.record.designsystem.component.RecordyVideoThumbnail
import com.record.designsystem.component.button.FollowButton
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.RecordyTheme
import com.record.model.VideoType
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import com.record.ui.scroll.OnBottomReached
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToVideoDetail: (VideoType, Long, Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.initialData()
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is ProfileSideEffect.navigateToVideoDetail -> {
                    navigateToVideoDetail(sideEffect.type, sideEffect.id, sideEffect.userId)
                }
            }
        }
    }

    ProfileScreen(
        state = uiState,
        padding = padding,
        modifier = modifier,
        onLoadMore = viewModel::getVideos,
        onFollowClick = viewModel::toggleFollow,
        onBookmarkClick = viewModel::bookmark,
        onVideoClick = viewModel::navigateVideo,
    )
}

@Composable
fun ProfileScreen(
    state: ProfileState,
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    onLoadMore: () -> Unit,
    onFollowClick: () -> Unit,
    onBookmarkClick: (Long) -> Unit,
    onVideoClick: (VideoType, Long) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    lazyGridState.OnBottomReached {
        onLoadMore()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RecordyTheme.colors.background),
    ) {
        TopNavigationBar(
            title = "프로필",
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(vertical = 10.dp)
                .padding(bottom = 24.dp),
        ) {
            AsyncImage(
                model = state.profileImageUrl,
                contentDescription = "profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = state.nickname,
                    style = RecordyTheme.typography.subtitle,
                    color = RecordyTheme.colors.white,
                )
                Spacer(modifier = Modifier.height(4.dp))

                BuildFollowerFollowingRow(
                    followerNum = state.followerCount,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (state.nickname != "유영") {
                FollowButton(
                    isFollowing = state.isFollowing,
                    onClick = { onFollowClick() },
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }

        LazyVerticalGrid(
            state = lazyGridState,
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .padding(bottom = padding.calculateBottomPadding()),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    Text(
                        text = buildRecordCountText(state.recordCount),
                        style = RecordyTheme.typography.body2M,
                        color = RecordyTheme.colors.gray01,
                        modifier = Modifier
                            .padding(end = 16.dp, bottom = 8.dp),
                    )
                }
            }

            items(state.userVideos) { item ->
                RecordyVideoThumbnail(
                    imageUri = item.previewUrl,
                    isBookmarkable = true,
                    isBookmark = item.isBookmark,
                    location = item.location,
                    onClick = {
                        onVideoClick(VideoType.PROFILE, item.id)
                    },
                    onBookmarkClick = {
                        onBookmarkClick(item.id)
                    },
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

@Composable
fun buildRecordCountText(recordCount: Int): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = RecordyTheme.colors.white)) {
            append("• $recordCount")
        }
        withStyle(style = SpanStyle(color = RecordyTheme.colors.gray03)) {
            append(" 개의 기록")
        }
    }
}
