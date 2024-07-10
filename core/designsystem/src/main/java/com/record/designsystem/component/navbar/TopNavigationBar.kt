package com.record.designsystem.component.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.Background
import com.record.designsystem.theme.RecordyTheme
import com.record.designsystem.theme.White

@Composable
fun TopNavigationBar(
    modifier: Modifier = Modifier,
    title: String = "",
    enableGradation: Boolean = false,
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            if (enableGradation) Background.copy(alpha = 0f) else Background,
            Background,
        ),
    )
    Box(
        modifier = modifier
            .background(brush = Brush.verticalGradient(listOf(Color(0x339babfb), Color(0x00000000))))
            .fillMaxWidth()
            .padding(
                top = 45.dp,
                bottom = 15.dp,
            ),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            color = White,
            style = RecordyTheme.typography.title3,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun RecordyTopNavigationBarPreview() {
    RecordyTheme {
        RecordyTheme {
            Column(
                modifier = Modifier
                    .background(Background)
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                TopNavigationBar(title = "Title", enableGradation = true)
                TopNavigationBar(title = "Title")
            }
        }
    }
}
