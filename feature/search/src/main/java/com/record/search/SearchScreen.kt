package com.record.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.record.designsystem.R
import com.record.designsystem.component.searchcomponent.SearchBox
import com.record.designsystem.component.searchcomponent.SearchedContainerBtn
import com.record.designsystem.component.searchcomponent.SearchingContainerBtn
import com.record.designsystem.theme.RecordyTheme
import com.record.exhibition.model.ResultType
import com.record.exhibition.model.SearchResult
import com.record.ui.extension.customClickable
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchRoute(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToPlaceDetail: (Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is SearchSideEffect.navigateToDetail -> {
                    navigateToPlaceDetail(sideEffect.id)
                }
            }
        }
    }
    SearchScreen(
        modifier = modifier.padding(bottom = paddingValues.calculateBottomPadding()),
        query = uiState.query,
        onQueryChange = viewModel::onQueryChanged,
        items = uiState.filteredItems,
        navigateToPlaceDetail = viewModel::navigateToDetail,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    items: List<SearchResult>,
    navigateToPlaceDetail: (Long) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var showSearchedContainer by remember { mutableStateOf(false) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                focusManager.clearFocus()
                return Offset.Zero
            }
        }
    }

    Column(
        modifier = modifier
            .background(color = RecordyTheme.colors.background)
            .systemBarsPadding()
            .padding(horizontal = 16.dp)
            .padding(top = 28.dp)
            .pointerInput(Unit) {
                detectTapGestures(onPress = {
                    focusManager.clearFocus()
                },)
            }
            .nestedScroll(nestedScrollConnection),
    ) {
        SearchBox(
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        showSearchedContainer = false
                    }
                }
                .focusTarget(),
            query = query,
            onQueryChange = {
                onQueryChange(it)
                showSearchedContainer = false
            },
            onImageClick = {
                showSearchedContainer = true
                focusRequester.requestFocus()
                keyboardController?.hide()
            },
        )

        if (showSearchedContainer) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(items) { item ->
                    Column {
                        SearchedContainerBtn(
                            modifier = Modifier.fillMaxWidth()
                                .customClickable {
                                    navigateToPlaceDetail(item.id)
                                },
                            exhibitionName = item.name,
                            location = item.address,
                            venue = when (item.type) {
                                ResultType.PLACE -> "전시관"
                                ResultType.EXHIBITION -> "전시회"
                                ResultType.UNKNOWN -> "기타"
                            },
                        )
                        HorizontalDivider(
                            modifier = Modifier
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
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(items) { item ->
                        SearchingContainerBtn(
                            modifier = Modifier
                                .background(RecordyTheme.colors.background)
                                .fillMaxWidth()
                                .customClickable {
                                    navigateToPlaceDetail(item.id)
                                },
                            exhibitionName = item.name,
                            location = item.address,
                            venue = when (item.type) {
                                ResultType.PLACE -> "전시관"
                                ResultType.EXHIBITION -> "전시회"
                                ResultType.UNKNOWN -> "기타"
                            },
                        )
                    }
                }
            }
        } else {
            DefaultSearchUI()
        }
    }
}

@Composable
fun EmptySearchResult(showSearchedContainer: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = RecordyTheme.colors.background)
            .systemBarsPadding()
            .imePadding(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_viskit_noresult),
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

@Composable
fun DefaultSearchUI() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_viskit_search),
            contentDescription = "Icon",
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterVertically)
                .padding(end = 12.dp),
        )
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = "공간뿐만 아니라 원하는 전시회를 찾고 싶다면?",
                style = RecordyTheme.typography.caption1M,
                color = RecordyTheme.colors.gray05,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
            ) {
                Text(
                    text = "\'전시회명\'",
                    style = RecordyTheme.typography.subtitle,
                    color = RecordyTheme.colors.viskitYellow300,
                )

                Text(
                    text = "을 검색해 보세요!",
                    style = RecordyTheme.typography.subtitle,
                    color = RecordyTheme.colors.gray01,
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchRoutePreview() {
    RecordyTheme {
    }
}
