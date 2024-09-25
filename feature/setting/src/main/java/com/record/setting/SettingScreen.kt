package com.record.setting

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.R
import com.record.designsystem.component.dialog.RecordyDialog
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingRoute(
    padding: PaddingValues,
    modifier: Modifier,
    navigateToLogin: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is SettingSideEffect.Restart -> {
                    navigateToLogin()
                }
            }
        }
    }

    SettingScreen(padding, modifier, uiState, viewModel::logout, viewModel::delete, viewModel::dismissDialog, viewModel::eventDialog)
}

@Composable
fun SettingScreen(
    padding: PaddingValues = PaddingValues(),
    modifier: Modifier = Modifier,
    uiState: SettingState,
    logoutEvent: () -> Unit,
    deleteEvent: () -> Unit,
    dismissDialog: () -> Unit,
    eventDialog: () -> Unit,
) {
    val context = LocalContext.current

    if (uiState.dialog != SettingDialog.NONE) {
        RecordyDialog(
            graphicAsset = R.drawable.img_alert,
            title = uiState.dialogTitle,
            subTitle = uiState.dialogSubTitle,
            negativeButtonLabel = uiState.negativeButtonLabel,
            positiveButtonLabel = uiState.positiveButtonLabel,
            onDismissRequest = { dismissDialog() },
            onPositiveButtonClick = { eventDialog() },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = RecordyTheme.colors.background),
    ) {
        TopNavigationBar(
            title = "설정",
        )
        Text(
            text = "도움말",
            modifier = Modifier
                .padding(start = 18.dp)
                .padding(top = 28.dp, bottom = 12.dp),
            style = RecordyTheme.typography.title3,
            color = RecordyTheme.colors.gray01,
        )
        SettingButtonWithIcon(
            text = "커뮤니티 가이드라인",
            onClickEvent = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bohyunnkim.notion.site/98d0fa7eac84431ab6f6dd63be0fb8ff?pvs=4"))
                context.startActivity(intent)
            },
        )

        SettingButtonWithIcon(
            text = "서비스 이용약관",
            onClickEvent = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bohyunnkim.notion.site/e5c0a49d73474331a21b1594736ee0df?pvs=4"))
                context.startActivity(intent)
            },
        )
        SettingButtonWithIcon(
            text = "개인정보 취급취침",
            onClickEvent = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bohyunnkim.notion.site/c2bdf3572df1495c92aedd0437158cf0"))
                context.startActivity(intent)
            },
        )
        SettingButtonWithIcon(
            text = "문의",
            onClickEvent = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bohyunnkim.notion.site/46bdd724bf734cf79d34142a03ad52bc?pvs=4"))
                context.startActivity(intent)
            },
        )
        Spacer(modifier = Modifier.height(12.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(color = RecordyTheme.colors.gray09),
        )
        Text(
            text = "기타",
            modifier = Modifier
                .padding(start = 18.dp)
                .padding(top = 24.dp, bottom = 12.dp),
            style = RecordyTheme.typography.title3,
            color = RecordyTheme.colors.gray01,
        )
        SettingButton(kakao = true)
        SettingButton(text = "로그아웃", onClickEvent = logoutEvent)
        SettingButton(text = "탈퇴", onClickEvent = deleteEvent)
        Text(
            text = "앱 버전 1.0",
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(top = 4.dp),
            style = RecordyTheme.typography.caption2R,
            color = RecordyTheme.colors.gray06,
        )
    }
}

@Composable
fun SettingButtonWithIcon(
    modifier: Modifier = Modifier,
    text: String = "커뮤니티 가이드라인",
    onClickEvent: () -> Unit = {},
) {
    Row(
        modifier = modifier.customClickable(rippleColor = RecordyTheme.colors.white, onClick = onClickEvent),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            modifier = modifier
                .padding(start = 16.dp)
                .padding(vertical = 16.dp),
            style = RecordyTheme.typography.body1M,
            color = RecordyTheme.colors.gray01,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_angle_right_24),
            modifier = modifier
                .padding(16.dp),
            contentDescription = "",
            tint = RecordyTheme.colors.gray03,
        )
    }
}

@Composable
fun SettingButton(
    modifier: Modifier = Modifier,
    text: String = "로그인 연동",
    kakao: Boolean = false,
    onClickEvent: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .customClickable(rippleColor = RecordyTheme.colors.white, onClick = onClickEvent),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            modifier = modifier
                .padding(start = 16.dp)
                .padding(vertical = 16.dp)
                .align(Alignment.CenterVertically),
            style = RecordyTheme.typography.body1M,
            color = RecordyTheme.colors.gray01,
            textAlign = TextAlign.Center,
        )
        if (kakao) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_social_setting_kakao_24),
                modifier = modifier
                    .padding(end = 16.dp)
                    .padding(vertical = 12.dp),
                contentDescription = "",
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun PreviewSettingScreen() {
    RecordyTheme {
        SettingScreen(
            uiState = SettingState(),
            logoutEvent = {},
            deleteEvent = {},
            dismissDialog = {},
            eventDialog = {},
        )
    }
}
