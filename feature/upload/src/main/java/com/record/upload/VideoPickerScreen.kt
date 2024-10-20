package com.record.upload

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import com.record.designsystem.R
import com.record.designsystem.component.button.RecordyButton
import com.record.designsystem.component.button.RecordyImgButton
import com.record.designsystem.component.dialog.RecordyDialog
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.designsystem.component.textfield.RecordyBasicTextField
import com.record.designsystem.theme.Background
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import com.record.upload.component.bottomsheet.DefinedContentBottomSheet
import com.record.upload.component.bottomsheet.SelectedVideoBottomSheet
import com.record.upload.model.GalleryVideo
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun VideoPickerRoute(
    paddingValues: PaddingValues,
    viewModel: UploadViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    onShowSnackBar: (String, SnackBarType) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val locationFocusRequester = remember { FocusRequester() }
    val contentFocusRequester = remember { FocusRequester() }

    BackHandler(true) {
        coroutineScope.launch {
            viewModel.showExitUploadDialog()
        }
    }

    LaunchedEffectWithLifecycle {
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
            }
        }
    }

    VideoPickerScreen(
        state = state,
        onClickContentChip = viewModel::setSelectedList,
        onClickVideo = viewModel::setVideo,
        onClickUpload = viewModel::upload,
        locationFocusRequester = locationFocusRequester,
        contentFocusRequester = locationFocusRequester,
        updateLocationTextField = viewModel::updateLocationTextField,
        showShouldShowRationaleDialog = viewModel::showShouldShowRationaleDialog,
        hideExitUploadDialog = viewModel::hideUploadDialog,
        updateContentTextField = viewModel::updateContentTextField,
        showIsSelectedVideoSheetOpen = viewModel::showIsSelectedVideoSheetOpen,
        hideIsSelectedVideoSheetOpen = viewModel::hideIsSelectedVideoSheetOpen,
        showIsSelectedDefinedContentSheetOpen = viewModel::showIsSelectedDefinedContentSheetOpen,
        hideIsSelectedDefinedContentSheetOpen = viewModel::hideIsSelectedDefinedContentSheetOpen,
        showSnackBar = viewModel::makeSnackBar,
        onClickBackStack = viewModel::popBackStack,
        onLoadMore = viewModel::onLoadMore,
    )
}

@OptIn(
    ExperimentalPermissionsApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
)
@Composable
fun VideoPickerScreen(
    modifier: Modifier = Modifier,
    state: UploadState = UploadState(),
    onClickContentChip: (List<String>) -> Unit,
    onClickVideo: (GalleryVideo) -> Unit,
    onClickUpload: () -> Unit,
    showShouldShowRationaleDialog: () -> Unit = {},
    hideExitUploadDialog: () -> Unit = {},
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
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val cameraPermissionState = rememberPermissionState(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_VIDEO else Manifest.permission.READ_EXTERNAL_STORAGE,
    )

    var isGranted by remember { mutableStateOf(false) }
    LaunchedEffect(isGranted) {
        onLoadMore()
    }
    val permissionState = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_VIDEO else Manifest.permission.READ_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED,
        )
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .customClickable {
                focusManager.clearFocus()
            },
    ) {
        TopNavigationBar(modifier = Modifier, title = "내용 작성", enableGradation = true)
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
                .weight(1f)
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
            )
            RecordyImgButton(
                modifier = Modifier.padding(16.dp),
                icon = R.drawable.ic_move_18,
                text = "장소",
                onClick = { Timber.d("basic key word") },
            )
            RecordyBasicTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .focusRequester(locationFocusRequester),
                placeholder = "전시명을 입력해 주세요.",
                maxLines = 1,
                maxLength = 20,
                value = state.locationTextValue,
                onValueChange = updateLocationTextField,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            )
        }

        Box(modifier = Modifier.padding(16.dp)) {
            RecordyButton(
                text = "다음",
                enabled = state.locationTextValue.isNotEmpty() && state.video != null,
                onClick = {
                    if (state.selectedList.isNotEmpty() && state.locationTextValue.isNotEmpty() && state.video != null) {
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
                if (cameraPermissionState.status.shouldShowRationale) {
                    openAppSettings(context)
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
        onClickDefinedContent = onClickContentChip,
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun VideoPickerScreenPreview() {
    RecordyTheme {
        VideoPickerScreen(
            onClickContentChip = {},
            onClickVideo = {},
            onClickUpload = {},
        )
    }
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}
