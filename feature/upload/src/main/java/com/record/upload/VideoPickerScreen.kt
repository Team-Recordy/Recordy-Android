package com.record.upload

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.record.designsystem.R
import com.record.designsystem.component.button.RecordyButton
import com.record.designsystem.component.dialog.RecordyDialog
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.component.textfield.RecordyBasicTextField
import com.record.designsystem.theme.Background
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import com.record.upload.component.bottomsheet.DefinedContentBottomSheet
import com.record.upload.component.bottomsheet.SelectedVideoBottomSheet
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun VideoPickerRoute(
    paddingValues: PaddingValues,
    viewModel: UploadViewModel = hiltViewModel(),
    navigateSelectedVideo: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { }
    }

    VideoPickerScreen(
        state = state,
        showShouldShowRationaleDialog = viewModel::showShouldShowRationaleDialog,
        hideShouldShowRationaleDialog = viewModel::hideShouldShowRationaleDialog,
        showIsSelectedVideoSheetOpen = viewModel::showIsSelectedVideoSheetOpen,
        hideIsSelectedVideoSheetOpen = viewModel::hideIsSelectedVideoSheetOpen,
        showIsSelectedDefinedContentSheetOpen = viewModel::showIsSelectedDefinedContentSheetOpen,
        hideIsSelectedDefinedContentSheetOpen = viewModel::hideIsSelectedDefinedContentSheetOpen,
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun VideoPickerScreen(
    state: UploadState = UploadState(),
    showShouldShowRationaleDialog: () -> Unit = {},
    hideShouldShowRationaleDialog: () -> Unit = {},
    showIsSelectedVideoSheetOpen: () -> Unit = {},
    hideIsSelectedVideoSheetOpen: () -> Unit = {},
    showIsSelectedDefinedContentSheetOpen: () -> Unit = {},
    hideIsSelectedDefinedContentSheetOpen: () -> Unit = {},
) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.READ_MEDIA_VIDEO)
    val exampleVideoList = getAllVideos(10, null, context)
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (isGranted) {
            Timber.d("Permission granted")
        } else {
            Timber.d("Handle permission denial")
        }
    }
    var normalValue by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState()),
        ) {
            TopNavigationBar(title = "내용 작성", enableGradation = true)
            Text(
                text = "ⓘ 주제와 무관한 기록은 무통보로 삭제될 수 있습니다",
                color = RecordyTheme.colors.gray03,
                style = RecordyTheme.typography.caption2,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
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
                                showIsSelectedVideoSheetOpen()
                                return@customClickable
                            }
                            if (cameraPermissionState.status.shouldShowRationale) {
                                showShouldShowRationaleDialog()
                                return@customClickable
                            }
                            scope.launch {
                                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_VIDEO)
                            }
                        },
                    ),
            ) {
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
            }
            Text(
                text = "키워드",
                color = RecordyTheme.colors.white,
                style = RecordyTheme.typography.subtitle,
                modifier = Modifier.padding(top = 22.dp, bottom = 12.dp),
            )
            Row(
                modifier = Modifier
                    .background(RecordyTheme.colors.gray08, shape = RoundedCornerShape(30.dp))
                    .padding(vertical = 8.dp)
                    .padding(start = 8.dp, end = 12.dp)
                    .customClickable(onClick = showIsSelectedDefinedContentSheetOpen),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
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
            Text(
                text = "위치",
                color = RecordyTheme.colors.white,
                style = RecordyTheme.typography.subtitle,
                modifier = Modifier.padding(top = 20.dp, bottom = 12.dp),
            )
            RecordyBasicTextField(
                placeholder = "영상 속 위치는 어디인가요?",
                maxLines = 1,
                maxLength = 10,
                value = normalValue,
                onValueChange = { normalValue = it },
            )
            Text(
                text = "내용",
                color = RecordyTheme.colors.white,
                style = RecordyTheme.typography.subtitle,
                modifier = Modifier.padding(top = 10.dp, bottom = 12.dp),
            )
            RecordyBasicTextField(
                placeholder = "공간에 대한 나의 생각을 자유롭게 적어주세요!",
                maxLines = 20,
                maxLength = 300,
                minHeight = 148.dp,
                value = normalValue,
                modifier = Modifier.padding(bottom = 10.dp),
                onValueChange = { normalValue = it },
            )
            RecordyButton(
                text = "키워드",
                enabled = false,
                onClick = { Timber.d("basic key word") },
            )
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
        SelectedVideoBottomSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            isSheetOpen = state.isSelectedVideoSheetOpen,
            onDismissRequest = hideIsSelectedVideoSheetOpen,
            galleyVideos = exampleVideoList,
        )
        DefinedContentBottomSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            isSheetOpen = state.isSelectedDefinedContentSheetOpen,
            onDismissRequest = hideIsSelectedDefinedContentSheetOpen,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun VideoPickerScreenPreview() {
    RecordyTheme {
        VideoPickerScreen()
    }
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}
