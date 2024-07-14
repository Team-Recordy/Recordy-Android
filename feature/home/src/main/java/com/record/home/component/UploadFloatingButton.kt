package com.record.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.theme.PretendardBold
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable

@Composable
fun UploadFloatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
){
    Box(
        modifier = modifier
            .customClickable { onClick() }
            .background(color = Color(0xFF9BABFB), shape = RoundedCornerShape(40.dp)),
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .padding(start = 10.dp),
                painter = painterResource(id = R.drawable.ic_upload_floating),
                contentDescription = "floating button"
            )
            Text(
                modifier = Modifier
                    .padding(end = 11.dp),
                text = "기록하기",
                style = RecordyTheme.typography.button2.copy(fontFamily = PretendardBold),
                color = RecordyTheme.colors.gray08,
            )
        }
    }
}

@Preview
@Composable
fun PreviewFloatingButton() {
    RecordyTheme {
        UploadFloatingButton()
    }
}
