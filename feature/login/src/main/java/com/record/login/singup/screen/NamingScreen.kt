package com.record.login.singup.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.record.designsystem.component.textfield.RecordyValidateTextfield
import com.record.designsystem.theme.RecordyTheme
import com.record.login.singup.SignUpState
import kotlinx.coroutines.delay

@Composable
fun NamingScreen(uiState: SignUpState, onTextChangeEvent: (String) -> Unit, onInputComplete: () -> Unit) {
    var lastInputTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(modifier = Modifier.height(55.dp))
        Text(
            text = "비스킷에서 사용할 \n닉네임을 작성해 주세요.",
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth(),
            style = RecordyTheme.typography.title1,
            color = RecordyTheme.colors.gray01,
        )
        Spacer(modifier = Modifier.height(30.dp))
        RecordyValidateTextfield(
            errorState = uiState.nicknameValidate,
            onValueChange = { text ->
                onTextChangeEvent(text)
                lastInputTime = System.currentTimeMillis()
            },
            padding = PaddingValues(),
        )
    }

    LaunchedEffect(lastInputTime) {
        delay(300)
        if (System.currentTimeMillis() - lastInputTime >= 300) {
            onInputComplete()
        }
    }
}
