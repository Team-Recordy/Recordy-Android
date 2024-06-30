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
    sub01: Color,
    sub02: Color,
    alert: Color,
    kakao: Color,
    gray01: Color,
    gray03: Color,
    gray04: Color,
    gray05: Color,
    gray08: Color,
    gray09: Color,
    white: Color,
    black: Color,
    background: Color
) {
    var main by mutableStateOf(main)
        private set
    var sub01 by mutableStateOf(sub01)
        private set
    var sub02 by mutableStateOf(sub02)
        private set
    var alert by mutableStateOf(alert)
        private set
    var kakao by mutableStateOf(kakao)
        private set
    var gray01 by mutableStateOf(gray01)
        private set
    var gray03 by mutableStateOf(gray03)
        private set
    var gray04 by mutableStateOf(gray04)
        private set
    var gray05 by mutableStateOf(gray05)
        private set
    var gray08 by mutableStateOf(gray08)
        private set
    var gray09 by mutableStateOf(gray09)
        private set
    var white by mutableStateOf(white)
        private set
    var black by mutableStateOf(black)
        private set
    var background by mutableStateOf(background)
        private set

    fun copy(): RecordyColors = RecordyColors(
        main,
        sub01,
        sub02,
        alert,
        kakao,
        gray01,
        gray03,
        gray04,
        gray05,
        gray08,
        gray09,
        white,
        black,
        background
    )

    fun update(other: RecordyColors) {
        main = other.main
        sub01 = other.sub01
        sub02 = other.sub02
        alert = other.alert
        kakao = other.kakao
        gray01 = other.gray01
        gray03 = other.gray03
        gray04 = other.gray04
        gray05 = other.gray05
        gray08 = other.gray08
        gray09 = other.gray09
        white = other.white
        black = other.black
        background = other.background
    }
}

fun RecordyDarkColor(
    main: Color = Main,
    sub01: Color = Sub01,
    sub02: Color = Sub02,
    alert: Color = Alert,
    kakao: Color = Kakao,
    gray01: Color = Gray01,
    gray03: Color = Gray03,
    gray04: Color = Gray04,
    gray05: Color = Gray05,
    gray08: Color = Gray08,
    gray09: Color = Gray09,
    white: Color = White,
    black: Color = Black,
    background: Color = Background
) = RecordyColors(
    main,
    sub01,
    sub02,
    alert,
    kakao,
    gray01,
    gray03,
    gray04,
    gray05,
    gray08,
    gray09,
    white,
    black,
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
