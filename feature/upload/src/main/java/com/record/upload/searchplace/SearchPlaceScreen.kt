package com.record.upload.searchplace

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.theme.RecordyTheme
import com.record.designsystem.theme.White

@Composable
fun SearchPlaceScreenRoute(
    paddingValues: PaddingValues,
) {
    SearchPlaceScreen(
        modifier =  Modifier.padding(paddingValues =paddingValues)
    )
}

@Composable
fun SearchPlaceScreen(
    modifier: Modifier=Modifier,
) {
    LazyColumn(
        modifier=Modifier
            .fillMaxSize()
            .padding(
            top = 45.dp,)
    ) {
        item {
            Box(modifier=Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_angle_left_24),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "장소",
                    color = White,
                    style = RecordyTheme.typography.title3,
                )
            }
        }
//        item {
//            SearchBox(
//                modifier = modifier
//                    .onFocusChanged { focusState ->
//                        if (focusState.isFocused) {
//                            showSearchedContainer = false
//                        }
//                    },
//                query = query,
//                onQueryChange = {
//                    onQueryChange(it)
//                    showSearchedContainer = false
//                },
//                onImageClick = {
//                    showSearchedContainer = true
//                    keyboardController?.hide()
//                },
//            )
//        }
    }
}
