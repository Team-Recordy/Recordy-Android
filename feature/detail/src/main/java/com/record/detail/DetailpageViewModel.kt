package com.record.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.model.VideoType
import com.record.model.exception.ApiError
import com.record.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.record.user.repository.UserRepository
import com.record.video.repository.VideoRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailpageViewModel @Inject constructor (
    private val userRepository: UserRepository,
    private val videoRepository: VideoRepository,
) : BaseViewModel<DetailpageState, DetailpageSideEffect>(DetailpageState()) {

    fun selectTab(tab: DetailpageTab) {
        intent {
            copy(detailpageTab = tab)
        }
    }

    fun navigateToVideoDetail(type: VideoType, videoId: Long) {
        postSideEffect(DetailpageSideEffect.NavigateToVideoDetail(type, videoId))
    }

    fun fetchPlaceInfo() {
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
                    reviewList = reviewVideo.data.toImmutableList(),
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
                    reviewList = (list + it.data).toImmutableList(),
                )
            }
            if (!it.hasNext) {
                intent {
                    copy(reviewIsEnd = true)
                }
            }
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
