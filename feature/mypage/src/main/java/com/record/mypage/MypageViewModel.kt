package com.record.mypage

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.model.VideoType
import com.record.model.exception.ApiError
import com.record.ui.base.BaseViewModel
import com.record.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val userRepository: UserRepository,
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

    fun navigateToVideoDetail(type: VideoType, index: Int) {
        postSideEffect(MypageSideEffect.NavigateToVideoDetail(type, index))
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            userRepository.getUserId().onSuccess {
                    userId ->
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
}
