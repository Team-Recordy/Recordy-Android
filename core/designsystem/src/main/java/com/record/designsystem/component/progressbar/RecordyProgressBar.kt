package com.record.designsystem.component.progressbar

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.RecordyTheme

@Composable
fun RecordyProgressBar(
    completionRatioNumerator: Int,
    completionRatioDenominator: Int,
) {
    var progress by remember { mutableFloatStateOf(0f) }

    val size by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = LinearOutSlowInEasing,
        ),
        label = "animation",
    )

    LaunchedEffect(key1 = completionRatioNumerator, key2 = completionRatioDenominator) {
        progress = if (completionRatioDenominator != 0) {
            completionRatioNumerator / completionRatioDenominator.toFloat()
        } else {
            0f
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .height(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .align(Alignment.Center)
                .background(RecordyTheme.colors.sub01),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(size)
                .height(6.dp)
                .background(RecordyTheme.colors.main)
                .animateContentSize(),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun Test() {
    RecordyProgressBar(
        completionRatioNumerator = 3,
        completionRatioDenominator = 4,
    )
}
