package com.record.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Button(onClick = {}) {
            Text("test")
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun LoginView() {
    LoginRoute(PaddingValues(10.dp), Modifier)
}
