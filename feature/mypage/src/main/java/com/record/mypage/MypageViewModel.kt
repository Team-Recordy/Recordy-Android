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

    fun fetchUserPreferences() {
        viewModelScope.launch {
            userRepository.getUserPreference().onSuccess { preferences ->
                val preferenceList = preferences.toList().map {
                    Pair(it.keyword, it.percentage)
                }.toImmutableList()
                intent {
                    copy(
                        preferences = preferenceList,
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

    fun fetchUserVideos() {
        viewModelScope.launch {
            userRepository.getUserId().onSuccess { userId ->
                videoRepository.getUserVideos(
                    userId,
                    cursorId = 0,
                    size = 40,
                ).onSuccess { cursor ->
                    intent {
                        copy(
                            myRecordList = cursor.data.toImmutableList(),
                            recordVideoCount = myRecordList.size,
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

    fun fetchBookmarkVideos() {
        viewModelScope.launch {
            videoRepository.getBookmarkVideos(
                cursorId = 0,
                size = 20,
            ).onSuccess { cursor ->
                intent {
                    copy(
                        myBookmarkList = cursor.data.toImmutableList(),
                        bookmarkVideoCount = myBookmarkList.size,
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
