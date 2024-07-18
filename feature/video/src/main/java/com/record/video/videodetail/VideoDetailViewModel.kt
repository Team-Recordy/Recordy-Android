package com.record.video.videodetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.record.model.Cursor
import com.record.model.Page
import com.record.model.VideoType
import com.record.model.exception.ApiError
import com.record.ui.base.BaseViewModel
import com.record.video.model.VideoData
import com.record.video.navigation.VideoRoute
import com.record.video.repository.VideoCoreRepository
import com.record.video.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoDetailViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val videoCoreRepository: VideoCoreRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<VideoDetailState, VideoDetailSideEffect>(VideoDetailState()) {
    private val videoTypeString = savedStateHandle.get<String>(VideoRoute.VIDEO_TYPE_ARG_NAME)
    private val videoIdString = savedStateHandle.get<String>(VideoRoute.VIDEO_INDEX)
    private val userIdString = savedStateHandle.get<String>(VideoRoute.VIDEO_USER_ID)
    private val keyword = savedStateHandle.get<String>(VideoRoute.VIDEO_KEYWORD)
    private val videoTypeEnum = videoTypeString?.let { VideoType.valueOf(videoTypeString) }
    private val videoId = videoIdString?.toLong()
    private val otherUserId = userIdString?.toLong()

    init {
        val realKeyword = if (keyword == "all" || keyword == null) "" else keyword
        intent {
            copy(
                videoType = videoTypeEnum ?: VideoType.MY,
                observingId = videoId ?: 0,
                page = 0,
                cursor = videoId ?: 0,
                keyword = realKeyword,
                userId = otherUserId ?: 0,
            )
        }
        getVideos()
    }

    fun getVideos() {
        if (uiState.value.isEnd) return
        when (uiState.value.videoType) {
            VideoType.PROFILE -> fetchVideos { getUserVideos() }
            VideoType.BOOKMARK -> fetchVideos { getBookmarkVideos() }
            VideoType.MY -> fetchVideos { getMyVideos() }
            VideoType.POPULAR -> fetchVideos { getPopularVideos() }
            VideoType.RECENT -> fetchVideos { getRecentVideos() }
        }
    }

    private inline fun fetchVideos(crossinline fetch: suspend () -> Unit) = viewModelScope.launch {
        runCatching { fetch() }.onFailure { handleError(it) }
    }

    private suspend fun getPopularVideos() {
        val videos = uiState.value.videos.toList()
        val keyword = uiState.value.keyword.takeIf { it.isNotBlank() }?.let { listOf(it) }
        videoRepository.getPopularVideos(keyword, uiState.value.page, 10).handlePageResponse(videos)
    }

    private suspend fun getRecentVideos() {
        val videos = uiState.value.videos.toList()
        val keyword = uiState.value.keyword.takeIf { it.isNotBlank() }?.let { listOf(it) }
        videoRepository.getRecentVideos(keyword, uiState.value.cursor + 1, 10).handleCursorResponse(videos)
    }

    private suspend fun getMyVideos() {
        val videos = uiState.value.videos.toList()
        videoRepository.getMyVideos(uiState.value.cursor + 1, 10).handleCursorResponse(videos)
    }

    private suspend fun getUserVideos() {
        val videos = uiState.value.videos.toList()
        videoRepository.getUserVideos(uiState.value.userId, uiState.value.cursor + 1, 10).handleCursorResponse(videos)
    }

    private suspend fun getBookmarkVideos() {
        val videos = uiState.value.videos.toList()
        videoRepository.getBookmarkVideos(uiState.value.cursor + 1, 10).handleCursorResponse(videos)
    }

    private fun Result<Cursor<VideoData>>.handleCursorResponse(existingVideos: List<VideoData>) {
        onSuccess { response ->
            val newVideos = (existingVideos + response.data).toImmutableList()
            intent {
                copy(videos = newVideos, cursor = response.nextCursor?.toLong() ?: 0)
            }
            intent {
                copy(isEnd = !response.hasNext)
            }
        }
    }

    private fun Result<Page<VideoData>>.handlePageResponse(existingVideos: List<VideoData>) {
        onSuccess { response ->
            val newVideos = (existingVideos + response.data).toImmutableList()
            val observingIndex = newVideos.indexOfFirst { it.id == uiState.value.observingId }
            intent {
                copy(videos = newVideos)
            }
            intent {
                copy(isEnd = !response.hasNext)
            }
            if (!uiState.value.isInit) {
                postSideEffect(VideoDetailSideEffect.InitialPagerState(observingIndex))
                intent {
                    copy(isInit = true)
                }
            }
        }
    }

    private fun handleError(throwable: Throwable) {
        if (throwable is ApiError) {
            Log.e("에러", throwable.message)
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

    fun deleteVideo() = viewModelScope.launch {
        val id = uiState.value.deleteVideoId
        videoCoreRepository.deleteVideo(id).onSuccess {
            val videos = uiState.value.videos.filter { it.id != id }.toImmutableList()
            postSideEffect(VideoDetailSideEffect.MovePage(uiState.value.videos.size - videos.size))
            intent { copy(videos = videos) }
        }.onFailure { handleError(it) }
    }

    fun watchVideo(id: Long) = viewModelScope.launch { videoCoreRepository.watchVideo(id) }

    fun showDeleteDialog(id: Long) {
        intent { copy(showDeleteDialog = true, deleteVideoId = id) }
    }

    fun dismissDeleteDialog() {
        intent { copy(showDeleteDialog = false, deleteVideoId = 0) }
    }

    fun showNetworkErrorSnackbar(msg: String) {
        postSideEffect(VideoDetailSideEffect.ShowNetworkErrorSnackbar(msg))
    }

    fun navigateToProfile(id: Int) {
        postSideEffect(VideoDetailSideEffect.NavigateToUserProfile(id))
    }

    fun navigateToBack() {
        postSideEffect(VideoDetailSideEffect.NavigateToBack)
    }
}
