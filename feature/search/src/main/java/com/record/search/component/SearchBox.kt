package com.record.search.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.record.designsystem.R
import com.record.designsystem.theme.RecordyTheme
import kotlinx.coroutines.delay

@Composable
fun SearchBox(
    modifier: Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onImageClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        delay(300)
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = RecordyTheme.colors.gray10)
            .fillMaxWidth()
            .padding(vertical = 14.dp, horizontal = 16.dp)
            .clickable {
                focusRequester.requestFocus()
                keyboardController?.show()
            },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_search_24),
            contentDescription = "Search Icon",
            modifier = Modifier
                .clickable {
                    onImageClick()
                    keyboardController?.hide()
                }
        )

        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
                .focusRequester(focusRequester),
            textStyle = RecordyTheme.typography.body1M.copy(color = RecordyTheme.colors.gray01),
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    Text(
                        text = "어떤 공간을 원하시나요?",
                        style = RecordyTheme.typography.body1M,
                        color = RecordyTheme.colors.gray05
                    )
                }
                innerTextField()
            }
        )
    }
}

@Preview
@Composable
fun SearchBoxPreview() {
    RecordyTheme {
        SearchBox(
            modifier = Modifier.fillMaxWidth(),
            query = "",
            onQueryChange = {},
            onImageClick = {},
        )
    }
}
