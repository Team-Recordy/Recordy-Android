package com.record.video

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.model.exception.ApiError
import com.record.ui.base.BaseViewModel
import com.record.video.repository.VideoCoreRepository
import com.record.video.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val videoCoreRepository: VideoCoreRepository,
) : BaseViewModel<VideoState, VideoSideEffect>(VideoState()) {
    init {
        getAllVideos()
    }

    fun updateToggleState() {
        intent {
            copy(isAll = !isAll)
        }
    }

    fun getAllVideos() = viewModelScope.launch {
        videoRepository.getAllVideos(
            cursorId = uiState.value.allCursor,
            pageSize = 10,
        ).onSuccess {
            val list = uiState.value.videos.toList()
            intent {
                copy(videos = (list + it).toImmutableList())
            }
        }.onFailure {
            when (it) {
                is ApiError -> {
                    Log.e("에러", it.message)
                }
            }
        }
    }

    fun loadMoreVideos() {
        if (uiState.value.isAll) {
            intent { copy(allCursor = uiState.value.allCursor + 10) }
            getAllVideos()
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

    fun deleteVideo(id: Long) = viewModelScope.launch {
        videoCoreRepository.deleteVideo(id).onSuccess {
            val list = uiState.value.videos.toList()
            val firstSize = list.size
            val newlist = list.filter { it.id != id }
            val secondSize = newlist.size
            val removeItemCount = firstSize - secondSize
            postSideEffect(VideoSideEffect.MovePage(removeItemCount))
            intent {
                copy(videos = newlist.toImmutableList())
            }
        }.onFailure {
            when (it) {
                is ApiError -> {
                    Log.e("실패", it.message)
                }
            }
        }
    }

    fun showNetworkErrorSnackbar(msg: String) {
        postSideEffect(VideoSideEffect.ShowNetworkErrorSnackbar(msg))
    }

    fun watchVideo(id: Long) = viewModelScope.launch {
        videoCoreRepository.watchVideo(id)
    }

    fun navigateToProfile(id: Int) {
        postSideEffect(VideoSideEffect.NavigateToUserProfile(id))
    }
}
