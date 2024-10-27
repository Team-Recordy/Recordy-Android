package com.record.upload.addPlace

import com.record.exhibition.model.SearchResult
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class AddPlaceState(
    val query: String = "",
    val filteredItems: ImmutableList<SearchResult> = emptyList<SearchResult>().toImmutableList(),
) : UiState

sealed interface SearchSideEffect : SideEffect
