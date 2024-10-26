package com.record.setting.profileedit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.record.designsystem.R
import com.record.designsystem.component.button.RecordyButton
import com.record.designsystem.component.textfield.RecordyValidateTextfield
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable
import kotlinx.coroutines.delay

@Composable
fun ProfileEditRoute(
    padding: PaddingValues,
    modifier: Modifier,
    popBackStack: () -> Unit,
) {
    ProfileScreen(padding, modifier, popBackStack)
}

@Composable
fun ProfileScreen(
    padding: PaddingValues,
    modifier: Modifier,
    popBackStack: () -> Unit,
    viewModel: ProfileEditViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = result.data?.data
            selectedImageUri?.let {
                viewModel.updateImgUrl(getPathFromUri(context, it))
            }
        }
    }

    var lastInputTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(lastInputTime) {
        delay(300)
        if (System.currentTimeMillis() - lastInputTime >= 300) {
            viewModel.checkValidateNickName()
        }
    }

    Column(
        modifier = modifier
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
                        popBackStack()
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
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = RecordyTheme.colors.background)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    val painter = if (uiState.value.profileImgUrl != null) {
                        rememberAsyncImagePainter(uiState.value.profileImgUrl)
                    } else {
                        painterResource(id = R.drawable.img_profileedit)
                    }

                    Image(
                        painter = painter,
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
                    .padding(6.dp)
                    .customClickable {
                        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        galleryLauncher.launch(intent)
                    },
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
                errorState = uiState.value.nicknameValidate,
                onValueChange = {
                    viewModel.updateName(it)
                    lastInputTime = System.currentTimeMillis()
                },
                padding = PaddingValues(),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        RecordyButton(
            enabled = uiState.value.btnEnable,
            text = "다음",
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 14.dp),
            onClick = {
                uiState.value.profileImgUrl?.let {
                    viewModel.updateUserProfile()
                    popBackStack()
                }
            },
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

@Preview
@Composable
fun PreviewProfileEdit() {
    RecordyTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            ProfileScreen(PaddingValues(), Modifier, {})
        }
    }
}
