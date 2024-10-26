package com.record.home

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class HomeState(
    val exhibitionList: ImmutableList<Exhibition> = emptyList<Exhibition>().toImmutableList(),
    val isLoading: Boolean = false,
    val location: Location = Location(0.0, 0.0),
    val showLocationPermissionDialog: Boolean = true,
) : UiState

sealed interface HomeSideEffect : SideEffect {
    data object navigateToUpload : HomeSideEffect
    data class navigateToVideo(val id: Long, val location: String) : HomeSideEffect
    data class navigateToDetail(val id: Long) : HomeSideEffect
    data object launchSettingIntent : HomeSideEffect
}
