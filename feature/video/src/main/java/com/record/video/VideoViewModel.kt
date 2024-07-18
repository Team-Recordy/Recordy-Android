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
            handleError(it)
        }
    }

    fun loadMoreVideos() {
        if (uiState.value.isAll) {
            intent { copy(allCursor = uiState.value.allCursor + 10) }
            getAllVideos()
        }
    }

    fun bookmark(id: Long) {
        var originalBookmarkCount = 0
        var originalIsBookmark = false
        val videos = uiState.value.videos.toList().map { video ->
            if (video.id == id) {
                originalBookmarkCount = video.bookmarkCount
                originalIsBookmark = video.isBookmark
                video.copy(
                    isBookmark = !video.isBookmark,
                    bookmarkCount = if (originalIsBookmark) originalBookmarkCount - 1 else originalBookmarkCount + 1,
                )
            } else {
                video
            }
        }
        intent {
            copy(videos = videos.toImmutableList())
        }
        viewModelScope.launch {
            videoRepository.bookmark(id).onSuccess { response ->
                updateBookmarkStatus(id, response, originalBookmarkCount)
            }.onFailure {
                revertBookmarkStatus(id, originalBookmarkCount, originalIsBookmark)
            }
        }
    }

    private fun updateBookmarkStatus(id: Long, response: Boolean, originalBookmarkCount: Int) {
        val videos = uiState.value.videos.toList().map { video ->
            if (video.id == id) {
                video.copy(
                    isBookmark = response,
                    bookmarkCount = if (response) originalBookmarkCount + 1 else originalBookmarkCount - 1,
                )
            } else {
                video
            }
        }
        intent {
            copy(videos = videos.toImmutableList())
        }
    }

    private fun revertBookmarkStatus(id: Long, originalBookmarkCount: Int, originalIsBookmark: Boolean) {
        val videos = uiState.value.videos.toList().map { video ->
            if (video.id == id) {
                video.copy(
                    isBookmark = originalIsBookmark,
                    bookmarkCount = originalBookmarkCount,
                )
            } else {
                video
            }
        }
        intent {
            copy(videos = videos.toImmutableList())
        }
    }

    fun showDeleteDialog(id: Long) {
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
        dismissDeleteDialog()
        videoCoreRepository.deleteVideo(id).onSuccess {
            val videos = uiState.value.videos.filter { it.id != id }.toImmutableList()
            postSideEffect(VideoSideEffect.MovePage(uiState.value.videos.size - videos.size))
            intent { copy(videos = videos) }
        }.onFailure { handleError(it) }
    }

    private fun handleError(throwable: Throwable) {
        if (throwable is ApiError) {
            Log.e("에러", throwable.message)
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
