package com.viskit.upload

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.viskit.designsystem.R
import com.viskit.designsystem.component.button.RecordyButton
import com.viskit.designsystem.component.button.RecordyImgButton
import com.viskit.designsystem.component.dialog.RecordyDialog
import com.viskit.designsystem.component.navbar.TopNavigationBar
import com.viskit.designsystem.component.snackbar.SnackBarType
import com.viskit.designsystem.component.textfield.RecordyBasicTextField
import com.viskit.designsystem.component.textfield.RecordyBasicTextField2
import com.viskit.designsystem.theme.RecordyTheme
import com.viskit.ui.extension.customClickable
import com.viskit.ui.lifecycle.LaunchedEffectWithLifecycle
import com.viskit.upload.component.bottomsheet.DefinedContentBottomSheet
import com.viskit.upload.component.bottomsheet.SelectedVideoBottomSheet
import com.viskit.upload.model.GalleryImage
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun VideoPickerRoute(
    paddingValues: PaddingValues,
    viewModel: UploadViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateToSearchPlace: () -> Unit,
    onShowSnackBar: (String, SnackBarType) -> Unit,
    id: String,
    address: String,
    name: String,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val locationFocusRequester = remember { FocusRequester() }
    val contentFocusRequester = remember { FocusRequester() }

    BackHandler(true) {
        coroutineScope.launch {
            viewModel.showExitUploadDialog()
        }
    }

    LaunchedEffectWithLifecycle {
        viewModel.setSelectedPlace(id, address, name)
        viewModel.getKeyWordList()
    }

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is UploadSideEffect.PopBackStack -> popBackStack()

                is UploadSideEffect.ShowSnackBar -> {
                    onShowSnackBar(sideEffect.msg, sideEffect.type)
                }

                is UploadSideEffect.FocusLocation -> {
                    awaitFrame()
                    locationFocusRequester.requestFocus()
                }

                is UploadSideEffect.FocusContent -> {
                    awaitFrame()
                    contentFocusRequester.requestFocus()
                }

                is UploadSideEffect.NavigateToSearchPlace -> {
                    navigateToSearchPlace()
                }
            }
        }
    }

    VideoPickerScreen(
        state = state,
        onClickVideo = viewModel::setVideo,
        onClickUpload = viewModel::upload,
        locationFocusRequester = locationFocusRequester,
        contentFocusRequester = locationFocusRequester,
        updateLocationTextField = viewModel::updateLocationTextField,
        showShouldShowRationaleDialog = viewModel::showShouldShowRationaleDialog,
        hideExitUploadDialog = viewModel::hideUploadDialog,
        showExitUploadDialog = viewModel::showExitUploadDialog,
        updateContentTextField = viewModel::updateContentTextField,
        showIsSelectedVideoSheetOpen = viewModel::showIsSelectedVideoSheetOpen,
        hideIsSelectedVideoSheetOpen = viewModel::hideIsSelectedVideoSheetOpen,
        showIsSelectedDefinedContentSheetOpen = viewModel::showIsSelectedDefinedContentSheetOpen,
        hideIsSelectedDefinedContentSheetOpen = viewModel::hideIsSelectedDefinedContentSheetOpen,
        showSnackBar = viewModel::makeSnackBar,
        onClickBackStack = viewModel::popBackStack,
        onLoadMore = viewModel::onLoadMore,
        navigateToSearchPlace = viewModel::navigateToSearchPlace,
    )
}

