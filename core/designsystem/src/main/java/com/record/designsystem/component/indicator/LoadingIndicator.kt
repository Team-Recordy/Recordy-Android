package com.record.designsystem.component.indicator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.record.designsystem.R
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable

@Composable
fun LoadingIndicator() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_lotties))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 4.0f,
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .customClickable(rippleEnabled = false) {}
            .background(color = RecordyTheme.colors.black50),
    ) {
        LottieAnimation(
            composition,
            { progress },
            modifier = Modifier
                .align(Alignment.Center),
        )
    }
}
