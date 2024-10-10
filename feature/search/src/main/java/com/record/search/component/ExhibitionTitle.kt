package com.record.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.RecordyTheme

@Composable
fun ExhibitionTitle(
    modifier: Modifier = Modifier,
    type: String,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = RecordyTheme.colors.gray10)
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
    ) {
        Text(text = type, style = RecordyTheme.typography.caption1R, color = RecordyTheme.colors.gray03)
    }
}
