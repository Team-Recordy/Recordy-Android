package com.record.ui.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString

@Composable
fun AnnotatedText(
    modifier: Modifier = Modifier,
    originalText: String,
    originalTextStyle: TextStyle,
    targetTextList: List<String>,
    spanStyle: SpanStyle,
) {
    val annotatedString = buildAnnotatedString {
        append(originalText)

        targetTextList.forEach { targetText ->
            val startIndex = originalText.indexOf(targetText)
            val endIndex = startIndex + targetText.length
            addStyle(style = spanStyle, start = startIndex, end = endIndex)
        }
    }
    Text(
        modifier = modifier,
        style = originalTextStyle,
        text = annotatedString,
    )
}

@Composable
fun AnnotatedTextByIndex(
    modifier: Modifier = Modifier,
    originalText: String,
    originalTextStyle: TextStyle,
    targetTextRangeList: List<IntRange>,
    spanStyle: SpanStyle,
) {
    val annotatedString = buildAnnotatedString {
        append(originalText)

        targetTextRangeList.forEach { range ->
            addStyle(style = spanStyle, start = range.first, end = range.last)
        }
    }
    Text(
        modifier = modifier,
        style = originalTextStyle,
        text = annotatedString,
    )
}