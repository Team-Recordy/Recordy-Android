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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.record.designsystem.R

val PretendardBold = FontFamily(Font(R.font.pretendard_bold, FontWeight.Bold))
val PretendardExtraBold = FontFamily(Font(R.font.pretendard_extrabold, FontWeight.ExtraBold))
val PretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold, FontWeight.SemiBold))
val PretendardMedium = FontFamily(Font(R.font.pretendard_medium, FontWeight.Medium))
val PretendardRegular = FontFamily(Font(R.font.pretendard_regular, FontWeight.Normal))

@Stable
class RecordyTypography internal constructor(
    headline: TextStyle,
    title1: TextStyle,
    title2: TextStyle,
    title3: TextStyle,
    title3SB: TextStyle,
    title4: TextStyle,
    subtitle: TextStyle,
    emptybodyB: TextStyle,
    body1M: TextStyle,
    body1R: TextStyle,
    body2M: TextStyle,
    body2SB: TextStyle,
    body2B: TextStyle,
    body2L: TextStyle,
    caption1R: TextStyle,
    caption1M: TextStyle,
    caption1U: TextStyle,
    caption1SB: TextStyle,
    caption2R: TextStyle,
    caption2M: TextStyle,
    button1: TextStyle,
    button2: TextStyle,
    button2B: TextStyle,
    keyword1: TextStyle,
    keyword2: TextStyle,
    keyword3: TextStyle,
    emptybody: TextStyle,

) {
    var headline: TextStyle by mutableStateOf(headline)
        private set
    var title1: TextStyle by mutableStateOf(title1)
        private set
    var title2: TextStyle by mutableStateOf(title2)
        private set
    var title3: TextStyle by mutableStateOf(title3)
        private set
    var title3SB: TextStyle by mutableStateOf(title3SB)
        private set
    var title4: TextStyle by mutableStateOf(title4)
        private set
    var subtitle: TextStyle by mutableStateOf(subtitle)
        private set
    var emptybodyB: TextStyle by mutableStateOf(emptybodyB)
        private set
    var body1M: TextStyle by mutableStateOf(body1M)
        private set
    var body1R: TextStyle by mutableStateOf(body1R)
        private set
    var body2M: TextStyle by mutableStateOf(body2M)
        private set
    var body2SB: TextStyle by mutableStateOf(body2SB)
        private set
    var body2B: TextStyle by mutableStateOf(body2B)
        private set
    var body2L: TextStyle by mutableStateOf(body2L)
        private set
    var caption1R: TextStyle by mutableStateOf(caption1R)
        private set
    var caption1M: TextStyle by mutableStateOf(caption1M)
        private set
    var caption1U: TextStyle by mutableStateOf(caption1U)
        private set
    var caption1SB: TextStyle by mutableStateOf(caption1SB)
        private set
    var caption2R: TextStyle by mutableStateOf(caption2R)
        private set
    var caption2M: TextStyle by mutableStateOf(caption2M)
        private set
    var button1: TextStyle by mutableStateOf(button1)
        private set
    var button2: TextStyle by mutableStateOf(button2)
        private set
    var button2B: TextStyle by mutableStateOf(button2B)
        private set
    var keyword1: TextStyle by mutableStateOf(keyword1)
        private set
    var keyword2: TextStyle by mutableStateOf(keyword2)
        private set
    var keyword3: TextStyle by mutableStateOf(keyword3)
        private set
    var emptybody: TextStyle by mutableStateOf(emptybody)
        private set
    fun copy(
        headline: TextStyle = this.headline,
        title1: TextStyle = this.title1,
        title2: TextStyle = this.title2,
        title3: TextStyle = this.title3,
        title3SB: TextStyle = this.title3SB,
        title4: TextStyle = this.title4,
        subtitle: TextStyle = this.subtitle,
        emptybodyB: TextStyle = this.emptybodyB,
        body1M: TextStyle = this.body1M,
        body1R: TextStyle = this.body1R,
        body2M: TextStyle = this.body2M,
        body2SB: TextStyle = this.body2SB,
        body2B: TextStyle = this.body2B,
        body2L: TextStyle = this.body2L,
        caption1R: TextStyle = this.caption1R,
        caption1M: TextStyle = this.caption1M,
        caption1U: TextStyle = this.caption1U,
        caption1SB: TextStyle = this.caption1SB,
        caption2R: TextStyle = this.caption2R,
        caption2M: TextStyle = this.caption2M,
        button1: TextStyle = this.button1,
        button2: TextStyle = this.button2,
        button2B: TextStyle = this.button2B,
        keyword1: TextStyle = this.keyword1,
        keyword2: TextStyle = this.keyword2,
        keyword3: TextStyle = this.keyword3,
        emptybody: TextStyle = this.emptybody,
    ): RecordyTypography = RecordyTypography(
        headline,
        title1,
        title2,
        title3,
        title3SB,
        title4,
        subtitle,
        emptybodyB,
        body1M,
        body1R,
        body2M,
        body2SB,
        body2B,
        body2L,
        caption1R,
        caption1M,
        caption1U,
        caption1SB,
        caption2R,
        caption2M,
        button1,
        button2,
        button2B,
        keyword1,
        keyword2,
        keyword3,
        emptybody,
    )

    fun update(other: RecordyTypography) {
        headline = other.headline
        title1 = other.title1
        title2 = other.title2
        title3 = other.title3
        title3SB = other.title3SB
        title4 = other.title4
        subtitle = other.subtitle
        emptybodyB = other.emptybodyB
        body1M = other.body1M
        body1R = other.body1R
        body2M = other.body2M
        body2B = other.body2B
        body2L = other.body2L
        caption1R = other.caption1R
        caption1M = other.caption1M
        caption1U = other.caption1U
        caption1SB = other.caption1SB
        caption2R = other.caption2R
        caption2M = other.caption2M
        button1 = other.button1
        button2 = other.button2
        button2B = other.button2B
        keyword1 = other.keyword1
        keyword2 = other.keyword2
        keyword3 = other.keyword3
        emptybody = other.emptybody
    }
}

