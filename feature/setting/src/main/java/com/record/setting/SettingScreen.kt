package com.record.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable

@Composable
fun SettingRoute(
    padding: PaddingValues,
    modifier: Modifier,
) {
    SettingScreen(padding,modifier)
}


@Composable
fun SettingScreen(
    padding: PaddingValues = PaddingValues(),
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = RecordyTheme.colors.background),
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "설정",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center,
            style = RecordyTheme.typography.title3,
            color = RecordyTheme.colors.gray01,
        )
        Text(
            text = "도움말",
            modifier = Modifier
                .padding(start = 18.dp)
                .padding(top = 28.dp, bottom = 12.dp),
            style = RecordyTheme.typography.title3,
            color = RecordyTheme.colors.gray01,
        )
        SettingButtonWithIcon(text = "커뮤니티 가이드라인")
        SettingButtonWithIcon(text = "서비스 이용약관")
        SettingButtonWithIcon(text = "개인정보 취급취침")
        SettingButtonWithIcon(text = "문의")
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
        SettingButton(text = "로그아웃")
        SettingButton(text = "탈퇴")
        Text(
            text = "앱 버전 1.0",
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(top = 4.dp),
            style = RecordyTheme.typography.caption2,
            color = RecordyTheme.colors.gray04,
        )
    }

}

@Composable
fun SettingButtonWithIcon(
    modifier: Modifier = Modifier
        .height(48.dp),
    text: String = "커뮤니티 가이드라인",
    onClickEvent: () -> Unit = {},
) {
    Row(modifier = modifier.customClickable(rippleEnabled = false, onClick = onClickEvent)) {
        Text(
            text = text,
            modifier = modifier
                .fillMaxHeight()
                .padding(start = 16.dp)
                .padding(vertical = 12.dp),
            style = RecordyTheme.typography.body1M, color = RecordyTheme.colors.gray01, textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_angle_right_24),
            modifier = modifier
                .padding(end = 16.dp)
                .padding(vertical = 16.dp),
            contentDescription = "",
            tint = RecordyTheme.colors.gray03,
        )
    }

}

@Composable
fun SettingButton(
    modifier: Modifier = Modifier
        .height(48.dp),
    text: String = "로그인 연동",
    kakao: Boolean = false,
    onClickEvent: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .customClickable(rippleEnabled = false, onClick = onClickEvent),
    ) {
        Text(
            text = text,
            modifier = modifier
                .fillMaxHeight()
                .padding(start = 16.dp)
                .padding(vertical = 12.dp),
            style = RecordyTheme.typography.body1M, color = RecordyTheme.colors.gray01, textAlign = TextAlign.Center,
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
        SettingScreen()
    }
}
