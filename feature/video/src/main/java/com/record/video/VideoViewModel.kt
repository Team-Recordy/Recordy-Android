package com.record.video

import com.record.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor() : BaseViewModel<VideoState, VideoSideEffect>(VideoState()) {
    fun updateToggleState() {
        intent {
            copy(isAll = !isAll)
        }
    }

    fun bookmark(id: Int) {
        intent {
            val videos = uiState.value.videos.toList()
            videos[videos.indexOfFirst { it.id.toInt() == id }].run {
                copy(isBookmark = !isBookmark)
            }
            copy(
                videos = videos.toImmutableList(),
            )
        }
    }

    fun showDeleteDialog(id: Int) {
        intent {
            copy(showDeleteDialog = true, deleteVideoId = id)
        }
    }

    fun dismissDeleteDialog() {
        intent {
            copy(showDeleteDialog = false, deleteVideoId = 0)
        }
    }

    fun showNetworkErrorSnackbar(msg: String) {
        postSideEffect(VideoSideEffect.ShowNetworkErrorSnackbar(msg))
    }

    fun watchVideo(id: Int) {
    }

    fun navigateToProfile(id: Int) {
        postSideEffect(VideoSideEffect.NavigateToUserProfile(id))
    }
}
