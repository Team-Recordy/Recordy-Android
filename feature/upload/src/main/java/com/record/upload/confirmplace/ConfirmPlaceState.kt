package com.record.upload.confirmplace

import com.record.exhibition.model.PlaceUsingMap
import com.record.model.AlertInfo
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

data class ConfirmPlaceState(
    val place: PlaceUsingMap = PlaceUsingMap(),
    val alertInfo: AlertInfo = AlertInfo(),
) : UiState

sealed interface ConfirmPlaceSideEffect : SideEffect
