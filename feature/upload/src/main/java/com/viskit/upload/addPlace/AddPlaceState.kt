package com.viskit.upload.addPlace

import com.viskit.exhibition.model.PlaceUsingMap
import com.viskit.ui.base.SideEffect
import com.viskit.ui.base.UiState
import com.viskit.upload.navigation.UploadRoute
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
