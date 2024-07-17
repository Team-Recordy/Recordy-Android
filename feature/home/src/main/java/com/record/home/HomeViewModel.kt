package com.record.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.keyword.repository.KeywordRepository
import com.record.model.VideoType
import com.record.model.exception.ApiError
import com.record.ui.base.BaseViewModel
import com.record.video.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val keywordRepository: KeywordRepository,
) : BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {

    init {
        getPreferenceKeywords()
        getPopularVideos()
        getRecentVideos()
    }

    fun navigateToUpload() {
        postSideEffect(HomeSideEffect.navigateToUpload)
    }

    fun selectCategory(categoryIndex: Int) {
        intent {
            copy(selectedChipIndex = categoryIndex)
        }
        getPopularVideos()
        getRecentVideos()
    }

    private fun getPreferenceKeywords() {
        viewModelScope.launch {
            keywordRepository.getKeywords().onSuccess {
                intent {
                    copy(chipList = it.keywords)
                }
            }.onFailure {
                when (it) {
                    is ApiError -> {
                        Log.e("error", it.message)
                    }
                }
            }
        }
    }

    private fun getRecentVideos() {
        viewModelScope.launch {
            val keyIndex = uiState.value.selectedChipIndex
            val keyword = if (keyIndex != null) listOf(uiState.value.chipList[keyIndex]) else null
            videoRepository.getRecentVideos(
                keywords = keyword,
                pageNumber = 0,
                pageSize = 10,
            ).onSuccess {
                intent {
                    copy(recentList = it.data.toImmutableList())
                }
            }.onFailure {
                when (it) {
                    is ApiError -> {
                        Log.e("error", it.message)
                    }
                }
            }
        }
    }

    private fun getPopularVideos() {
        viewModelScope.launch {
            val keyIndex = uiState.value.selectedChipIndex
            val keyword = if (keyIndex != null) listOf(uiState.value.chipList[keyIndex]) else null
            videoRepository.getPopularVideos(
                keywords = keyword,
                pageNumber = 0,
                pageSize = 10,
            ).onSuccess {
                intent {
                    copy(popularList = it.data.toImmutableList())
                }
            }.onFailure {
                when (it) {
                    is ApiError -> {
                        Log.e("error", it.message)
                    }
                }
            }
        }
    }

    fun navigateToVideo(index: Int, type: VideoType) {
        val selectedIndex = uiState.value.selectedChipIndex
        val selectedKeyword = if (selectedIndex != null) uiState.value.chipList[selectedIndex] else null
        postSideEffect(HomeSideEffect.navigateToVideo(index, type, selectedKeyword))
    }

    fun bookmark(id: Long) {
        intent {
            val updatedRecentList = uiState.value.recentList.map { video ->
                if (video.id == id) {
                    Log.e("태그", "변경")
                    video.copy(isBookmark = !video.isBookmark)
                } else {
                    video
                }
            }

            val updatedPopularList = uiState.value.popularList.map { video ->
                if (video.id == id) {
                    Log.e("태그", "변경")
                    video.copy(isBookmark = !video.isBookmark)
                } else {
                    video
                }
            }

            Log.e("반환값", updatedPopularList.toString())
            copy(
                recentList = updatedRecentList,
                popularList = updatedPopularList,
            )
        }
        viewModelScope.launch {
            videoRepository.bookmark(id).onSuccess {
                val updatedRecentList1 = uiState.value.recentList.map { video ->
                    if (video.id == id) {
                        Log.e("태그", "변경")
                        video.copy(isBookmark = it)
                    } else {
                        video
                    }
                }

                val updatedPopularList1 = uiState.value.popularList.map { video ->
                    if (video.id == id) {
                        Log.e("태그", "변경")
                        video.copy(isBookmark = it)
                    } else {
                        video
                    }
                }

                Log.e("반환값", updatedPopularList1.toString())
                intent {
                    copy(
                        recentList = updatedRecentList1,
                        popularList = updatedPopularList1,
                    )
                }
            }.onFailure {
            }
        }
    }
}
