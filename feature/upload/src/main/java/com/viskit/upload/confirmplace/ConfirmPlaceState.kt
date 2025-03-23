package com.viskit.upload.confirmplace

import com.viskit.exhibition.model.PlaceUsingMap
import com.viskit.model.AlertInfo
import com.viskit.ui.base.SideEffect
import com.viskit.ui.base.UiState

data class ConfirmPlaceState(
    val place: PlaceUsingMap = PlaceUsingMap(),
    val alertInfo: AlertInfo = AlertInfo(),
) : UiState

sealed interface ConfirmPlaceSideEffect : SideEffect {
    data object PopBackStack : ConfirmPlaceSideEffect
    data object NavigateToUpload : ConfirmPlaceSideEffect
}
