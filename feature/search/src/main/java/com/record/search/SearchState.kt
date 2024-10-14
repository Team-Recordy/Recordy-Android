package com.record.search

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

data class SearchState(
    val query: String = "",
    val filteredItems: List<Exhibition> = emptyList(),
) : UiState

sealed interface SearchSideEffect : SideEffect
