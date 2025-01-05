package com.record.home

import com.record.exhibition.model.Place
import com.record.model.VideoType
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class HomeState(
    val exhibitionList: ImmutableList<Place> = emptyList<Place>().toImmutableList(),
    val isLoading: Boolean = false,
    val location: Location = Location(0.0, 0.0),
    val page: Int = 0,
    val isEnd: Boolean = false,
    val isPermissionGranted: Boolean = false,
    val dataInitialized: Boolean = false,
    val showLocationPermissionDialog: Boolean = false,
) : UiState

sealed interface HomeSideEffect : SideEffect {
    data object navigateToUpload : HomeSideEffect
    data class navigateToVideo(val type: VideoType, val id: Long, val placeId: Long) : HomeSideEffect
    data class navigateToDetail(val id: Long) : HomeSideEffect
    data object launchSettingIntent : HomeSideEffect
}
