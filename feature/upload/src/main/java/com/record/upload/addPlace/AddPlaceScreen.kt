package com.record.upload.addPlace

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.record.designsystem.R
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.component.searchcomponent.SearchBox
import com.record.designsystem.component.searchcomponent.SearchedContainerBtn
import com.record.designsystem.component.searchcomponent.SearchingContainerBtn
import com.record.designsystem.theme.RecordyTheme
import com.record.exhibition.model.PlaceUsingMap
import com.record.ui.extension.customClickable
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import com.record.upload.navigation.UploadRoute
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddPlaceScreenRoute(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: AddPlaceViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateToConfirmPlace: (UploadRoute.ConfirmPlace) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is AddPlaceSideEffect.PopBackStack -> popBackStack()

                is AddPlaceSideEffect.NavigateToConfirmPlaceScreen -> {
                    navigateToConfirmPlace(sideEffect.confirmPlace)
                }
            }
        }
    }
    AddPlaceScreen(
        modifier = modifier,
        uiState = uiState,
        onQueryChange = viewModel::onQueryChanged,
        popBackStack = viewModel::popBackStack,
        navigateToConfirmPlace = viewModel::navigateToConfirmPlace,
    )
}

@Composable
fun AddPlaceScreen(
    modifier: Modifier,
    uiState: AddPlaceState,
    onQueryChange: (String) -> Unit,
    popBackStack: () -> Unit,
    navigateToConfirmPlace: (UploadRoute.ConfirmPlace) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var showSearchedContainer by remember { mutableStateOf(false) }

    val searchBoxModifier = remember {
        Modifier.onFocusChanged { focusState ->
            if (focusState.isFocused) {
                showSearchedContainer = false
            }
        }
    }

    Column(
        modifier = Modifier
            .background(color = RecordyTheme.colors.background)
            .padding(horizontal = 16.dp),
    ) {
        TopNavigationBar(
            title = "장소 등록",
            enableGradation = true,
            popBackStackEnable = true,
            popBackStack = popBackStack,
        )

        SearchBox(
            modifier = searchBoxModifier,
            query = uiState.query,
            onQueryChange = { newQuery ->
                onQueryChange(newQuery)
                showSearchedContainer = false
            },
            onImageClick = {
                showSearchedContainer = true
                keyboardController?.hide()
            },
        )

        SearchResults(
            query = uiState.query,
            items = uiState.filteredItems,
            showSearchedContainer = showSearchedContainer,
            navigateToConfirmPlace = navigateToConfirmPlace,
            modifier = modifier,
        )
    }
}

@Composable
private fun SearchResults(
    query: String,
    items: List<PlaceUsingMap>,
    showSearchedContainer: Boolean,
    navigateToConfirmPlace: (UploadRoute.ConfirmPlace) -> Unit,
    modifier: Modifier,
) {
    when {
        showSearchedContainer -> {
            SearchResultsList(
                items = items,
                isSearched = true,
                navigateToConfirmPlace = navigateToConfirmPlace,
                modifier = modifier,
            )
        }
        query.isNotEmpty() -> {
            if (items.isEmpty()) {
                EmptySearchResult(showSearchedContainer = false)
            } else {
                SearchResultsList(
                    items = items,
                    isSearched = false,
                    navigateToConfirmPlace = navigateToConfirmPlace,
                    modifier = modifier,
                )
            }
        }
        else -> DefaultSearchUI()
    }
}

@Composable
private fun SearchResultsList(
    items: List<PlaceUsingMap>,
    isSearched: Boolean,
    navigateToConfirmPlace: (UploadRoute.ConfirmPlace) -> Unit,
    modifier: Modifier,
) {
    if (items.isEmpty() && isSearched) {
        EmptySearchResult(showSearchedContainer = true)
        return
    }

    LazyColumn {
        items(
            items = items,
            key = { it.platformPlaceId }, // Stable key for better performance
        ) { item ->
            if (isSearched) {
                SearchedResultItem(
                    item = item,
                    navigateToConfirmPlace = navigateToConfirmPlace,
                    modifier = modifier,
                )
            } else {
                SearchingResultItem(
                    item = item,
                    navigateToConfirmPlace = navigateToConfirmPlace,
                    modifier = modifier,
                )
            }
        }
    }
}

@Composable
private fun SearchedResultItem(
    item: PlaceUsingMap,
    navigateToConfirmPlace: (UploadRoute.ConfirmPlace) -> Unit,
    modifier: Modifier,
) {
    Column {
        SearchedContainerBtn(
            modifier = modifier
                .fillMaxWidth()
                .customClickable {
                    navigateToConfirmPlace(
                        UploadRoute.ConfirmPlace(
                            name = item.name,
                            address = item.address,
                        ),
                    )
                },
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
private fun SearchingResultItem(
    item: PlaceUsingMap,
    navigateToConfirmPlace: (UploadRoute.ConfirmPlace) -> Unit,
    modifier: Modifier,
) {
    val confirmPlace = remember(item) {
        UploadRoute.ConfirmPlace(
            name = item.name,
            address = item.address,
            latitude = item.latitude,
            longitude = item.longitude,
            placeId = item.platformPlaceId,
        )
    }

    SearchingContainerBtn(
        modifier = modifier
            .fillMaxWidth()
            .customClickable { navigateToConfirmPlace(confirmPlace) },
        exhibitionName = item.name,
        location = item.address,
        venue = item.name,
    )
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
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 18.dp),
            )

            val text = if (showSearchedContainer) {
                "검색 결과가 없어요."
            } else {
                "검색 결과가 없어요.\n검색어가 정확한지 확인해주세요!"
            }

            Text(
                text = text,
                color = RecordyTheme.colors.gray01,
                style = RecordyTheme.typography.title2,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun DefaultSearchUI() {
    val rowModifier = remember {
        Modifier
            .fillMaxWidth()
            .padding(top = 28.dp)
    }
    Row(modifier = rowModifier) {
        Image(
            painter = painterResource(id = R.drawable.ic_intro_search_40),
            contentDescription = null,
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterVertically)
                .padding(end = 12.dp),
        )

        DefaultSearchTexts()
    }
}

@Composable
private fun DefaultSearchTexts() {
    Column(modifier = Modifier) {
        Text(
            modifier = Modifier.padding(bottom = 2.dp),
            text = "방문한 장소가 없다면?",
            style = RecordyTheme.typography.caption1M,
            color = RecordyTheme.colors.gray05,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
        ) {
            Text(
                text = "\'직접 장소를 등록\'",
                style = RecordyTheme.typography.subtitle,
                color = RecordyTheme.colors.viskitYellow300,
            )
            Text(
                text = "해보세요!",
                style = RecordyTheme.typography.subtitle,
                color = RecordyTheme.colors.gray01,
            )
        }
    }
}
