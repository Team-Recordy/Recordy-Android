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
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import com.record.designsystem.component.button.RecordyChipButton
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
import com.record.upload.extension.GalleryVideo
import com.record.upload.extension.getAllVideos
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

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
        viewModel.getPresignedUrl()
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
        uploadVideoS3Bucket = {
//            viewModel.uploadVideoToS3Bucket(context,it)
            viewModel.uploadVideoToS3Bucket(it)
        },
        locationFocusRequester = locationFocusRequester,
        contentFocusRequester = locationFocusRequester,
        updateLocationTextField = viewModel::updateLocationTextField,
        showShouldShowRationaleDialog = viewModel::showShouldShowRationaleDialog,
        updateContentTextField = viewModel::updateContentTextField,
        hideShouldShowRationaleDialog = viewModel::hideShouldShowRationaleDialog,
        hideExitUploadDialog = viewModel::hideExitUploadDialog,
        showIsSelectedVideoSheetOpen = viewModel::showIsSelectedVideoSheetOpen,
        hideIsSelectedVideoSheetOpen = viewModel::hideIsSelectedVideoSheetOpen,
        showIsSelectedDefinedContentSheetOpen = viewModel::showIsSelectedDefinedContentSheetOpen,
        hideIsSelectedDefinedContentSheetOpen = viewModel::hideIsSelectedDefinedContentSheetOpen,
        showSnackBar = viewModel::makeSnackBar,
        onClickBackStack = viewModel::popBackStack,
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
    uploadVideoS3Bucket: (File) -> Unit,
    showShouldShowRationaleDialog: () -> Unit = {},
    hideShouldShowRationaleDialog: () -> Unit = {},
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
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val cameraPermissionState = rememberPermissionState(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_VIDEO else Manifest.permission.READ_EXTERNAL_STORAGE,
    )
    var exampleVideoList by remember { mutableStateOf<List<GalleryVideo>>(emptyList()) }

    var isGranted by remember { mutableStateOf(false) }
    LaunchedEffect(isGranted) {
        exampleVideoList = getAllVideos(10, null, context)
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
            style = RecordyTheme.typography.caption2,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = "영상",
                color = RecordyTheme.colors.white,
                style = RecordyTheme.typography.subtitle,
                modifier = Modifier.padding(top = 20.dp, bottom = 9.dp),
            )
            Box(
                modifier = Modifier
                    .background(RecordyTheme.colors.gray08, shape = RoundedCornerShape(16.dp))
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
                            color = RecordyTheme.colors.white,
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
        }
        Text(
            text = "키워드",
            color = RecordyTheme.colors.white,
            style = RecordyTheme.typography.subtitle,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 22.dp, bottom = 12.dp),
        )
        FlowRow(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(start = 16.dp, end = 12.dp)
                .customClickable(onClick = showIsSelectedDefinedContentSheetOpen),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(9.dp),
        ) {
            state.selectedList.forEach {
                RecordyChipButton(
                    text = it,
                    isActive = true,
                    isCheckSmall = false,
                    onClick = { },
                )
            }
            Row(
                modifier = Modifier
                    .background(RecordyTheme.colors.gray08, shape = RoundedCornerShape(30.dp))
                    .padding(
                        start = 8.dp,
                        top = 10.dp,
                        end = 12.dp,
                        bottom = 10.dp,
                    ),

                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_plus_16),
                    contentDescription = null,
                )
                Text(
                    text = "키워드",
                    color = RecordyTheme.colors.gray03,
                    style = RecordyTheme.typography.body2M,
                )
            }
        }
        Text(
            text = "위치",
            color = RecordyTheme.colors.white,
            style = RecordyTheme.typography.subtitle,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 20.dp, bottom = 12.dp),
        )
        RecordyBasicTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .focusRequester(locationFocusRequester),
            placeholder = "영상 속 위치는 어디인가요?",
            maxLines = 1,
            maxLength = 20,
            value = state.locationTextValue,
            onValueChange = updateLocationTextField,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        )
        Text(
            text = "내용",
            color = RecordyTheme.colors.white,
            style = RecordyTheme.typography.subtitle,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 10.dp, bottom = 12.dp),
        )
        RecordyBasicTextField(
            placeholder = "공간에 대한 나의 생각을 자유롭게 적어주세요!",
            maxLines = 20,
            maxLength = 300,
            minHeight = 148.dp,
            value = state.contentTextValue,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 10.dp)
                .focusRequester(contentFocusRequester),
            onValueChange = updateContentTextField,
        )
        Box(modifier = Modifier.padding(16.dp)) {
            RecordyButton(
                text = "업로드",
                enabled = state.selectedList.isNotEmpty() && state.locationTextValue.isNotEmpty() && state.video != null,
                onClick = {
                    if (state.selectedList.isNotEmpty() && state.locationTextValue.isNotEmpty() && state.video != null) {
                        uploadVideoS3Bucket(File(state.video?.filepath))
//                compressVideo(context, state.video.uri,state.video.name, onSuccess = onSuccess)
                    }
                },
            )
        }
    }

    if (state.showShouldShowRationaleDialog) {
        RecordyDialog(
            graphicAsset = R.drawable.img_allow,
            title = "필수 권한을 허용해주세요",
            subTitle = "사진 접근을 허용하여 영상을 업로드 하세요.",
            negativeButtonLabel = "닫기",
            positiveButtonLabel = "지금 설정",
            onDismissRequest = hideShouldShowRationaleDialog,
            onPositiveButtonClick = {
                openAppSettings(context)
            },
        )
    }

    if (state.showExitUploadDialog) {
        RecordyDialog(
            graphicAsset = R.drawable.img_pen,
            title = "화면을 나가시겠어요?",
            subTitle = "지금까지 작성하신 내용이 모두 사라져요.",
            negativeButtonLabel = "취소",
            positiveButtonLabel = "나가기",
            onDismissRequest = hideExitUploadDialog,
            onPositiveButtonClick = onClickBackStack,
        )
    }
    SelectedVideoBottomSheet(
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        isSheetOpen = state.isSelectedVideoSheetOpen,
        onDismissRequest = hideIsSelectedVideoSheetOpen,
        galleyVideos = exampleVideoList,
        isSelectedVideo = onClickVideo,
        showSnackBar = showSnackBar,
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
            uploadVideoS3Bucket = {},
        )
    }
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}
