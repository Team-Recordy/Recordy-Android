package com.record.search

import com.record.ui.base.UiState
import com.record.ui.base.SideEffect

data class SearchState(
    val query: String = "",
    val filteredItems: List<Exhibition> = emptyList()
) : UiState

sealed interface SearchSideEffect : SideEffect
