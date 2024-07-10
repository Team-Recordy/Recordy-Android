package com.record.designsystem.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.record.designsystem.component.button.RecordyButton
import com.record.designsystem.theme.RecordyTheme

@Composable
fun RecordyDialog(
    graphicAsset: Int? = null,
    title: String? = null,
    subTitle: String? = null,
    positiveButtonLabel: String = "",
    negativeButtonLabel: String = "",
    onPositiveButtonClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = RecordyTheme.colors.gray08, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp, top = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            graphicAsset?.let {
                Image(
                    modifier = Modifier
                        .size(80.dp),
                    painter = painterResource(id = it),
                    contentDescription = "graphicAsset",
                )
                Spacer(modifier = Modifier.height(18.dp))
            }
            title?.let {
                Text(
                    text = title,
                    style = RecordyTheme.typography.title3,
                    color = RecordyTheme.colors.gray01,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            subTitle?.let {
                Text(
                    text = subTitle,
                    style = RecordyTheme.typography.caption1,
                    color = RecordyTheme.colors.gray01,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                RecordyButton(
                    text = negativeButtonLabel,
                    cornerShape = 8.dp,
                    enabled = true,
                    textStyle = RecordyTheme.typography.button2,
                    containerColor = RecordyTheme.colors.gray05,
                    contentColor = RecordyTheme.colors.gray01,
                    onClick = { onDismissRequest() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                )
                RecordyButton(
                    text = positiveButtonLabel,
                    cornerShape = 8.dp,
                    enabled = true,
                    textStyle = RecordyTheme.typography.button2,
                    onClick = { onPositiveButtonClick() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordyDialogPreview() {
    RecordyTheme {
        RecordyDialog(title = "정말로 탈퇴하시겠어요?", subTitle = "회원님의 소중한 기록들이 사라져요.", negativeButtonLabel = "취소", positiveButtonLabel = "탈퇴")
    }
}
