package com.record.upload.searchplace

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.record.designsystem.R
import com.record.designsystem.component.SearchBox
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable
import com.record.upload.searchplace.component.Searched1ContainerBtn
import com.record.upload.searchplace.component.SearchingContainerBtn

@Composable
fun SearchPlaceScreenRoute(
    paddingValues: PaddingValues,
    modifier:Modifier=Modifier,
    viewModel: SearchPlaceViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    SearchPlaceScreen(
        modifier = modifier,
        query = uiState.query,
        onQueryChange = viewModel::onQueryChanged,
        items = uiState.filteredItems,
    )
}


@Composable
fun SearchPlaceScreen(
    modifier: Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    items: List<com.record.exhibition.model.SearchResult>,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var showSearchedContainer by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(color = RecordyTheme.colors.black)
            .systemBarsPadding()
            .padding(horizontal = 16.dp, vertical = 28.dp),
    ) {
        SearchBox(
            modifier = modifier
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        showSearchedContainer = false
                    }
                },
            query = query,
            onQueryChange = {
                onQueryChange(it)
                showSearchedContainer = false
            },
            onImageClick = {
                showSearchedContainer = true
                keyboardController?.hide()
            },
        )

        if (showSearchedContainer) {
            LazyColumn {
                items(items) { item ->
                    Column {
                        Searched1ContainerBtn(
                            modifier = modifier.fillMaxWidth().customClickable {
                                Log.d("searchSak1","$item")
                            },
                            exhibitionName = item.name,
                            location = item.address,
                            venue = item.name,
                            type = item.type,
                        )
                        HorizontalDivider(
                            modifier = modifier
                                .fillMaxWidth(),
                            color = RecordyTheme.colors.gray09,
                        )
                    }
                }
                if (items.isEmpty()) {
                    item {
                        EmptySearchResult(true)
                    }
                }
            }
        } else if (query.isNotEmpty()) {
            if (items.isEmpty()) {
                EmptySearchResult(false)
            } else {
                LazyColumn {
                    items(items) { item ->
                        SearchingContainerBtn(
                            modifier = modifier.fillMaxWidth().customClickable {
                                Log.d("searchSak","$item")
                            },
                            exhibitionName = item.name,
                            location = item.address,
                            venue = item.name,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptySearchResult(showSearchedContainer: Boolean) {
    val imePadding = Modifier.imePadding()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = RecordyTheme.colors.black)
            .systemBarsPadding()
            .then(imePadding),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_recordy_logo),
                contentDescription = "Empty Icon",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 18.dp),
            )

            if (showSearchedContainer) {
                Text(
                    text = "검색 결과가 없어요.",
                    color = RecordyTheme.colors.gray01,
                    style = RecordyTheme.typography.title2,
                    textAlign = TextAlign.Center,
                )
            } else {
                Text(
                    text = "검색 결과가 없어요.\n검색어가 정확한지 확인해주세요!",
                    color = RecordyTheme.colors.gray01,
                    style = RecordyTheme.typography.title2,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}