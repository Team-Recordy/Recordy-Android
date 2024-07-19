package com.record.designsystem.component.shadow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.cardShadow(
    modifier: Modifier = Modifier,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 12.dp,
) = this.then(
    modifier.basicShadow(
        color = Color.Black,
        spread = 0.1.dp,
        borderRadius = borderRadius,
        blurRadius = blurRadius,
        offsetY = -1.dp,
        offsetX = -1.dp,
    ).background(Color.Transparent),
)

@Preview(showBackground = true)
@Composable
fun CardShadowPreview() {
    Box(
        modifier = Modifier.padding(40.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .cardShadow(borderRadius = 1005.dp, blurRadius = 120.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.Transparent),
        ) {
        }
    }
}
