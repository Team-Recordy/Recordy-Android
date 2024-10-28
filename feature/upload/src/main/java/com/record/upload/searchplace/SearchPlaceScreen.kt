package com.record.upload.searchplace

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.record.designsystem.R
import com.record.designsystem.component.button.BasicButton
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.component.searchcomponent.SearchBox
import com.record.designsystem.component.searchcomponent.SearchedContainerBtn
import com.record.designsystem.component.searchcomponent.SearchingContainerBtn
import com.record.designsystem.theme.RecordyTheme
import com.record.exhibition.model.SearchResult
import com.record.ui.extension.customClickable
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import com.record.upload.navigation.UploadRoute
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchPlaceScreenRoute(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: SearchPlaceViewModel = hiltViewModel(),
    popBackStackArgument: (UploadRoute.Upload) -> Unit,
    navigateToAddPlace: () -> Unit,
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is SearchSideEffect.PopBackStack -> popBackStack()
                is SearchSideEffect.NavigateToAddPlace -> navigateToAddPlace()
                is SearchSideEffect.PopBackStackArgument -> {
                    popBackStackArgument(sideEffect.place)
                }
            }
        }
    }
    SearchPlaceScreen(
        modifier = modifier,
        uiState = uiState,
        onQueryChange = viewModel::onQueryChanged,
        popBackStackArgument = viewModel::popBackStackArgument,
        navigateToAddPlace = viewModel::navigateToAddPlaceScreen,
        popBackStack = viewModel::popBackStack,
    )
}

@Composable
fun SearchPlaceScreen(
    modifier: Modifier,
    uiState: SearchState,
    onQueryChange: (String) -> Unit,
    popBackStackArgument: (UploadRoute.Upload) -> Unit,
    navigateToAddPlace: () -> Unit,
    popBackStack: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var showSearchedContainer by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(color = RecordyTheme.colors.black)
            .padding(horizontal = 16.dp),
    ) {
        TopNavigationBar(
            modifier = Modifier,
            title = "장소",
            enableGradation = true,
            popBackStackEnable = true,
            popBackStack = popBackStack,
        )

        SearchBoxSection(
            modifier = modifier,
            query = uiState.query,
            onQueryChange = onQueryChange,
            onFocusChanged = { focusState ->
                if (focusState.isFocused) {
                    showSearchedContainer = false
                }
            },
            onImageClick = {
                showSearchedContainer = true
                keyboardController?.hide()
            },
        )

        SearchResultSection(
            showSearchedContainer = showSearchedContainer,
            query = uiState.query,
            items = uiState.filteredItems,
            modifier = modifier,
            navigateToAddPlace = navigateToAddPlace,
            popBackStackArgument = popBackStackArgument,
        )
    }
}

@Composable
fun SearchBoxSection(
    modifier: Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onFocusChanged: (FocusState) -> Unit,
    onImageClick: () -> Unit,
) {
    SearchBox(
        modifier = modifier.onFocusChanged(onFocusChanged),
        query = query,
        onQueryChange = {
            onQueryChange(it)
        },
        onImageClick = onImageClick,
    )
}

@Composable
fun SearchResultSection(
    showSearchedContainer: Boolean,
    query: String,
    items: List<SearchResult>,
    modifier: Modifier,
    navigateToAddPlace: () -> Unit,
    popBackStackArgument: (UploadRoute.Upload) -> Unit,
) {
    when {
        showSearchedContainer -> {
            SearchedResultList(
                items = items,
                modifier = modifier,
                navigateToAddPlace = navigateToAddPlace,
            )
        }
        query.isNotEmpty() -> {
            if (items.isEmpty()) {
                EmptySearchResult(onButtonClick = navigateToAddPlace)
            } else {
                SearchingResultList(
                    items = items,
                    modifier = modifier,
                    popBackStackArgument = popBackStackArgument,
                )
            }
        }
    }
}

@Composable
fun SearchedResultList(
    items: List<SearchResult>,
    modifier: Modifier,
    navigateToAddPlace: () -> Unit,
) {
    LazyColumn {
        items(items, key = { it.id }) { item ->
            SearchedResultItem(item = item, modifier = modifier)
        }
        if (items.isEmpty()) {
            item {
                EmptySearchResult(onButtonClick = navigateToAddPlace)
            }
        }
    }
}

@Composable
fun SearchedResultItem(
    item: SearchResult,
    modifier: Modifier,
) {
    Column {
        SearchedContainerBtn(
            modifier = modifier
                .fillMaxWidth()
                .customClickable {},
            exhibitionName = item.name,
            location = item.address,
            venue = item.name,
        )
        HorizontalDivider(
            modifier = modifier.fillMaxWidth(),
            color = RecordyTheme.colors.gray09,
        )
    }
}

@Composable
private fun SearchingResultList(
    items: List<SearchResult>,
    modifier: Modifier,
    popBackStackArgument: (UploadRoute.Upload) -> Unit,
) {
    LazyColumn {
        items(items, key = { it.id }) { item ->
            SearchingContainerBtn(
                modifier = modifier
                    .fillMaxWidth()
                    .customClickable {
                        popBackStackArgument(
                            UploadRoute.Upload(
                                id = item.id,
                                address = item.address,
                                name = item.name,
                            ),
                        )
                    },
                exhibitionName = item.name,
                location = item.address,
                venue = item.name,
            )
        }
    }
}

@Composable
fun EmptySearchResult(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = RecordyTheme.colors.black)
            .systemBarsPadding()
            .imePadding(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_alert_warning_80),
                contentDescription = "Empty Icon",
                contentScale = ContentScale.Fit,
                alpha = 1f,
                modifier = Modifier.padding(bottom = 18.dp),
            )
            Text(
                text = "검색 결과가 없어요.\n검색어가 정확한지 확인해주세요!",
                color = RecordyTheme.colors.gray01,
                style = RecordyTheme.typography.title2,
                textAlign = TextAlign.Center,
            )
            BasicButton(
                modifier = Modifier
                    .fillMaxWidth(0.33f)
                    .padding(top = 23.dp),
                text = "영상 업로드하기",
                textStyle = RecordyTheme.typography.body2B,
                textColor = RecordyTheme.colors.background,
                backgroundColor = RecordyTheme.colors.viskitYellow400,
                padding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                shape = RoundedCornerShape(30.dp),
                onClick = onButtonClick,
            )
        }
    }
}
