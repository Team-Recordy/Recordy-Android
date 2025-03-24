package com.viskit.detail.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.viskit.designsystem.component.button.BasicButton
import com.viskit.designsystem.theme.RecordyTheme

@Composable
fun EmptyDataScreen(
    message: String,
    showButton: Boolean,
    onButtonClick: () -> Unit = {},
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                val messages = message.split("\n")
                Text(
                    text = messages.getOrNull(0) ?: "",
                    style = RecordyTheme.typography.title3SB,
                    color = RecordyTheme.colors.gray02,
                    textAlign = TextAlign.Center,
                )
                if (messages.size > 1) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = messages[1],
                        style = RecordyTheme.typography.title3SB,
                        color = RecordyTheme.colors.gray02,
                        textAlign = TextAlign.Center,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                if (showButton) {
                    BasicButton(
                        modifier = Modifier
                            .height(44.dp)
                            .wrapContentWidth(),
                        text = "영상 업로드하기",
                        textStyle = RecordyTheme.typography.body2B,
                        textColor = RecordyTheme.colors.background,
                        backgroundColor = RecordyTheme.colors.viskitYellow400,
                        padding = PaddingValues(horizontal = 20.dp),
                        shape = RoundedCornerShape(30.dp),
                        onClick = onButtonClick,
                    )
                }
            }
        }
    }
}
