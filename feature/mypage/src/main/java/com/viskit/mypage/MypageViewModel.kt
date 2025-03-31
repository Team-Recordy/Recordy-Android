package com.viskit.mypage

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.amplitude.android.Amplitude
import com.viskit.model.VideoType
import com.viskit.model.exception.ApiError
import com.viskit.ui.base.BaseViewModel
import com.viskit.user.repository.UserRepository
import com.viskit.video.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val videoRepository: VideoRepository,
    private val amplitude: Amplitude,
) : BaseViewModel<MypageState, MypageSideEffect>(MypageState()) {

    fun selectTab(tab: MypageTab) {
        if (tab == MypageTab.BOOKMARK) amplitudeTrack("click_bookmark_tab?", true)
        intent {
            copy(mypageTab = tab)
        }
    }

    fun navigateToSetting() {
        postSideEffect(MypageSideEffect.NavigateToSettings)
    }

    fun navigateToFollowing() {
        amplitudeTrack("click_following_button?", true)
        postSideEffect(MypageSideEffect.NavigateToFollowing)
    }

    fun navigateToFollower() {
        amplitudeTrack("click_follower_button?", true)
        postSideEffect(MypageSideEffect.NavigateToFollower)
    }

    fun navigateToVideoDetail(type: VideoType, videoId: Long) {
        amplitudeTrack("click_bookmark_tab_video?", true)
        postSideEffect(MypageSideEffect.NavigateToVideoDetail(type, videoId))
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            userRepository.getUserId().onSuccess { userId ->
                userRepository.getUserProfile(userId).onSuccess { mine ->
                    intent {
                        copy(
                            profileImg = mine.profileImageUrl,
                            nickname = mine.nickname,
                            followerNum = mine.followerCount,
                            followingNum = mine.followingCount,
                            recordVideoCount = mine.recordCount,
                        )
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
    }

    fun initialData() = viewModelScope.launch {
        getInitialMyVideos()
        getInitialBookMark()
    }

    private suspend fun getInitialMyVideos() = videoRepository.getMyVideos(0, 10)
        .onSuccess { myVideo ->
            intent {
                copy(
                    myRecordList = myVideo.data.toImmutableList(),
                    recordCursor = myVideo.nextCursor?.toLong() ?: 0,
                    recordIsEnd = false,
                    recordVideoCount = myVideo.data.size,
                )
            }
        }
        .onFailure { error ->
            Log.e("오류 발생", error.message.toString())
        }

    private suspend fun getInitialBookMark() = videoRepository.getBookmarkVideos(0, 10)
        .onSuccess { bookmarkVideo ->
            intent {
                copy(
                    myBookmarkList = bookmarkVideo.data.toImmutableList(),
                    bookmarkCursor = bookmarkVideo.nextCursor?.toLong() ?: 0,
                    bookmarkIsEnd = false,
                    bookmarkVideoCount = bookmarkVideo.data.size,
                )
            }
        }
        .onFailure { error ->
            Log.e("오류 발생", error.message.toString())
        }

    fun loadMoreUserVideos() = viewModelScope.launch {
        val list = uiState.value.myRecordList.toList()
        if (uiState.value.recordIsEnd) return@launch
        videoRepository.getMyVideos(uiState.value.recordCursor, 10).onSuccess {
            intent {
                copy(
                    recordCursor = it.nextCursor?.toLong() ?: 0,
                    myRecordList = (list + it.data).toImmutableList(),
                )
            }
            if (!it.hasNext) {
                intent {
                    copy(recordIsEnd = true)
                }
            }
        }
    }

    fun loadMoreBookmarkVideos() = viewModelScope.launch {
        val list = uiState.value.myBookmarkList.toList()
        if (uiState.value.bookmarkIsEnd) return@launch
        videoRepository.getBookmarkVideos(uiState.value.bookmarkCursor, 10).onSuccess {
            intent {
                copy(
                    bookmarkCursor = it.nextCursor?.toLong() ?: 0,
                    myBookmarkList = (list + it.data).toImmutableList(),
                )
            }
            if (!it.hasNext) {
                intent {
                    copy(bookmarkIsEnd = true)
                }
            }
        }
    }

    fun bookmark(id: Long) {
        intent {
            val updatedMyRecordList = uiState.value.myRecordList.map { video ->
                if (video.id == id) {
                    video.copy(isBookmark = !video.isBookmark)
                } else {
                    video
                }
            }

            val updatedMyBookmarkList = uiState.value.myBookmarkList.map { video ->
                if (video.id == id) {
                    video.copy(isBookmark = !video.isBookmark)
                } else {
                    video
                }
            }

            copy(
                myRecordList = updatedMyRecordList.toImmutableList(),
                myBookmarkList = updatedMyBookmarkList.toImmutableList(),
            )
        }
        amplitudeTrack("cancel_bookmark_button?",true)
        viewModelScope.launch {
            videoRepository.bookmark(id).onSuccess {
                val updatedMyRecordList = uiState.value.myRecordList.map { video ->
                    if (video.id == id) {
                        video.copy(isBookmark = it)
                    } else {
                        video
                    }
                }
                val updatedMyBookmarkList = uiState.value.myBookmarkList.map { video ->
                    if (video.id == id) {
                        video.copy(isBookmark = it)
                    } else {
                        video
                    }
                }
                intent {
                    copy(
                        myRecordList = updatedMyRecordList.toImmutableList(),
                        myBookmarkList = updatedMyBookmarkList.toImmutableList(),
                    )
                }

                getInitialBookMark()
            }.onFailure {
            }
        }
    }

    fun amplitudeTrack(name: String, value: Any?) {
        amplitude.track("Profile", mutableMapOf(name to value))
    }
}
