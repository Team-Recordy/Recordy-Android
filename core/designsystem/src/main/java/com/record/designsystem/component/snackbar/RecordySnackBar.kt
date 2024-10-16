package com.record.designsystem.component.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.record.designsystem.R
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable

@Composable
fun RecordySnackBar(
    modifier: Modifier = Modifier,
    visible: Boolean,
    message: String,
    snackBarType: SnackBarType,
    onClick: () -> Unit = {},
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Popup(
                alignment = Alignment.BottomCenter,
            ) {
                RecordySnackBarContent(
                    modifier = Modifier.customClickable { onClick() },
                    snackBarType = snackBarType,
                    message = message,
                )
            }
        }
    }
}

@Composable
fun RecordySnackBarContent(
    modifier: Modifier = Modifier,
    snackBarType: SnackBarType,
    message: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
            )
            .padding(
                bottom = 16.dp,
            )
            .background(
                color = RecordyTheme.colors.background,
                shape = RoundedCornerShape(8.dp),
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (snackBarType) {
            SnackBarType.WARNING -> {
                Icon(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 10.dp, bottom = 10.dp, end = 4.dp),
                    painter = painterResource(id = R.drawable.ic_exclamation_24),
                    contentDescription = "warning",
                    tint = RecordyTheme.colors.alert01,
                )
            }

            SnackBarType.CHECK -> {
                Icon(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 10.dp, bottom = 10.dp, end = 4.dp),
                    painter = painterResource(id = R.drawable.ic_check_small_24),
                    contentDescription = "check",
                    tint = RecordyTheme.colors.viskitYellow500,
                )
            }
        }
        Text(
            text = message,
            style = RecordyTheme.typography.body2L,
            color = RecordyTheme.colors.gray01,
        )
    }
}

@Preview
@Composable
fun PreviewSnackBar() {
    RecordyTheme {
        RecordySnackBar(visible = true, message = "아아아아아아아아아아아아아", snackBarType = SnackBarType.CHECK, onClick = {})
    }
}
