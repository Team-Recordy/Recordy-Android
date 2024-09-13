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
    ViskitYellow500: Color,
    ViskitYellow400: Color,
    ViskitYellow300: Color,
    ViskitYellow200: Color,
    ViskitYellow100: Color,
    ViskitYellow80: Color,
    ViskitYellow60: Color,
    ViskitYellow40: Color,
    ViskitYellow20: Color,
    alert01: Color,
    alert02: Color,
    kakaoyellow: Color,
    kakaobrown: Color,
    white: Color,
    gray01: Color,
    gray02: Color,
    gray03: Color,
    gray05: Color,
    gray06: Color,
    gray07: Color,
    gray08: Color,
    gray09: Color,
    gray10: Color,
    gray11: Color,
    black: Color,
    background: Color,
    black20: Color,
    black30: Color,
    black50: Color,
    black70: Color,
) {
    var ViskitYellow500 by mutableStateOf(ViskitYellow500)
        private set
    var ViskitYellow400 by mutableStateOf(ViskitYellow400)
        private set
    var ViskitYellow300 by mutableStateOf(ViskitYellow300)
        private set
    var ViskitYellow200 by mutableStateOf(ViskitYellow200)
        private set
    var ViskitYellow100 by mutableStateOf(ViskitYellow100)
        private set
    var ViskitYellow80 by mutableStateOf(ViskitYellow80)
        private set
    var ViskitYellow60 by mutableStateOf(ViskitYellow60)
        private set
    var ViskitYellow40 by mutableStateOf(ViskitYellow40)
        private set
    var ViskitYellow20 by mutableStateOf(ViskitYellow20)
        private set
    var alert01 by mutableStateOf(alert01)
        private set
    var alert02 by mutableStateOf(alert02)
        private set
    var kakaoyellow by mutableStateOf(kakaoyellow)
        private set
    var kakaobrown by mutableStateOf(kakaobrown)
        private set
    var white by mutableStateOf(white)
        private set
    var gray01 by mutableStateOf(gray01)
        private set
    var gray02 by mutableStateOf(gray02)
        private set
    var gray03 by mutableStateOf(gray03)
        private set
    var gray05 by mutableStateOf(gray05)
        private set
    var gray06 by mutableStateOf(gray06)
        private set
    var gray07 by mutableStateOf(gray07)
        private set
    var gray08 by mutableStateOf(gray08)
        private set
    var gray09 by mutableStateOf(gray09)
        private set
    var gray10 by mutableStateOf(gray10)
        private set
    var gray11 by mutableStateOf(gray11)
        private set
    var black by mutableStateOf(black)
        private set
    var background by mutableStateOf(background)
        private set
    var black20 by mutableStateOf(black20)
        private set
    var black30 by mutableStateOf(black30)
        private set
    var black50 by mutableStateOf(black50)
        private set
    var black70 by mutableStateOf(black70)
        private set

    fun copy(): RecordyColors = RecordyColors(
        ViskitYellow500,
        ViskitYellow400,
        ViskitYellow300,
        ViskitYellow200,
        ViskitYellow100,
        ViskitYellow80,
        ViskitYellow60,
        ViskitYellow40,
        ViskitYellow20,
        alert01,
        alert02,
        kakaoyellow,
        kakaobrown,
        white,
        gray01,
        gray02,
        gray03,
        gray05,
        gray06,
        gray07,
        gray08,
        gray09,
        gray10,
        gray11,
        black,
        background,
        black20,
        black30,
        black50,
        black70,
    )

    fun update(other: RecordyColors) {
        ViskitYellow500 = other.ViskitYellow500
        ViskitYellow400 = other.ViskitYellow400
        ViskitYellow300 = other.ViskitYellow300
        ViskitYellow200 = other.ViskitYellow200
        ViskitYellow100 = other.ViskitYellow100
        ViskitYellow80 = other.ViskitYellow80
        ViskitYellow60 = other.ViskitYellow60
        ViskitYellow40 = other.ViskitYellow40
        ViskitYellow20 = other.ViskitYellow20
        alert01 = other.alert01
        alert02 = other.alert02
        kakaoyellow = other.kakaoyellow
        kakaobrown = other.kakaobrown
        white = other.white
        gray01 = other.gray01
        gray02 = other.gray02
        gray03 = other.gray03
        gray05 = other.gray05
        gray06 = other.gray06
        gray07 = other.gray07
        gray08 = other.gray08
        gray09 = other.gray09
        gray10 = other.gray10
        gray11 = other.gray11
        black = other.black
        background = other.background
        black20 = other.black20
        black30 = other.black30
        black50 = other.black50
        black70 = other.black70
    }
}

fun RecordyDarkColor(
    viskitYellow500: Color = ViskitYellow500,
    viskitYellow400: Color = ViskitYellow400,
    viskitYellow300: Color = ViskitYellow300,
    viskitYellow200: Color = ViskitYellow200,
    viskitYellow100: Color = ViskitYellow100,
    viskitYellow80: Color = ViskitYellow80,
    viskitYellow60: Color = ViskitYellow60,
    viskitYellow40: Color = ViskitYellow40,
    viskitYellow20: Color = ViskitYellow20,
    alert01: Color = Alert01,
    alert02: Color = Alert02,
    kakaoyellow: Color = Kakaoyellow,
    kakaobrown: Color = Kakaobrown,
    white: Color = White,
    gray01: Color = Gray01,
    gray02: Color = Gray02,
    gray03: Color = Gray03,
    gray05: Color = Gray05,
    gray06: Color = Gray06,
    gray07: Color = Gray07,
    gray08: Color = Gray08,
    gray09: Color = Gray09,
    gray10: Color = Gray10,
    gray11: Color = Gray11,
    black: Color = Black,
    background: Color = Background,
    black20: Color = Black20,
    black30: Color = Black30,
    black50: Color = Black50,
    black70: Color = Black70,
) = RecordyColors(
    viskitYellow500,
    viskitYellow400,
    viskitYellow300,
    viskitYellow200,
    viskitYellow100,
    viskitYellow80,
    viskitYellow60,
    viskitYellow40,
    viskitYellow20,
    alert01,
    alert02,
    white,
    kakaoyellow,
    kakaobrown,
    gray01,
    gray02,
    gray03,
    gray05,
    gray06,
    gray07,
    gray08,
    gray09,
    gray10,
    gray11,
    black,
    background,
    black20,
    black30,
    black50,
    black70,
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
    content: @Composable () -> Unit,
) {
    val provideColors = remember { colors.copy() }
    provideColors.update(colors)
    val provideTypography = remember { typography.copy() }
    provideTypography.update(typography)

    CompositionLocalProvider(
        LocalRecordyColors provides provideColors,
        LocalRecordyTypography provides provideTypography,
        content = content,
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
