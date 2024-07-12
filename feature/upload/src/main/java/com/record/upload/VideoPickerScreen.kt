package com.record.upload

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.record.designsystem.R
import com.record.designsystem.component.button.RecordyButton
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.component.textfield.RecordyBasicTextField
import com.record.designsystem.theme.Background
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun VideoPickerRoute(
    paddingValues: PaddingValues,
    navigateSelectedVideo: () -> Unit,
) {
    VideoPickerScreen(
        navigateSelectedVideo = navigateSelectedVideo,
        onClickKeyword = {}
    )
}

@Composable
fun VideoPickerScreen(
    navigateSelectedVideo: () -> Unit,
    onClickKeyword: () -> Unit = {},
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var hasPermission by remember { mutableStateOf(false) }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasPermission = permissions.values.all { it }
    }
    LaunchedEffect(Unit) {
        hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.checkSelfPermission(Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED
        } else {
            context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }

        if (!hasPermission) {
            permissionLauncher.launch(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    arrayOf(Manifest.permission.READ_MEDIA_VIDEO)
                } else {
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            )
        }
    }
    var normalValue by remember {
        mutableStateOf("")
    }
    if (hasPermission) {
        Log.d("galley","hasPermission")
    } else {
        scope.launch {
            permissionLauncher.launch(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    arrayOf(Manifest.permission.READ_MEDIA_VIDEO)
                } else {
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            )
        }
       Log.d("galleynotPermissio ","hasPermission")
    }
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
                modifier = Modifier.padding(top = 20.dp, bottom = 9.dp)
            )
            Box(
                modifier = Modifier
                    .background(RecordyTheme.colors.gray08, shape = RoundedCornerShape(16.dp))
                    .customClickable(onClick = navigateSelectedVideo),
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 115.dp, horizontal = 57.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus_25),
                        contentDescription = null,
                        modifier = Modifier.padding(bottom = 12.dp)
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
                modifier = Modifier.padding(top = 22.dp, bottom = 12.dp)
            )
                Row(modifier = Modifier
                    .background(RecordyTheme.colors.gray08, shape = RoundedCornerShape(30.dp))
                    .padding(vertical = 8.dp)
                    .padding(start = 8.dp, end = 12.dp)
                    .customClickable(onClick = onClickKeyword),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
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
                modifier = Modifier.padding(top = 20.dp, bottom = 12.dp)
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
                modifier = Modifier.padding(top = 10.dp, bottom = 12.dp)
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
//                modifier = Modifier.padding(16.dp),
                text = "키워드",
                enabled = false,
                onClick = { Timber.d("basic key word") },
            )
        }
    }
}

@Preview
@Composable
fun VideoPickerScreenPreview() {
    RecordyTheme {
        VideoPickerScreen(navigateSelectedVideo = { /*TODO*/ })
    }
}