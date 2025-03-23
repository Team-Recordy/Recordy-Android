package com.viskit.search

import com.viskit.exhibition.model.SearchResult
import com.viskit.ui.base.SideEffect
import com.viskit.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class SearchState(
    val query: String = "",
    val filteredItems: ImmutableList<SearchResult> = emptyList<SearchResult>().toImmutableList(),
) : UiState

sealed interface SearchSideEffect : SideEffect {
    data class navigateToDetail(val id: Long) : SearchSideEffect
}
