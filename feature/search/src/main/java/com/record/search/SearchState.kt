package com.record.search

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class SearchState(
    val query: String = "",
    val filteredItems: ImmutableList<ExhibitionData> = emptyList<ExhibitionData>().toImmutableList(),
) : UiState

sealed interface SearchSideEffect : SideEffect
