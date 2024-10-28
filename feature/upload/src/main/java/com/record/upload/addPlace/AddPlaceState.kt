package com.record.upload.addPlace

import com.record.exhibition.model.PlaceUsingMap
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.upload.navigation.UploadRoute
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class AddPlaceState(
    val query: String = "",
    val filteredItems: ImmutableList<PlaceUsingMap> = emptyList<PlaceUsingMap>().toImmutableList(),
) : UiState

sealed interface AddPlaceSideEffect : SideEffect {
    data object PopBackStack : AddPlaceSideEffect
    data class NavigateToConfirmPlaceScreen(val confirmPlace: UploadRoute.ConfirmPlace) : AddPlaceSideEffect
}
