package com.record.setting

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.component.button.RecordyButton
import com.record.designsystem.component.textfield.RecordyValidateTextfield
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable

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
) {
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
                    Image(
                        painter = painterResource(id = R.drawable.img_profileedit),
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
                onValueChange = {
                },
                padding = PaddingValues(),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        RecordyButton(
            enabled = false,
            text = "다음",
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 14.dp),
        )
    }
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
