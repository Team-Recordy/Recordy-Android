package com.record.search.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.theme.RecordyTheme
import kotlinx.coroutines.delay

@Composable
fun SearchBox(
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

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
            .padding(vertical = 14.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_search_24),
            contentDescription = "Search Icon",
            modifier = Modifier
                .wrapContentSize()
        )

        BasicTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .focusRequester(focusRequester)
                .padding(start = 8.dp)
        )

        Text(
            text = if (searchText.text.isEmpty()) "어떤 공간을 원하시나요?" else "",
            style = RecordyTheme.typography.body1M,
            color = RecordyTheme.colors.gray05,
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 8.dp)
        )
    }
}

@Preview
@Composable
fun SearchBoxView() {
    RecordyTheme {
        SearchBox(modifier = Modifier.wrapContentSize())
    }
}
