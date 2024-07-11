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
import androidx.compose.ui.graphics.Shape
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
    shape: Shape = RoundedCornerShape(8.dp),
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
                .background(color = RecordyTheme.colors.gray08, shape = shape)
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
            }
            if (subTitle != null) {
                Text(
                    modifier = Modifier.padding(top = 4.dp, bottom = 20.dp),
                    text = subTitle,
                    style = RecordyTheme.typography.caption1,
                    color = RecordyTheme.colors.gray01,
                    textAlign = TextAlign.Center,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                RecordyButton(
                    text = negativeButtonLabel,
                    shape = shape,
                    enabled = false,
                    textStyle = RecordyTheme.typography.button2,
                    onClick = { onDismissRequest() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                )
                RecordyButton(
                    text = positiveButtonLabel,
                    shape = shape,
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
