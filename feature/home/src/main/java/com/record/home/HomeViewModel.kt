package com.record.home

import androidx.lifecycle.viewModelScope
import com.record.ui.base.BaseViewModel
import com.record.video.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
) : BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {

    fun navigateToVideo(videoId: Long, location: String) {
        postSideEffect(HomeSideEffect.navigateToVideo(videoId, location))
    }

    fun showLocationPermissionDialog(isShow: Boolean) = intent {
        copy(showLocationPermissionDialog = isShow)
    }

    fun updateLocation(latitude: Double, longitude: Double) = intent {
        copy(location = Location(latitude, longitude))
    }

    fun bookmark(id: Long) {
        intent {
            val updatedList = uiState.value.exhibitionList.map { exhibition ->
                Exhibition(
                    location = exhibition.location,
                    name = exhibition.name,
                    exhibitionCount = exhibition.exhibitionCount,
                    userVideo = exhibition.userVideo.map { video ->
                        if (video.id == id) {
                            video.copy(isBookmark = !video.isBookmark)
                        } else {
                            video
                        }
                    }.toImmutableList(),
                )
            }
            copy(
                exhibitionList = updatedList.toImmutableList(),
            )
        }
        viewModelScope.launch {
            videoRepository.bookmark(id).onSuccess {
                val updatedList = uiState.value.exhibitionList.map { exhibition ->
                    Exhibition(
                        location = exhibition.location,
                        name = exhibition.name,
                        exhibitionCount = exhibition.exhibitionCount,
                        userVideo = exhibition.userVideo.map { video ->
                            if (video.id == id) {
                                video.copy(isBookmark = !video.isBookmark)
                            } else {
                                video
                            }
                        }.toImmutableList(),
                    )
                }
                intent {
                    copy(
                        exhibitionList = updatedList.toImmutableList(),
                    )
                }
            }.onFailure {
            }
        }
    }
}
