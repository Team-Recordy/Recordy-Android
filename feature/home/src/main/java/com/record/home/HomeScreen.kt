package com.record.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.record.designsystem.R
import com.record.designsystem.component.RecordyVideoThumbnail
import com.record.designsystem.component.dialog.RecordyDialog
import com.record.designsystem.theme.RecordyTheme
import com.record.model.VideoType
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import com.record.video.model.VideoData
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToVideoDetail: (VideoType, Long, String?, Long) -> Unit,
    navigateToUpload: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                HomeSideEffect.navigateToUpload -> navigateToUpload()
                is HomeSideEffect.navigateToVideo -> {
                    // navigateToVideoDetail(sideEffect.type, sideEffect.id, sideEffect.keyword, 0)
                }

                HomeSideEffect.launchSettingIntent -> TODO()
                is HomeSideEffect.navigateToDetail -> TODO()
            }
        }
    }
    HomeScreen(
        modifier = modifier.padding(bottom = padding.calculateBottomPadding()),
        state = state,
        showLocationPermissionDialog = viewModel::showLocationPermissionDialog,
        updateLocation = viewModel::updateLocation,
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    showLocationPermissionDialog: (Boolean) -> Unit,
    updateLocation: (Double, Double) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (isGranted) {
            val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

            // 위치 정보 요청
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return@rememberLauncherForActivityResult
            }
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    updateLocation(it.latitude, it.longitude)
                    Log.e("위치", "${it.latitude} ${it.longitude}")
                }
            }
            showLocationPermissionDialog(false)
        } else {
            showLocationPermissionDialog(true)
        }
    }

    LaunchedEffectWithLifecycle {
        launcher.launch(
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            item {
                Box {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                            .padding(top = 66.dp, bottom = 32.dp),
                        painter = painterResource(id = R.drawable.ic_viskit_logo),
                        tint = RecordyTheme.colors.viskitYellow500,
                        contentDescription = "logo",
                    )
                }
            }
            itemsIndexed(state.exhibitionList) { i, exhibition ->
                ExhibitionContatiner(exhibition, screenWidth)
            }
        }

        if (state.showLocationPermissionDialog) {
            RecordyDialog(
                graphicAsset = R.drawable.img_trashcan,
                title = "필수 권한 허용해 주세요",
                subTitle = "내 위치 기반 공간 추천을 위해\n사용자의 위치에 접근하도록 허용해 주세요.",
                negativeButtonLabel = "취소",
                positiveButtonLabel = "삭제",
                onDismissRequest = { },
                onPositiveButtonClick = { },
            )
        }
    }
}

@Composable
private fun ExhibitionContatiner(
    exhibition: Exhibition,
    screenWidth: Dp,
    onVideoClick: (Long, VideoType) -> Unit = { i, j -> },
    onBookmarkClick: (Long) -> Unit = {},
    videoType: VideoType = VideoType.RECENT,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .background(color = RecordyTheme.colors.gray10, shape = RoundedCornerShape(8.dp)),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = exhibition.location,
                    style = RecordyTheme.typography.caption1M,
                    color = RecordyTheme.colors.gray05,
                )
                Text(
                    text = exhibition.name,
                    style = RecordyTheme.typography.title3,
                    color = RecordyTheme.colors.gray01,
                )
                Text(
                    text = exhibition.exhibitionCount.toString(),
                    style = RecordyTheme.typography.body2SB,
                    color = RecordyTheme.colors.viskitYellow500,
                )
            }
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(16.dp),
                tint = RecordyTheme.colors.gray01,
                painter = painterResource(id = R.drawable.ic_angle_right_24),
                contentDescription = "next",
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item { Spacer(modifier = Modifier.width(4.dp)) }
            itemsIndexed(exhibition.userVideo) { index, videoData ->
                RecordyVideoThumbnail(
                    modifier = Modifier.width(screenWidth / 8 * 3),
                    imageUri = videoData.previewUrl,
                    location = videoData.location,
                    isBookmarkable = true,
                    isBookmark = videoData.isBookmark,
                    onClick = { onVideoClick(videoData.id, videoType) },
                    onBookmarkClick = { onBookmarkClick(videoData.id) },
                )
            }
            item { Spacer(modifier = Modifier.width(4.dp)) }
        }
    }
}

@Preview
@Composable
fun PreviewHome() {
    RecordyTheme {
        HomeScreen(
            state = HomeState(
                exhibitionList =
                listOf(
                    Exhibition(
                        "서울 종로구",
                        "국립현대미술관",
                        7,
                        userVideo = listOf(
                            VideoData(
                                bookmarkId = 1,
                                id = 1,
                                isBookmark = false,
                                bookmarkCount = 7,
                                content = "ㅎㅇ",
                                videoUrl = "ggg",
                                previewUrl = "ggg",
                                location = "korea",
                                uploaderId = 1,
                                nickname = "안녕",
                                isMine = false,
                            ),
                        ).toImmutableList(),
                    ),
                ).toImmutableList(),
            ),
            showLocationPermissionDialog = {},
            updateLocation = { i, j -> },
        )
    }
}
