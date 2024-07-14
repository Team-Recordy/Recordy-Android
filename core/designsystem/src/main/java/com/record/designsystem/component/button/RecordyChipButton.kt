package com.record.designsystem.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.theme.Black
import com.record.designsystem.theme.Gray01
import com.record.designsystem.theme.Gray04
import com.record.designsystem.theme.Gray08
import com.record.designsystem.theme.Gray09
import com.record.designsystem.theme.Main
import com.record.designsystem.theme.RecordyTheme
import com.record.designsystem.theme.Sub01
import com.record.ui.extension.customClickable
import timber.log.Timber

/**
 * 디자인 컴포넌트 chip button & chip small button
 * chip small button은 close 아이콘 포함
 * You can create two types of chip buttons: regular and small.
 * */
@Composable
fun RecordyChipButton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(30.dp),
    text: String,
    isActive: Boolean,
    isCheckSmall: Boolean = true,
    onClick: () -> Unit = {},
) {
    val (borderLineColor, contentColor, backGroundColor) = if (isActive) {
        if(isCheckSmall) Triple(Gray01, Gray09, Gray01) else Triple(Main, Main, Gray08)
    } else {
        Triple(Color.Transparent, Gray04, if (isCheckSmall) Gray09 else Gray08)
    }

    val textStyle = when {
        isCheckSmall -> RecordyTheme.typography.caption1
        isActive -> RecordyTheme.typography.button2
        else -> RecordyTheme.typography.body2M
    }

    Box(
        modifier = modifier
            .clip(shape)
            .background(color = backGroundColor)
            .border(width = 1.dp, color = borderLineColor, shape = shape)
            .padding(vertical = 8.dp)
            .padding(start = 10.dp, end = 10.dp)
            .customClickable(rippleEnabled = false, onClick = { onClick() }),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                style = textStyle,
                color = contentColor,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun RecordyChipButtonPreview() {
    RecordyTheme {
        RecordyTheme {
            Column(
                modifier = Modifier
                    .background(Black)
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                RecordyChipButton(
                    text = "키워드",
                    isActive = false,
                    isCheckSmall = false,
                    onClick = { Timber.d("basic key word") },
                )
                RecordyChipButton(
                    text = "키워드",
                    isActive = true,
                    isCheckSmall = false,
                    onClick = { },
                )
                RecordyChipButton(
                    text = "키워드",
                    isActive = false,
                    onClick = { },
                )
                RecordyChipButton(
                    text = "키워드",
                    isActive = true,
                    onClick = { Timber.d("close key word") },
                )
            }
        }
    }
}
