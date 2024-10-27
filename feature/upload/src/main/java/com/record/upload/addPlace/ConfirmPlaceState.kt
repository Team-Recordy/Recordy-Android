package com.record.upload.addPlace

import com.record.exhibition.model.PlaceUsingMap
import com.record.exhibition.model.SearchResult
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ConfirmPlaceState(
    val place:PlaceUsingMap=PlaceUsingMap()
) : UiState

sealed interface ConfirmPlaceSideEffect : SideEffect
