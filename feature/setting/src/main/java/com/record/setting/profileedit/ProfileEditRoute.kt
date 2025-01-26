package com.record.setting.profileedit

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
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
import com.record.designsystem.component.dialog.RecordyDialog
import com.record.designsystem.component.textfield.RecordyValidateTextfield
import com.record.designsystem.theme.RecordyTheme
import com.record.setting.component.SelectedImageBottomSheet
import com.record.ui.extension.customClickable
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import com.record.upload.model.GalleryImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun ProfileEditRoute(
    padding: PaddingValues,
    modifier: Modifier,
    viewModel: ProfileEditViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest {
            when (it) {
                ProfileEditSideEffect.BackToSetting -> { popBackStack() }
            }
        }
    }

    ProfileScreen(
        padding = padding,
        modifier = modifier,
        state = uiState,
        onClickImage = viewModel::setImage,
        onLoadMore = viewModel::onLoadMore,
        checkValidateNickname = viewModel::checkValidateNickName,
        backToSetting = viewModel::popBackStack,
        updateName = viewModel::updateName,
        updateUrl = viewModel::updateImgUrl,
        isSelectedImageSheetOpen = viewModel::showIsSelectedImageSheetOpen,
        hideIsSelectedVideoSheetOpen = viewModel::hideIsSelectedVideoSheetOpen,
        showShouldShowRationaleDialog = viewModel::showShouldShowRationaleDialog,
        hideExitUploadDialog = viewModel::hideUploadDialog,
        updateUserProfile = viewModel::updateUserProfile,
        showSelectImgDialog = viewModel:: showImageDialog,
        hideImageDialog = viewModel:: hideImageDialog
    )
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    padding: PaddingValues,
    modifier: Modifier,
    state: ProfileEditState,
    onClickImage: (GalleryImage) -> Unit,
    onLoadMore: () -> Unit,
    checkValidateNickname: () -> Unit,
    backToSetting: () -> Unit,
    updateName: (String) -> Unit,
    updateUrl: (String) -> Unit,
    isSelectedImageSheetOpen: () -> Unit,
    hideIsSelectedVideoSheetOpen: () -> Unit,
    showShouldShowRationaleDialog: () -> Unit = {},
    hideExitUploadDialog: () -> Unit = {},
    updateUserProfile: () -> Unit = {},
    showSelectImgDialog: () -> Unit = {},
    hideImageDialog : () -> Unit = {}
) {
    val context = LocalContext.current

    /*val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = result.data?.data
            selectedImageUri?.let {
                state.updateImgUrl(getPathFromUri(context, it))
            }
        }
    }*/

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

    LaunchedEffect(key1 = cameraPermissionState.status.isGranted) {
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

    val defaultPainter = rememberAsyncImagePainter(model = state.defaultProfileImgUrl)

    val painter = rememberAsyncImagePainter(
        model = state.image?.filepath ?: R.drawable.img_profileedit,
        imageLoader = imageLoader,
    )

    var lastInputTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(lastInputTime) {
        delay(300)
        if (System.currentTimeMillis() - lastInputTime >= 300) {
            checkValidateNickname()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        focusManager.clearFocus()
                    },
                )
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = RecordyTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(vertical = 15.dp),
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_angle_left_24),
                    contentDescription = "뒤로가기",
                    tint = RecordyTheme.colors.gray01,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .customClickable {
                            backToSetting()
                        }
                        .padding(start = 20.dp),
                )

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "프로필수정",
                    color = Color.White,
                    style = RecordyTheme.typography.title3,
                )
            }
            Spacer(
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxWidth(),
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .aspectRatio(1f),
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = RecordyTheme.colors.viskitYellow80)
                        .padding(2.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .customClickable {
                            showSelectImgDialog()
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = RecordyTheme.colors.background)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painter = if (state.isSelected) painter else defaultPainter,
                            contentDescription = "프로필 사진",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                        )
                    }
                }
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_camera),
                    contentDescription = "사진 변경",
                    tint = RecordyTheme.colors.background,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 6.dp)
                        .padding(bottom = 6.dp)
                        .clip(CircleShape)
                        .background(RecordyTheme.colors.gray01)
                        .padding(6.dp),
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(horizontal = 20.dp),
            ) {
                Text(
                    text = "닉네임 수정",
                    style = RecordyTheme.typography.title4,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(12.dp))
                RecordyValidateTextfield(
                    placeholder = state.placeHolder,
                    errorState = state.nicknameValidate,
                    onValueChange = {
                        updateName(it)
                        lastInputTime = System.currentTimeMillis()
                    },
                    padding = PaddingValues(),
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            RecordyButton(
                enabled = state.btnEnable,
                text = "완료",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 14.dp),
                onClick = {
                    updateUserProfile()
                },
            )
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
                    }
                },
            )
        }
        if(state.selectInfo.showDialog){
            RecordyDialog(
                title = state.selectInfo.title,
                subTitle = state.selectInfo.subTitle,
                negativeButtonLabel = state.selectInfo.negativeButtonLabel,
                positiveButtonLabel = state.selectInfo.positiveButtonLabel,
                onDismissRequest = {
                    hideImageDialog()
                    return@RecordyDialog
                },
                onPositiveButtonClick = {
                    if (cameraPermissionState.status.shouldShowRationale) {
                        openAppSettings(context)
                        return@RecordyDialog
                    }
                    if (cameraPermissionState.status.isGranted) {
                        isGranted = true
                        hideImageDialog()
                        isSelectedImageSheetOpen()
                        return@RecordyDialog
                    }
                    if (cameraPermissionState.status.shouldShowRationale) {
                        hideImageDialog()
                        return@RecordyDialog
                    }
                    scope.launch {
                        if (!permissionState.value) {
                            requestPermissionLauncher.launch(
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_VIDEO else Manifest.permission.READ_EXTERNAL_STORAGE,
                            )
                        }
                    }
                },
            )

        }

        SelectedImageBottomSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            isSheetOpen = state.isSelectedImageSheetOpen,
            onDismissRequest = hideIsSelectedVideoSheetOpen,
            galleyImages = state.galleryList,
            isSelectedImage = onClickImage,
            onLoadMore = onLoadMore,
            isLoading = state.isItemLoading,
        )
    }
}

fun getPathFromUri(context: Context, uri: Uri): String? {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    context.contentResolver.query(uri, projection, null, null, null).use { cursor ->
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            return cursor.getString(columnIndex)
        }
    }
    return null
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}

@Preview
@Composable
fun PreviewProfileEdit() {
    RecordyTheme {
    }
}
