package com.viskit.upload.searchplace

import com.record.exhibition.model.SearchResult
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.upload.navigation.UploadRoute
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class SearchState(
    val query: String = "",
    val filteredItems: ImmutableList<SearchResult> = emptyList<SearchResult>().toImmutableList(),
) : UiState

sealed interface SearchSideEffect : SideEffect {

    data object PopBackStack : SearchSideEffect
    data object NavigateToAddPlace : SearchSideEffect
    data class PopBackStackArgument(val place: UploadRoute.Upload) :
        SearchSideEffect
}
