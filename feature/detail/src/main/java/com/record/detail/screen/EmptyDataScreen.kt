package com.record.detail.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.record.designsystem.component.button.BasicButton
import com.record.designsystem.theme.RecordyTheme

@Composable
fun EmptyDataScreen(
    message: String,
    showButton: Boolean,
    onButtonClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = message,
                    style = RecordyTheme.typography.title3SB,
                    color = RecordyTheme.colors.gray02,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(24.dp))
                if (showButton) {
                    BasicButton(
                        modifier = Modifier
                            .height(44.dp)
                            .fillMaxWidth(0.33f),
                        text = "영상 업로드하기",
                        textStyle = RecordyTheme.typography.body2B,
                        textColor = RecordyTheme.colors.background,
                        backgroundColor = RecordyTheme.colors.viskitYellow400,
                        padding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                        shape = RoundedCornerShape(30.dp),
                        onClick = onButtonClick,
                    )
                }
                Spacer(modifier = Modifier.weight(2.2f))
            }
        }
    }
}
