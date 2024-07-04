package com.record.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.RecordyTheme
import com.record.model.ValidateResult

@Composable
fun RecordyValidateTextfield(
    modifier: Modifier = Modifier,
    errorState: ValidateResult = ValidateResult.ValidationError,
    placeholder: String = "EX) 레코디둥이들",
    maxLines: Int = 1,
    maxLength: Int = 10,
    textStyle: TextStyle = RecordyTheme.typography.body2M,
    onValueChange: (String) -> Unit = { _ -> },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    padding: PaddingValues = PaddingValues(horizontal = 16.dp),
    overlapErrorMessage: String = "이미 사용중인 닉네임이에요",
    validationErrorMessage: String = "한글, 숫자, 밑줄 및 마침표만 사용할 수 있어요",
    successMessage: String = "사용 가능한 닉네임이에요",
    inputtingMessage: String = "",
) {
    var value by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(8.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .clip(shape),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(color = RecordyTheme.colors.gray08, shape = shape)
                .border(
                    width = 1.dp,
                    color = if (isFocused) {
                        if (errorState == ValidateResult.OverlapError || errorState == ValidateResult.ValidationError) {
                            RecordyTheme.colors.alert
                        } else {
                            RecordyTheme.colors.main
                        }
                    } else {
                        Color.Transparent
                    },
                    shape = shape,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = value,
                onValueChange = {
                    if (it.length <= maxLength && !it.contains(" ")) {
                        value = it
                        onValueChange(it)
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 18.dp)
                    .onFocusChanged { isFocused = it.isFocused }
                    .wrapContentHeight(Alignment.CenterVertically),
                textStyle = RecordyTheme.typography.body2M.copy(color = RecordyTheme.colors.gray01),
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = maxLines == 1,
            ) { innerTextField ->
                if (value.isEmpty() && !isFocused) {
                    Text(
                        text = placeholder,
                        style = RecordyTheme.typography.body2M.copy(color = RecordyTheme.colors.gray04),
                    )
                }
                innerTextField()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = when (errorState) {
                    ValidateResult.OverlapError -> overlapErrorMessage
                    ValidateResult.ValidationError -> validationErrorMessage
                    ValidateResult.Success -> successMessage
                    ValidateResult.Inputting -> inputtingMessage
                },
                color = when (errorState) {
                    ValidateResult.OverlapError, ValidateResult.ValidationError -> RecordyTheme.colors.alert
                    ValidateResult.Success -> RecordyTheme.colors.gray03
                    ValidateResult.Inputting -> RecordyTheme.colors.gray03
                },
                style = RecordyTheme.typography.caption2,
            )
            Text(
                text = "${value.length} / $maxLength",
                style = RecordyTheme.typography.caption2,
                color = RecordyTheme.colors.gray04,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordyValidateTextfieldPreview() {
    RecordyTheme {
        var text by remember { mutableStateOf("") }
        var validationState by remember { mutableStateOf<ValidateResult>(ValidateResult.Inputting) }

        // 임의로 추가해놓은 함수 - Validate 별 class는 맨 위의 선언부에서 바꾸어서 확인해야함!
        fun validateNickname(input: String) {
            validationState = when {
                input.isEmpty() -> ValidateResult.Inputting
                input == "사용중" -> ValidateResult.OverlapError
                input.contains("!") -> ValidateResult.ValidationError
                else -> ValidateResult.Success
            }
        }

        Column {
            RecordyValidateTextfield(
                placeholder = "EX) 레코디둥이들",
                errorState = validationState,
                onValueChange = {
                    text = it
                    validateNickname(it)
                },
                overlapErrorMessage = "이미 사용중인 닉네임이에요",
                validationErrorMessage = "한글, 숫자, 밑줄 및 마침표만 사용할 수 있어요",
                successMessage = "사용 가능한 닉네임이에요",
                inputtingMessage = "",
            )
        }
    }
}
