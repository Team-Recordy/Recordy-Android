package com.viskit.video

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.Cache
import com.record.model.exception.ApiError
import com.record.ui.base.BaseViewModel
import com.record.user.repository.UserRepository
import com.record.video.model.VideoData
import com.record.video.repository.VideoCoreRepository
import com.record.video.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@UnstableApi
class VideoViewModel
@Inject constructor(
    private val videoRepository: VideoRepository,
    private val videoCoreRepository: VideoCoreRepository,
    private val userRepository: UserRepository,
    val simpleCache: Cache,
) : BaseViewModel<VideoState, VideoSideEffect>(VideoState()) {

    init {
        fetchInitialVideos()
    }

    private fun fetchInitialVideos() {
        getAllVideos()
        getFollowingVideos()
    }

    fun updateToggleState() {
        intent {
            copy(isAll = !isAll)
        }
    }

    private fun List<VideoData>.updateVideo(id: Long, update: (VideoData) -> VideoData): List<VideoData> {
        return map { video ->
            if (video.id == id) update(video) else video
        }
    }

    private fun updateVideos(id: Long, update: (VideoData) -> VideoData) {
        intent {
            copy(
                allVideos = uiState.value.allVideos.updateVideo(id, update).toImmutableList(),
                followingVideos = uiState.value.followingVideos.updateVideo(id, update).toImmutableList(),
            )
        }
    }

    fun getAllVideos() = viewModelScope.launch {
        videoRepository.getAllVideos(cursorId = 0, pageSize = 10).onSuccess { videos ->
            Log.e("됨?", "ㅇㅇ")
            intent { copy(allVideos = (uiState.value.allVideos + videos).toImmutableList()) }
        }.onFailure {
            Log.e("에라", it.message.toString())
            handleError(it)
        }
    }

    fun getFollowingVideos() = viewModelScope.launch {
        videoRepository.getFollowingVideos(cursorId = uiState.value.followingCursor, size = 10).onSuccess { videos ->
            val videos = videos
            intent {
                copy(
                    followingVideos = (uiState.value.followingVideos + videos).toImmutableList(),
                )
            }
        }.onFailure {
            Log.e("에라", it.message.toString())
            handleError(it)
        }
    }

    fun bookmark(id: Long) {
        val toggleBookmark: (VideoData) -> VideoData = { video ->
            video.copy(
                isBookmark = !video.isBookmark,
                bookmarkCount = video.bookmarkCount + if (video.isBookmark) -1 else 1,
            )
        }

        updateVideos(id, toggleBookmark)
        viewModelScope.launch {
            videoRepository.bookmark(id).onSuccess { response ->
                updateVideos(id) { it.copy(isBookmark = response, bookmarkCount = it.bookmarkCount) }
            }.onFailure {
                updateVideos(id, toggleBookmark) // Revert on failure
            }
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

    fun showReportBottomSheet(id: Long, isMine: Boolean) {
        intent {
            copy(showReportBottomSheet = true, selectedVideoIsMine = isMine, selectedVideoId = id)
        }
    }

    fun hideReportBottomSheet() {
        intent {
            copy(showReportBottomSheet = false, selectedVideoIsMine = false)
        }
    }

    fun deleteVideo(id: Long) = viewModelScope.launch {
        dismissDeleteDialog()
        videoCoreRepository.deleteVideo(id).onSuccess {
            val videos = uiState.value.allVideos.filterNot { it.id == id }.toImmutableList()
            postSideEffect(VideoSideEffect.MovePage(uiState.value.allVideos.size - videos.size))
            intent { copy(allVideos = videos) }
            hideReportBottomSheet()
            postSideEffect(VideoSideEffect.ShowReportSnackbar("정상적으로 삭제되었습니다."))
        }.onFailure { handleError(it) }
    }

    fun reportVideo(id: Long, reason: String, content: String) = viewModelScope.launch {
        videoCoreRepository.postReport(id, reason, content).onSuccess {
            if (uiState.value.isAll) {
                val videos = uiState.value.allVideos.filterNot { it.id == id }.toImmutableList()
                postSideEffect(VideoSideEffect.MovePage(uiState.value.allVideos.size - videos.size))
                intent { copy(allVideos = videos) }
            } else {
                val videos = uiState.value.followingVideos.filterNot { it.id == id }.toImmutableList()
                postSideEffect(VideoSideEffect.MovePage(uiState.value.followingVideos.size - videos.size))
                intent { copy(followingVideos = videos) }
            }
            hideReportBottomSheet()
            postSideEffect(VideoSideEffect.ShowReportSnackbar("정상적으로 신고되었습니다."))
        }.onFailure {
            handleError(it)
        }
    }

    private fun handleError(throwable: Throwable) {
        if (throwable is ApiError) {
            Log.e("Error", throwable.message)
        }
    }

    fun showNetworkErrorSnackbar(msg: String) {
        postSideEffect(VideoSideEffect.ShowNetworkErrorSnackbar(msg))
    }

    fun watchVideo(id: Long) = viewModelScope.launch {
        videoCoreRepository.watchVideo(id)
    }

    fun navigateToProfile(id: Long) = viewModelScope.launch {
        userRepository.getUserId().onSuccess { userId ->
            if (userId != id) {
                postSideEffect(VideoSideEffect.NavigateToUserProfile(id))
            }
        }
    }

    fun navigateToPlaceDetail(id: Long) = viewModelScope.launch {
        postSideEffect(VideoSideEffect.NavigateToPlaceDetail(id))
    }
}
