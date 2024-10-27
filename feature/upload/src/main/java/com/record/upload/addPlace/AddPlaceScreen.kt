package com.record.upload.addPlace

import android.util.Log
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
import com.record.designsystem.component.SearchBox
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.RecordyTheme
import com.record.exhibition.model.PlaceUsingMap
import com.record.ui.extension.customClickable
import com.record.upload.navigation.UploadRoute
import com.record.upload.searchplace.SearchPlaceViewModel
import com.record.upload.searchplace.component.Searched1ContainerBtn
import com.record.upload.searchplace.component.SearchingContainerBtn
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun AddPlaceScreenRoute(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: AddPlaceViewModel=hiltViewModel(),
    popBackStackArgument: (UploadRoute.Upload) -> Unit,
    navigateToConfirmPlace: (UploadRoute.ConfirmPlace) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()
    AddPlaceScreen(
        modifier = modifier,
        query = uiState.query,
        onQueryChange = viewModel::onQueryChanged,
        items = uiState.filteredItems,
        popBackStackArgument = popBackStackArgument,
        navigateToConfirmPlace=navigateToConfirmPlace
    )
}

@Composable
fun AddPlaceScreen(
    modifier: Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    items: List<PlaceUsingMap>,
    popBackStackArgument: (UploadRoute.Upload) -> Unit,
    navigateToConfirmPlace: (UploadRoute.ConfirmPlace) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var showSearchedContainer by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(color = RecordyTheme.colors.black)
            .systemBarsPadding()
            .padding(horizontal = 16.dp, vertical = 28.dp),
    ) {
        TopNavigationBar(modifier = Modifier, title = "내용 작성", enableGradation = true, popBackStackEnable = true,)
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
                                Log.d("searchSak1", "$item")
                                navigateToConfirmPlace(
                                    UploadRoute.ConfirmPlace(
                                        name = item.name,
                                        address = item.address
                                    )
                                )
                            },
                            exhibitionName = item.name,
                            location = item.address,
                            venue = item.name,
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
                                Log.d("searchSak", "$item")
                                navigateToConfirmPlace(
                                    UploadRoute.ConfirmPlace(
                                        name = item.name,
                                        address = item.address
                                    )
                                )
                            },
                            exhibitionName = item.name,
                            location = item.address,
                            venue = item.name,
                        )
                    }
                }
            }
        }else{
            DefaultSearchUI()
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
                painter = painterResource(id = R.drawable.jpeg_not_search),
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
            painter = painterResource(id = R.drawable.ic_tab_record_pressed_28),
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