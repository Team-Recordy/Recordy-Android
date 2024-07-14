package com.record.home

import android.util.Log
import com.record.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {
    fun navigateToUpload() {
        postSideEffect(HomeSideEffect.navigateToUpload)
    }

    fun selectCategory(category: String) = intent {
        copy(selectedChip = category)
    }

    fun navigateToVideo(id: Int, type: VideoType) {
        postSideEffect(HomeSideEffect.navigateToVideo(id, type))
    }

    fun bookmark(id: Int) = intent {
        val updatedRecentList = uiState.value.recentList.map { video ->
            if (video.id.toInt() == id) {
                Log.e("태그", "변경")
                video.copy(isBookmark = !video.isBookmark)
            } else {
                video
            }
        }

        val updatedPopularList = uiState.value.popularList.map { video ->
            if (video.id.toInt() == id) {
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
}
