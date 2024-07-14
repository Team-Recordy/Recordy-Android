package com.record.mypage.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.record.designsystem.component.button.BasicButton
import com.record.designsystem.component.button.RecordyButton
import com.record.designsystem.theme.RecordyTheme

@Composable
fun EmptyDataScreen(imageRes: Int, message: String, recordCount: Int, showButton: Boolean, onButtonClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(120.dp),
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = message,
                style = RecordyTheme.typography.title3,
                color = RecordyTheme.colors.gray01,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(42.dp))
            if (showButton) {
                BasicButton(
                    modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth(0.33f),
                    text = "기록하러 가기",
                    textStyle = RecordyTheme.typography.button2,
                    textColor = RecordyTheme.colors.gray06,
                    backgroundColor = RecordyTheme.colors.gray01,
                    padding = PaddingValues(horizontal = 20.dp, vertical = 12.dp,),
                    shape = RoundedCornerShape(30.dp),
                    onClick = onButtonClick
                )
            }
        }
    }
}
