package com.record.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


@Stable
class RecordyColors(
    main: Color,
    alert: Color,
    gray01: Color,
    gray03: Color,
    gray04: Color,
    gray08: Color,
    gray09: Color,
    background: Color,
) {
    var main by mutableStateOf(main)
        private set
    var alert by mutableStateOf(alert)
        private set
    var gray01 by mutableStateOf(gray01)
        private set
    var gray03 by mutableStateOf(gray03)
        private set
    var gray04 by mutableStateOf(gray04)
        private set
    var gray08 by mutableStateOf(gray08)
        private set
    var gray09 by mutableStateOf(gray09)
        private set
    var background by mutableStateOf(background)
        private set

    fun copy(): RecordyColors = RecordyColors(
        main,
        alert,
        gray01,
        gray03,
        gray04,
        gray08,
        gray09,
        background
    )

    fun update(other: RecordyColors) {
        main = other.main
        alert = other.alert
        gray01 = other.gray01
        gray03 = other.gray03
        gray04 = other.gray04
        gray08 = other.gray08
        gray09 = other.gray09
        background = other.background
    }
}

fun RecordyDarkColor(
    main: Color = Main,
    alert: Color = Alert,
    gray01: Color = Gray01,
    gray03: Color = Gray03,
    gray04: Color = Gray04,
    gray08: Color = Gray08,
    gray09: Color = Gray09,
    background: Color = Background,
) = RecordyColors(
    main,
    alert,
    gray01,
    gray03,
    gray04,
    gray08,
    gray09,
    background
)

private val LocalRecordyColors =
    staticCompositionLocalOf<RecordyColors> { error("provide none color") }

private val LocalRecordyTypography =
    staticCompositionLocalOf<RecordyTypography> { error("provide none typography") }

object RecordyTheme {
    val colors: RecordyColors
        @Composable
        @ReadOnlyComposable
        get() = LocalRecordyColors.current

    val typography: RecordyTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalRecordyTypography.current
}

@Composable
fun provideRecordyColorsAndTypography(
    colors: RecordyColors,
    typography: RecordyTypography,
    content: @Composable () -> Unit
) {
    val provideColors = remember { colors.copy() }
    provideColors.update(colors)
    val provideTypography = remember { typography.copy() }
    provideTypography.update(typography)

    CompositionLocalProvider(
        LocalRecordyColors provides provideColors,
        LocalRecordyTypography provides provideTypography,
        content = content
    )
}

@Composable
fun RecordyTheme(content: @Composable () -> Unit) {
    val colors = RecordyDarkColor()
    val typography = RecordyTypography()
    provideRecordyColorsAndTypography(colors, typography) {
        MaterialTheme(content = content)
    }
}