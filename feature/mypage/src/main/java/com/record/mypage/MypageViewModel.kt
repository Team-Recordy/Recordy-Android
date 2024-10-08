package com.record.mypage

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.model.VideoType
import com.record.model.exception.ApiError
import com.record.ui.base.BaseViewModel
import com.record.user.repository.UserRepository
import com.record.video.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val videoRepository: VideoRepository,
) : BaseViewModel<MypageState, MypageSideEffect>(MypageState()) {

    fun selectTab(tab: MypageTab) {
        intent {
            copy(mypageTab = tab)
        }
    }

    fun navigateToSetting() {
        postSideEffect(MypageSideEffect.NavigateToSettings)
    }

    fun navigateToFollowing() {
        postSideEffect(MypageSideEffect.NavigateToFollowing)
    }

    fun navigateToFollower() {
        postSideEffect(MypageSideEffect.NavigateToFollower)
    }

    fun navigateToVideoDetail(type: VideoType, videoId: Long) {
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
                            bookmarkVideoCount = mine.bookmarkCount,
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
        val myVideosResult = async {
            videoRepository.getMyVideos(0, 10)
        }
        val bookmarkVideosResult = async {
            videoRepository.getBookmarkVideos(0, 10)
        }

        val myVideoRes = myVideosResult.await()
        val bookmarkVideoRes = bookmarkVideosResult.await()

        if (myVideoRes.isSuccess && bookmarkVideoRes.isSuccess) {
            val myVideo = myVideoRes.getOrThrow()
            val bookmarkVideo = bookmarkVideoRes.getOrThrow()
            intent {
                copy(
                    myRecordList = myVideo.data.toImmutableList(),
                    myBookmarkList = bookmarkVideo.data.toImmutableList(),
                    recordCursor = myVideo.nextCursor?.toLong() ?: 0,
                    bookmarkCursor = bookmarkVideo.nextCursor?.toLong() ?: 0,
                    recordIsEnd = false,
                    bookmarkIsEnd = false,
                )
            }
        }
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
            }.onFailure {
            }
        }
    }
}
