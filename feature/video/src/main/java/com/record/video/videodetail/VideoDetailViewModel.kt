package com.record.video.videodetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.record.model.VideoType
import com.record.model.exception.ApiError
import com.record.ui.base.BaseViewModel
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
    private val videoIndexString = savedStateHandle.get<String>(VideoRoute.VIDEO_INDEX)
    private val userIdString = savedStateHandle.get<String>(VideoRoute.VIDEO_USER_ID)
    private val keyword = savedStateHandle.get<String>(VideoRoute.VIDEO_KEYWORD)
    private val videoTypeEnum = videoTypeString?.let { VideoType.valueOf(videoTypeString) }
    private val videoIndex = videoIndexString?.toInt()
    private val userId = userIdString?.toLong()

    init {
        val page = videoIndex?.div(10) ?: 0
        val index = videoIndex?.rem(10) ?: 0
        val realKeyword = if (keyword == "all" || keyword == null) "" else keyword
        Log.e("순서대로 페이지 인덱스 키워드", "$page $index $realKeyword")
        intent {
            copy(
                videoType = videoTypeEnum ?: VideoType.MY,
                observingIndex = index,
                page = page,
                keyword = realKeyword,
                userId = userId,
            )
        }
        runCatching { getVideos() }.onSuccess {
        }
    }

    fun getVideos() {
        if (uiState.value.isEnd) return
        when (uiState.value.videoType) {
            VideoType.PROFILE -> {
            }

            VideoType.BOOKMARK -> {}
            VideoType.MY -> {
            }

            VideoType.POPULAR -> {
                getPopularVideo()
            }

            VideoType.RECENT -> {
                getRecentVideo()
            }
        }
    }

    fun getPopularVideo() = viewModelScope.launch {
        val keyword = if (uiState.value.keyword.isBlank()) null else listOf(uiState.value.keyword)
        val videos = uiState.value.videos.toList()
        videoRepository.getPopularVideos(keyword, uiState.value.page, 10).onSuccess {
            intent {
                copy(videos = (videos + it.data).toImmutableList(), page = it.page + 1)
            }
            if (!it.hasNext) {
                intent {
                    copy(isEnd = true)
                }
            }
            if (!uiState.value.init) {
                postSideEffect(VideoDetailSideEffect.InitialPagerState((uiState.value.page - 1) * 10 + uiState.value.observingIndex))
                intent {
                    copy(init = true)
                }
            }
        }.onFailure {
            when (it) {
                is ApiError -> {
                    Log.e("에러", it.message)
                }
            }
        }
    }

    fun getRecentVideo() = viewModelScope.launch {
        val keyword = if (uiState.value.keyword.isBlank()) null else listOf(uiState.value.keyword)
        val videos = uiState.value.videos.toList()
        videoRepository.getRecentVideos(keyword, uiState.value.page, 10).onSuccess {
            intent {
                copy(videos = (videos + it.data).toImmutableList(), page = uiState.value.page + 1)
            }
            if (!it.hasNext) {
                intent {
                    copy(isEnd = true)
                }
            }
            if (!uiState.value.init) {
                postSideEffect(VideoDetailSideEffect.InitialPagerState((uiState.value.page - 1) * 10 + uiState.value.observingIndex))
                intent {
                    copy(init = true)
                }
            }
            intent {
                copy(page = it.nextCursor)
            }
        }.onFailure {
            when (it) {
                is ApiError -> {
                    Log.e("에러", it.message)
                }
            }
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

    fun deleteVideo(id: Long) = viewModelScope.launch {
        videoCoreRepository.deleteVideo(id).onSuccess {
            val list = uiState.value.videos.toList()
            val firstSize = list.size
            val newlist = list.filter { it.id != id }
            val secondSize = newlist.size
            val removeItemCount = firstSize - secondSize
            postSideEffect(VideoDetailSideEffect.MovePage(removeItemCount))
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
    fun watchVideo(id: Long) = viewModelScope.launch {
        videoCoreRepository.watchVideo(id)
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

    fun showNetworkErrorSnackbar(msg: String) {
        postSideEffect(VideoDetailSideEffect.ShowNetworkErrorSnackbar(msg))
    }

    fun watchVideo(id: Int) {
    }

    fun navigateToProfile(id: Int) {
        postSideEffect(VideoDetailSideEffect.NavigateToUserProfile(id))
    }
}
