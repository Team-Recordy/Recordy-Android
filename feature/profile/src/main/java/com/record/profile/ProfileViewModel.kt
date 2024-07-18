package com.record.profile

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.record.model.VideoType
import com.record.model.exception.ApiError
import com.record.profile.navigation.ProfileRoute
import com.record.ui.base.BaseViewModel
import com.record.user.repository.UserRepository
import com.record.video.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val videoRepository: VideoRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProfileState, ProfileSideEffect>(ProfileState()) {
    private val userId = savedStateHandle.get<String>(ProfileRoute.PROFILE_ID_ARG_NAME)

    init {
        intent {
            copy(id = userId?.toLong() ?: 0)
        }
        getProfile()
        initialData()
    }

    fun getProfile() = viewModelScope.launch {
        userRepository.getUserProfile(uiState.value.id).onSuccess { response ->
            intent {
                copy(
                    id = response.id.toLong(),
                    followerCount = response.followerCount,
                    followingCount = response.followingCount,
                    isFollowing = response.isFollowing,
                    nickname = response.nickname,
                    profileImageUrl = response.profileImageUrl,
                    recordCount = response.recordCount,
                )
            }
        }.onFailure {
            when (it) {
                is ApiError -> {
                    Log.e("ProfileViewModel", it.message)
                }
            }
        }
    }

    fun initialData() = viewModelScope.launch {
        videoRepository.getUserVideos(uiState.value.id, 0, 10).onSuccess {
            intent {
                copy(
                    userVideos = it.data.toImmutableList(),
                    cursorId = it.nextCursor?.plus(1)?.toLong() ?: 0,
                    isEnd = !it.hasNext,
                )
            }
        }
    }

    fun getVideos() = viewModelScope.launch {
        if (uiState.value.isEnd) return@launch
        val list = uiState.value.userVideos.toList()
        videoRepository.getUserVideos(uiState.value.id, uiState.value.cursorId, 10).onSuccess {
            intent {
                copy(userVideos = (list + it.data).toImmutableList(), cursorId = (it.nextCursor?.plus(1))?.toLong() ?: 0)
            }
            if (!it.hasNext) {
                intent {
                    copy(isEnd = true)
                }
            }
        }.onFailure {
            when (it) {
                is ApiError -> {
                    Log.e("ProfileViewModel", it.message)
                }
            }
        }
    }

    fun toggleFollow() {
        val originalIsFollowing = uiState.value.isFollowing
        val originalFollowerCount = uiState.value.followerCount
        viewModelScope.launch {
            intent {
                copy(
                    isFollowing = !originalIsFollowing,
                    followerCount = if (originalIsFollowing) {
                        originalFollowerCount - 1
                    } else {
                        originalFollowerCount + 1
                    },
                )
            }
            userRepository.postFollow(uiState.value.id).onSuccess {
                intent {
                    copy(
                        isFollowing = !originalIsFollowing,
                        followerCount = if (originalIsFollowing) {
                            originalFollowerCount - 1
                        } else {
                            originalFollowerCount + 1
                        },
                    )
                }
            }.onFailure {
                intent {
                    copy(
                        isFollowing = originalIsFollowing,
                        followerCount = originalFollowerCount,
                    )
                }
            }
        }
    }

    fun bookmark(id: Long) {
        intent {
            val updatedMyRecordList = uiState.value.userVideos.map { video ->
                if (video.id == id) {
                    Log.e("태그", "변경")
                    video.copy(isBookmark = !video.isBookmark)
                } else {
                    video
                }
            }

            copy(
                userVideos = updatedMyRecordList.toImmutableList(),
            )
        }
        viewModelScope.launch {
            videoRepository.bookmark(id).onSuccess {
                val updatedMyRecordList = uiState.value.userVideos.map { video ->
                    if (video.id == id) {
                        Log.e("태그", "변경")
                        video.copy(isBookmark = it)
                    } else {
                        video
                    }
                }

                intent {
                    copy(
                        userVideos = updatedMyRecordList.toImmutableList(),
                    )
                }
            }.onFailure {
            }
        }
    }

    fun navigateVideo(videoType: VideoType, id: Long) {
        postSideEffect(ProfileSideEffect.navigateToVideoDetail(videoType, id, uiState.value.id))
    }
}
