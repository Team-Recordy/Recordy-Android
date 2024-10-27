package com.record.upload.confirmplace

import com.record.exhibition.model.PlaceUsingMap
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

data class ConfirmPlaceState(
    val place:PlaceUsingMap=PlaceUsingMap()
) : UiState

sealed interface ConfirmPlaceSideEffect : SideEffect
