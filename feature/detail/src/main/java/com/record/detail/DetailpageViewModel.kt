package com.record.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.record.detail.navigation.DetailRoute
import com.record.detail.screen.ChipTab
import com.record.exhibition.model.ExhibitionFilter
import com.record.exhibition.repository.ExhibitionRepository
import com.record.model.VideoData
import com.record.model.VideoType
import com.record.ui.base.BaseViewModel
import com.record.video.model.toCore
import com.record.video.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailpageViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val exhibitionRepository: ExhibitionRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<DetailpageState, DetailpageSideEffect>(DetailpageState()) {
    private val placeIdString = savedStateHandle.get<String>(DetailRoute.PLACE_ID)

    init {
        intent {
            copy(placeId = placeIdString?.toLong() ?: 0)
        }
        selectChip(uiState.value.selectedChip)
    }
    fun selectTab(tab: DetailpageTab) {
        intent {
            copy(detailpageTab = tab)
        }
    }

    fun selectChip(chip: ChipTab) {
        intent {
            copy(selectedChip = chip)
        }
        viewModelScope.launch {
            exhibitionRepository.getExhibitions(
                placeId = uiState.value.placeId,
                filter = when (uiState.value.selectedChip) {
                    ChipTab.ALL -> ExhibitionFilter.DEFAULT
                    ChipTab.FREE -> ExhibitionFilter.FREE
                    ChipTab.ENDING_SOON -> ExhibitionFilter.CLOSING
                },
            ).onSuccess {
                intent {
                    copy(
                        exhibitionList = it.toImmutableList(),
                    )
                }
            }.onFailure {
                Log.e("레코디최고", it.message.toString())
            }
        }
    }

    fun navigateToVideoDetail(type: VideoType, videoId: Long, placeId: Long) {
        postSideEffect(DetailpageSideEffect.NavigateToVideoDetail(type, videoId, placeId))
    }

    fun fetchPlaceInfo() = viewModelScope.launch {
        exhibitionRepository.getPlaceById(uiState.value.placeId).onSuccess {
            intent {
                copy(
                    placeAddress = it.address,
                    placeName = it.name,
                    exhibitionCount = it.exhibitionCount,
                    reviewVideoCount = it.recordCount,
                    reviewList = it.exhibitionRecord?.toImmutableList() ?: emptyList<VideoData>().toImmutableList(),
                    platformId = it.platformId,
                )
            }
        }.onFailure {
        }
    }

    fun initialData() = viewModelScope.launch {
        val reviewVideosResult = async {
            videoRepository.getMyVideos(0, 10) // 일단 review video 들어갈 자리
        }

        val reviewRes = reviewVideosResult.await()

        if (reviewRes.isSuccess) {
            val reviewVideo = reviewRes.getOrThrow()
            intent {
                copy(
                    reviewList = reviewVideo.data.map { it.toCore() }.toImmutableList(),
                    reviewCursor = reviewVideo.nextCursor?.toLong() ?: 0,
                    reviewIsEnd = false,
                )
            }
        }
    }

    fun loadMoreReviewVideos() = viewModelScope.launch {
        val list = uiState.value.reviewList.toList()
        if (uiState.value.reviewIsEnd) return@launch
        videoRepository.getMyVideos(uiState.value.reviewCursor, 10).onSuccess {
            intent {
                copy(
                    reviewCursor = it.nextCursor?.toLong() ?: 0,
                    reviewList = (list + it.data.map { it.toCore() }).toImmutableList(),
                )
            }
            if (!it.hasNext) {
                intent {
                    copy(reviewIsEnd = true)
                }
            }
        }
    }

    fun showReviewBottomSheet() {
        intent {
            copy(showReportBottomSheet = true)
        }
    }

    fun hideReviewBottomSheet() {
        intent {
            copy(showReportBottomSheet = false)
        }
    }

    fun bookmark(id: Long) {
        intent {
            val updatedReviewList = uiState.value.reviewList.map { video ->
                if (video.id == id) {
                    video.copy(isBookmark = !video.isBookmark)
                } else {
                    video
                }
            }

            copy(
                reviewList = updatedReviewList.toImmutableList(),
            )
        }
        viewModelScope.launch {
            videoRepository.bookmark(id).onSuccess {
                val updatedReviewList = uiState.value.reviewList.map { video ->
                    if (video.id == id) {
                        video.copy(isBookmark = it)
                    } else {
                        video
                    }
                }

                intent {
                    copy(
                        reviewList = updatedReviewList.toImmutableList(),
                    )
                }
            }.onFailure {
            }
        }
    }
}
