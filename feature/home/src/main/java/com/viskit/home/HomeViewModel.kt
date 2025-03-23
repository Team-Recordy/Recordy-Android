package com.viskit.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.exhibition.model.Place
import com.record.exhibition.repository.ExhibitionRepository
import com.record.model.VideoType
import com.record.model.exception.ApiError
import com.record.ui.base.BaseViewModel
import com.record.video.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val exhibitionRepository: ExhibitionRepository,
) : BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {

    fun navigateToVideo(videoType: VideoType, id: Long, videoId: Long) {
        postSideEffect(HomeSideEffect.navigateToVideo(videoType, id, videoId))
    }

    fun navigateToDetail(placeId: Long) {
        postSideEffect(HomeSideEffect.navigateToDetail(placeId))
    }

    fun resetPlaces() = viewModelScope.launch {
        exhibitionRepository.getNearPlaceData(0, if (uiState.value.exhibitionList.size < 10) 30 else uiState.value.exhibitionList.size, uiState.value.location.latitude, uiState.value.location.longitude)
            .onSuccess {
                intent {
                    copy(exhibitionList = (it.data).toImmutableList())
                }
                intent {
                    copy(isEnd = !it.hasNext, page = 1, dataInitialized = true)
                }
            }
            .onFailure { throwable ->
                if (throwable is ApiError) {
                    Log.e("asdfasdf", throwable.message)
                } else {
                    Log.e("asdfasdf", throwable.message.toString())
                }
            }
    }

    fun getPlaces() = viewModelScope.launch {
        if (uiState.value.isEnd) return@launch
        val list = uiState.value.exhibitionList
        exhibitionRepository.getNearPlaceData(uiState.value.page, 30, uiState.value.location.latitude, uiState.value.location.longitude)
            .onSuccess {
                intent {
                    copy(exhibitionList = (list + it.data).toImmutableList())
                }
                intent {
                    copy(isEnd = !it.hasNext, page = uiState.value.page + 1)
                }
            }
            .onFailure { throwable ->
                if (throwable is ApiError) {
                    Log.e("asdfasdf", throwable.message)
                } else {
                    Log.e("asdfasdf", throwable.message.toString())
                }
            }
    }

    fun showLocationPermissionDialog(isShow: Boolean) = intent {
        copy(showLocationPermissionDialog = isShow)
    }

    fun updateLocation(latitude: Double, longitude: Double) = intent {
        copy(location = Location(latitude, longitude))
    }

    fun updatePermissionGranted(isGranted: Boolean) = intent {
        copy(isPermissionGranted = isGranted)
    }

    fun bookmark(id: Long) {
        intent {
            val updatedList = uiState.value.exhibitionList.map { exhibition ->
                Place(
                    placeId = exhibition.placeId,
                    address = exhibition.address,
                    name = exhibition.name,
                    exhibitionCount = exhibition.exhibitionCount,
                    recordCount = exhibition.recordCount,
                    exhibitionRecord = exhibition.exhibitionRecord?.map { video ->
                        if (video.id == id) {
                            video.copy(isBookmark = !video.isBookmark)
                        } else {
                            video
                        }
                    }?.toImmutableList(),
                    platformId = exhibition.platformId,
                )
            }
            copy(
                exhibitionList = updatedList.toImmutableList(),
            )
        }
        viewModelScope.launch {
            videoRepository.bookmark(id).onSuccess {
                val updatedList = uiState.value.exhibitionList.map { exhibition ->
                    Place(
                        placeId = exhibition.placeId,
                        address = exhibition.address,
                        name = exhibition.name,
                        exhibitionCount = exhibition.exhibitionCount,
                        recordCount = exhibition.recordCount,
                        exhibitionRecord = exhibition.exhibitionRecord?.map { video ->
                            if (video.id == id) {
                                video.copy(isBookmark = video.isBookmark)
                            } else {
                                video
                            }
                        }?.toImmutableList(),
                        platformId = exhibition.platformId,
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
