package com.record.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.record.exhibition.model.Place
import com.record.model.VideoType
import com.record.ui.extension.customClickable
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import com.record.ui.scroll.OnBottomReached
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToVideoDetail: (VideoType, Long, Long) -> Unit,
    navigateToPlaceDetail: (Long) -> Unit,
    navigateToUpload: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                HomeSideEffect.navigateToUpload -> navigateToUpload()
                is HomeSideEffect.navigateToVideo -> {
                    navigateToVideoDetail(sideEffect.type, sideEffect.id, sideEffect.placeId)
                }

                HomeSideEffect.launchSettingIntent -> TODO()
                is HomeSideEffect.navigateToDetail -> {
                    navigateToPlaceDetail(sideEffect.id)
                }
            }
        }
    }
    HomeScreen(
        modifier = modifier.padding(bottom = padding.calculateBottomPadding()),
        state = state,
        showLocationPermissionDialog = viewModel::showLocationPermissionDialog,
        updateLocation = viewModel::updateLocation,
        getData = viewModel::getPlaces,
        resetData = viewModel::resetPlaces,
        navigateToDetail = viewModel::navigateToDetail,
        onVideoClick = viewModel::navigateToVideo,
        onBookmarkClick = viewModel::bookmark,
        updatePermissionGranted = viewModel::updatePermissionGranted,
        updateLocationSelected = viewModel::updateLocationSelected,
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    showLocationPermissionDialog: (Boolean) -> Unit,
    updateLocation: (Double, Double) -> Unit,
    resetData: () -> Unit,
    getData: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    onVideoClick: (VideoType, Long, Long) -> Unit,
    onBookmarkClick: (Long) -> Unit,
    updatePermissionGranted: (Boolean) -> Unit,
    updateLocationSelected: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (isGranted) {
            val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return@rememberLauncherForActivityResult
            }
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    updateLocation(it.latitude, it.longitude)
                    Log.e("위치", "${it.latitude} ${it.longitude}")
                    updatePermissionGranted(true)
                }
            }
            showLocationPermissionDialog(false)
        } else {
            showLocationPermissionDialog(true)
        }
    }
    val lazyColumnState = rememberLazyListState()
    lazyColumnState.OnBottomReached(buffer = 2) {
        getData()
    }

    LaunchedEffectWithLifecycle {
        launcher.launch(
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    }

    LaunchedEffectWithLifecycle(state.isPermissionGranted) {
        if (state.isPermissionGranted) {
            resetData()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            state = lazyColumnState,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row {
                        Icon(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .padding(top = 66.dp, bottom = 32.dp),
                            painter = painterResource(id = R.drawable.ic_viskit_logo),
                            tint = RecordyTheme.colors.viskitYellow500,
                            contentDescription = "logo",
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .padding(top = 66.dp, bottom = 32.dp)
                                .clickable {
                                    updateLocationSelected()
                                },
                            painter = painterResource(id = if (state.locationSelected) R.drawable.ic_location else R.drawable.ic_location_denied),
                            tint = RecordyTheme.colors.white,
                            contentDescription = "location",
                        )
                    }
                }
            }
            itemsIndexed(state.exhibitionList) { i, exhibition ->
                ExhibitionContatiner(
                    place = exhibition,
                    screenWidth = screenWidth,
                    onItemClick = navigateToDetail,
                    onVideoClick = onVideoClick,
                    onBookmarkClick = onBookmarkClick,
                )
            }
        }

        if (state.showLocationPermissionDialog) {
            RecordyDialog(
                graphicAsset = R.drawable.ic_alert_warning_80,
                title = "필수 권한 허용해 주세요",
                subTitle = "내 위치 기반 공간 추천을 위해\n사용자의 위치에 접근하도록 허용해 주세요.",
                negativeButtonLabel = "취소",
                positiveButtonLabel = "설정으로 가기",
                onDismissRequest = { },
                onPositiveButtonClick = {
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null),
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                },
            )
        }
    }
}

@Composable
private fun ExhibitionContatiner(
    modifier: Modifier = Modifier,
    place: Place,
    screenWidth: Dp,
    onItemClick: (Long) -> Unit = {},
    onVideoClick: (VideoType, Long, Long) -> Unit,
    onBookmarkClick: (Long) -> Unit = {},
) {
    Column(
        modifier = modifier
            .padding(vertical = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = if (place.exhibitionRecord.isNullOrEmpty()) 0.dp else 16.dp)
                .background(color = RecordyTheme.colors.gray10, shape = RoundedCornerShape(8.dp))
                .customClickable {
                    onItemClick(place.placeId.toLong())
                },
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                if (place.address.isNotBlank()) {
                    Text(
                        text = place.address,
                        style = RecordyTheme.typography.caption1M,
                        color = RecordyTheme.colors.gray05,
                    )
                }
                Text(
                    text = place.name,
                    style = RecordyTheme.typography.title3,
                    color = RecordyTheme.colors.gray01,
                )
                Row {
                    Text(
                        text = place.exhibitionCount.toString() + "개",
                        style = RecordyTheme.typography.body2SB,
                        color = RecordyTheme.colors.viskitYellow500,
                    )
                    Text(
                        text = "의 전시가 진행 중이에요.",
                        style = RecordyTheme.typography.body2SB,
                        color = RecordyTheme.colors.gray02,
                    )
                }
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
            if (place.exhibitionRecord != null) {
                itemsIndexed(place.exhibitionRecord!!) { index, videoData ->
                    RecordyVideoThumbnail(
                        modifier = Modifier.width(screenWidth / 8 * 3),
                        imageUri = videoData.previewUrl,
                        location = videoData.exhibitionName,
                        isBookmarkable = true,
                        isBookmark = videoData.isBookmark,
                        onClick = { onVideoClick(VideoType.PLACE, videoData.id, place.placeId.toLong()) },
                        onBookmarkClick = { onBookmarkClick(videoData.id) },
                    )
                }
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
                exhibitionList = emptyList<Place>().toImmutableList(),
            ),
            showLocationPermissionDialog = {},
            updateLocation = { i, j -> },
            getData = {},
            resetData = {},
            navigateToDetail = {},
            onVideoClick = { i, j, k -> },
            onBookmarkClick = {},
            updatePermissionGranted = {},
            updateLocationSelected ={},
        )
    }
}