@Composable
fun RecordyTypography(): RecordyTypography {
    return RecordyTypography(
        headline = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 26.sp,
            lineHeight = 38.sp,
            letterSpacing = (-0.5).sp,
            color = ViskitYellow500,
        ),
        title1 = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 22.sp,
            lineHeight = 32.sp,
            letterSpacing = (-0.5).sp,
        ),
        title2 = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 20.sp,
            lineHeight = 30.sp,
        ),
        title3 = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
        ),
        title3SB = TextStyle(
            fontFamily = PretendardSemiBold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
        ),
        title4 = TextStyle(
            fontFamily = PretendardExtraBold,
            fontSize = 17.sp,
            lineHeight = 24.sp,
        ),
        subtitle = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 16.sp,
            lineHeight = 28.sp,
        ),
        emptybodyB = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 20.sp,
            lineHeight = 30.sp,
            color = ViskitYellow500,
        ),
        body1M = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        ),
        body1R = TextStyle(
            fontFamily = PretendardRegular,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = ViskitYellow500,
        ),
        body2M = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = (-0.5).sp,
        ),
        body2SB = TextStyle(
            fontFamily = PretendardSemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = (-0.5).sp,
        ),
        body2B = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = (-0.5).sp,
        ),
        body2L = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 14.sp,
            lineHeight = 24.sp,
            letterSpacing = (-0.5).sp,
        ),
        caption1R = TextStyle(
            fontFamily = PretendardRegular,
            fontSize = 12.sp,
            lineHeight = 18.sp,
        ),
        caption1M = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 12.sp,
            lineHeight = 18.sp,
        ),
        caption1U = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            textDecoration = TextDecoration.Underline,
        ),
        caption1SB = TextStyle(
            fontFamily = PretendardSemiBold,
            fontSize = 12.sp,
            lineHeight = 18.sp,
        ),
        caption2R = TextStyle(
            fontFamily = PretendardRegular,
            fontSize = 10.sp,
            lineHeight = 18.sp,
        ),
        caption2M = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 10.sp,
            lineHeight = 18.sp,
        ),
        button1 = TextStyle(
            fontFamily = PretendardSemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        ),
        button2 = TextStyle(
            fontFamily = PretendardSemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        ),
        button2B = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 14.sp,
            lineHeight = 18.sp,
        ),
        keyword1 = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 17.sp,
            lineHeight = 24.sp,
            letterSpacing = (-1).sp,
        ),
        keyword2 = TextStyle(
            fontFamily = PretendardRegular,
            fontSize = 14.sp,
            lineHeight = 24.sp,
            letterSpacing = (-1).sp,
        ),
        keyword3 = TextStyle(
            fontFamily = PretendardRegular,
            fontSize = 12.sp,
            lineHeight = 24.sp,
            letterSpacing = (-1).sp,
        ),
        emptybody = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 20.sp,
            lineHeight = 30.sp,
        ),
    )
}