@OptIn(
    ExperimentalPermissionsApi::class,
    ExperimentalMaterial3Api::class,
)
@Composable
fun VideoPickerScreen(
    modifier: Modifier = Modifier,
    state: UploadState = UploadState(),
    onClickVideo: (GalleryImage) -> Unit,
    onClickUpload: () -> Unit,
    showShouldShowRationaleDialog: () -> Unit = {},
    hideExitUploadDialog: () -> Unit = {},
    showExitUploadDialog: () -> Unit = {},
    showIsSelectedVideoSheetOpen: () -> Unit = {},
    hideIsSelectedVideoSheetOpen: () -> Unit = {},
    showIsSelectedDefinedContentSheetOpen: () -> Unit = {},
    hideIsSelectedDefinedContentSheetOpen: () -> Unit = {},
    locationFocusRequester: FocusRequester = remember { FocusRequester() },
    contentFocusRequester: FocusRequester = remember { FocusRequester() },
    updateContentTextField: (String) -> Unit = {},
    updateLocationTextField: (String) -> Unit = {},
    showSnackBar: () -> Unit = {},
    onClickBackStack: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    navigateToSearchPlace: () -> Unit,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val cameraPermissionState = rememberPermissionState(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_VIDEO else Manifest.permission.READ_EXTERNAL_STORAGE,
    )

    var isGranted by remember { mutableStateOf(false) }

    val permissionState = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_VIDEO else Manifest.permission.READ_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED,
        )
    }
    LaunchedEffect(cameraPermissionState.status.isGranted) {
        if (cameraPermissionState.status.isGranted) {
            hideExitUploadDialog()
            onLoadMore()
        }
    }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (isGranted) {
            Timber.d("Permission granted")
        } else {
            Timber.d("Handle permission denial")
        }
    }
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(VideoFrameDecoder.Factory())
            }
            .crossfade(true)
            .build()
    }

    val painter = rememberAsyncImagePainter(
        model = state.video?.filepath,
        imageLoader = imageLoader,
    )

    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .background(RecordyTheme.colors.background)
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus() // 포커스 해제
                    },)
                },
        ) {
            TopNavigationBar(modifier = Modifier, title = "영상 업로드", showCloseButton = true, enableGradation = true, popBackStack = showExitUploadDialog)
            Text(
                text = "ⓘ 주제와 무관한 기록은 무통보로 삭제될 수 있습니다",
                color = RecordyTheme.colors.gray03,
                style = RecordyTheme.typography.caption2R,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .background(RecordyTheme.colors.gray10, shape = RoundedCornerShape(16.dp))
                        .customClickable(
                            onClick = {
                                if (cameraPermissionState.status.isGranted) {
                                    isGranted = true
                                    showIsSelectedVideoSheetOpen()
                                    return@customClickable
                                }
                                if (cameraPermissionState.status.shouldShowRationale) {
                                    showShouldShowRationaleDialog()
                                    return@customClickable
                                }
                                scope.launch {
                                    if (!permissionState.value) {
                                        requestPermissionLauncher.launch(
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_VIDEO else Manifest.permission.READ_EXTERNAL_STORAGE,
                                        )
                                    }
                                }
                            },
                        ),
                ) {
                    if (state.video == null) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(vertical = 115.dp, horizontal = 57.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_plus_25),
                                contentDescription = null,
                                modifier = Modifier.padding(bottom = 12.dp),
                            )
                            Text(
                                text = "영상 선택",
                                color = RecordyTheme.colors.gray01,
                                style = RecordyTheme.typography.subtitle,
                            )
                        }
                    } else {
                        Image(
                            modifier = Modifier
                                .width(158.dp)
                                .height(281.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .customClickable(
                                    onClick = showIsSelectedVideoSheetOpen,
                                ),
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 9.dp)
                                .width(90.dp)
                                .height(24.dp)
                                .background(
                                    color = Color(0xB3000000),
                                    shape = RoundedCornerShape(40.dp),
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "다른 영상 고르기",
                                color = Color(0xFFE2E5EC),
                                style = RecordyTheme.typography.caption2R,
                            )
                        }
                    }
                }
                RecordyBasicTextField(
                    placeholder = "나의 생각을 자유롭게 적어주세요!",
                    maxLines = 20,
                    maxLength = 300,
                    minHeight = 80.dp,
                    value = state.contentTextValue,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp)
                        .focusRequester(contentFocusRequester),
                    onValueChange = updateContentTextField,
                    highlightOnFocus = false,
                )
                RecordyImgButton(
                    modifier = Modifier.padding(16.dp),
                    icon = R.drawable.ic_move_18,
                    placeName = state.selectPlace.name,
                    text = "장소",
                    onClick = {
                        navigateToSearchPlace()
                    },
                )
                RecordyBasicTextField2(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .focusRequester(locationFocusRequester),
                    placeholder = "전시명",
                    placeholder2 = "전시명을 입력해 주세요.",
                    maxLines = 1,
                    maxLength = 20,
                    value = state.locationTextValue,
                    onValueChange = updateLocationTextField,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    highlightOnFocus = false,
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter),
        ) {
            RecordyButton(
                text = "업로드",
                enabled = state.selectPlace.name.isNotEmpty() && state.video != null,
                onClick = {
                    if (state.selectPlace.name.isNotEmpty() && state.video != null) {
                        onClickUpload()
//                compressVideo(context, state.video.uri,state.video.name, onSuccess = onSuccess)
                    }
                },
            )
        }
    }

    if (state.alertInfo.showDialog) {
        RecordyDialog(
            graphicAsset = R.drawable.ic_alert_warning_80,
            title = state.alertInfo.title,
            subTitle = state.alertInfo.subTitle,
            negativeButtonLabel = state.alertInfo.negativeButtonLabel,
            positiveButtonLabel = state.alertInfo.positiveButtonLabel,
            onDismissRequest = hideExitUploadDialog,
            onPositiveButtonClick = {
                if (state.isSystemAlert) {
                    if (cameraPermissionState.status.shouldShowRationale) {
                        openAppSettings(context)
                    }
                } else {
                    onClickBackStack()
                }
            },
        )
    }
    SelectedVideoBottomSheet(
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        isSheetOpen = state.isSelectedVideoSheetOpen,
        onDismissRequest = hideIsSelectedVideoSheetOpen,
        galleyVideos = state.galleryList,
        isSelectedVideo = onClickVideo,
        showSnackBar = showSnackBar,
        onLoadMore = onLoadMore,
        isLoading = state.isItemLoading,
    )

    DefinedContentBottomSheet(
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        isSheetOpen = state.isSelectedDefinedContentSheetOpen,
        onDismissRequest = hideIsSelectedDefinedContentSheetOpen,
        contentList = state.contentList,
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun VideoPickerScreenPreview() {
    RecordyTheme {
        VideoPickerScreen(
            onClickVideo = {},
            onClickUpload = {},
            navigateToSearchPlace = {},
        )
    }
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}
