package com.record.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.record.designsystem.R

val PretendardBold = FontFamily(Font(R.font.pretendard_bold, FontWeight.Bold))
val PretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold, FontWeight.SemiBold))
val PretendardMedium = FontFamily(Font(R.font.pretendard_medium, FontWeight.Medium))
val PretendardRegular = FontFamily(Font(R.font.pretendard_regular, FontWeight.Normal))

@Stable
class RecordyTypography internal constructor(
    headlineB26: TextStyle,
    title1B20: TextStyle,
    title2B18: TextStyle,
    subtitleB16: TextStyle,
    body1M16: TextStyle,
    body1R16: TextStyle,
    body2M14: TextStyle,
    body2LM14: TextStyle,
    captionM12: TextStyle,
    button1S16: TextStyle,
    button2M14: TextStyle,
) {
    var headlineB26: TextStyle by mutableStateOf(headlineB26)
        private set
    var title1B20: TextStyle by mutableStateOf(title1B20)
        private set
    var title2B18: TextStyle by mutableStateOf(title2B18)
        private set
    var subtitleB16: TextStyle by mutableStateOf(subtitleB16)
        private set
    var body1M16: TextStyle by mutableStateOf(body1M16)
        private set
    var body1R16: TextStyle by mutableStateOf(body1R16)
        private set
    var body2M14: TextStyle by mutableStateOf(body2M14)
        private set
    var body2LM14: TextStyle by mutableStateOf(body2LM14)
        private set
    var captionM12: TextStyle by mutableStateOf(captionM12)
        private set
    var button1S16: TextStyle by mutableStateOf(button1S16)
        private set
    var button2M14: TextStyle by mutableStateOf(button2M14)
        private set

    fun copy(
        headlineB26: TextStyle = this.headlineB26,
        title1B20: TextStyle = this.title1B20,
        title2B18: TextStyle = this.title2B18,
        subtitleB16: TextStyle = this.subtitleB16,
        body1M16: TextStyle = this.body1M16,
        body1R16: TextStyle = this. body1R16,
        body2M14: TextStyle = this.body2M14,
        body2LM14: TextStyle = this.body2LM14,
        captionM12: TextStyle = this.captionM12,
        button1S16: TextStyle = this.button1S16,
        button2M14: TextStyle = this.button2M14,
    ): RecordyTypography = RecordyTypography(
        headlineB26,
        title1B20,
        title2B18,
        subtitleB16,
        body1M16,
        body1R16,
        body2M14,
        body2LM14,
        captionM12,
        button1S16,
        button2M14,
    )

    fun update(other: RecordyTypography) {
        headlineB26 = other.headlineB26
        title1B20 = other.title1B20
        title2B18 = other.title2B18
        subtitleB16 = other.subtitleB16
        body1M16 = other.body1M16
        body1R16 = other.body1R16
        body2M14 = other.body2M14
        body2LM14 = other.body2LM14
        captionM12 = other.captionM12
        button1S16 = other.button1S16
        button2M14 = other.button2M14
    }
}

@Composable
fun RecordyTypography(): RecordyTypography {
    return RecordyTypography(
        headlineB26 = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 26.sp,
            lineHeight = 38.sp,
        ),
        title1B20 = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 20.sp,
            lineHeight = 24.sp,
        ),
        title2B18 = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 18.sp,
            lineHeight = 24.sp
        ),
        subtitleB16 = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 16.sp,
            lineHeight = 28.sp,
        ),
        body1M16 = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        body1R16 = TextStyle(
            fontFamily = PretendardRegular,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        ),
        body2M14 = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        body2LM14 = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 14.sp,
            lineHeight = 24.sp,
        ),
        captionM12 = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 12.sp,
            lineHeight = 18.sp
        ),
        button1S16 = TextStyle(
            fontFamily = PretendardSemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        ),
        button2M14 = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
    )
}