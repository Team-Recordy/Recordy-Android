package com.record.video.videodetail

import androidx.lifecycle.SavedStateHandle
import com.record.model.VideoType
import com.record.ui.base.BaseViewModel
import com.record.video.navigation.VideoRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

@HiltViewModel
class VideoDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<VideoDetailState, VideoDetailSideEffect>(VideoDetailState()) {
    private val videoTypeString = savedStateHandle.get<String>(VideoRoute.VIDEO_TYPE_ARG_NAME)
    private val videoIdString = savedStateHandle.get<String>(VideoRoute.VIDEO_ID_ARG_NAME)
    private val videoTypeEnum = videoTypeString?.let { VideoType.valueOf(videoTypeString) }
    private val videoId = videoIdString?.toInt()

    init {
        intent { copy(videoType = videoTypeEnum ?: VideoType.MY, observingId = videoId ?: 0) }
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
        postSideEffect(VideoDetailSideEffect.ShowNetworkErrorSnackbar(msg))
    }

    fun watchVideo(id: Int) {
    }
}
