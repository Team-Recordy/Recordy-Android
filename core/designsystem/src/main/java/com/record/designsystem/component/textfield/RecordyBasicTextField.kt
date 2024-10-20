package com.record.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.Alert01
import com.record.designsystem.theme.Black
import com.record.designsystem.theme.Gray01
import com.record.designsystem.theme.Gray03
import com.record.designsystem.theme.Gray06
import com.record.designsystem.theme.Gray08
import com.record.designsystem.theme.RecordyTheme
import com.record.designsystem.theme.ViskitYellow500

/**
 * design system small text field / Large text field 2가지 타입 구현 가능
 * */
@Composable
fun RecordyBasicTextField(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    placeholder: String = "",
    labelText: String = "",
    value: String = "",
    onValueChange: (String) -> Unit = { _ -> },
    isError: Boolean = false,
    maxLines: Int = 1,
    minLines: Int = 1,
    maxLength: Int = 10,
    minHeight: Dp = 52.dp,
    textStyle: TextStyle = RecordyTheme.typography.body2M,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderLineColor = when {
        isError -> Alert01
        isFocused -> ViskitYellow500
        value.isEmpty() -> Color.Transparent
        else -> Color.Transparent
    }

    val labelTextColor = when {
        isError -> Alert01
        else -> Gray03
    }

    BasicTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { newValue ->
            if (newValue.replace(" ", "").length <= maxLength) onValueChange(newValue)
        },
        singleLine = maxLines == 1,
        textStyle = textStyle.copy(Gray01),
        maxLines = if (minLines > maxLines) minLines else maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(ViskitYellow500),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        decorationBox = { innerText ->
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    modifier = Modifier
                        .heightIn(minHeight)
                        .fillMaxWidth()
                        .clip(shape = shape)
                        .background(color = Gray08)
                        .border(
                            width = 1.dp,
                            color = borderLineColor,
                            shape = shape,
                        )
                        .padding(vertical = 16.dp, horizontal = 18.dp),
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Gray06,
                            style = RecordyTheme.typography.body2M,
                            maxLines = 1,
                            overflow = TextOverflow.Clip,
                        )
                    }
                    innerText()
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                ) {
                    Text(
                        text = labelText,
                        modifier = Modifier.align(Alignment.CenterStart),
                        color = labelTextColor,
                        style = RecordyTheme.typography.caption2R,
                        maxLines = 1,
                    )
                    Text(
                        text = "${value.length} / $maxLength",
                        modifier = Modifier.align(Alignment.CenterEnd),
                        color = Gray06,
                        style = RecordyTheme.typography.caption2R,
                        maxLines = 1,
                    )
                }
            }
        },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun TextFieldPreview() {
    RecordyTheme {
        var normalValue by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .background(Black)
                .padding(vertical = 10.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            RecordyBasicTextField(
                placeholder = "레코디",
                value = normalValue,
                onValueChange = { normalValue = it },
            )
            RecordyBasicTextField(
                placeholder = "레코디",
                value = normalValue,
                labelText = "사용 가능한 닉네임이에요",
                onValueChange = { normalValue = it },
            )
            RecordyBasicTextField(
                placeholder = "레코디",
                isError = true,
                value = normalValue,
                labelText = "ⓘ 이미 사용중인 닉네임이에요",
                onValueChange = { normalValue = it },
            )

            RecordyBasicTextField(
                placeholder = "레코디",
                maxLines = 20,
                maxLength = 300,
                minHeight = 148.dp,
                value = normalValue,
                onValueChange = { normalValue = it },
            )
        }
    }
}
