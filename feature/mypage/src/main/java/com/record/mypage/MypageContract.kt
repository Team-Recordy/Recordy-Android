package com.record.mypage

import com.record.model.VideoType
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

data class MypageState(
    val profileImg: String = "",
    val nickname: String = "",
    val followerNum: Int = 0,
    val followingNum: Int = 0,
    val mypageTab: MypageTab = MypageTab.TASTE,
) : UiState

sealed interface MypageSideEffect : SideEffect {
    data class NavigateToVideoDetail(val type: VideoType, val index: Int) : MypageSideEffect
    data object NavigateToSettings : MypageSideEffect
    data object NavigateToFollower : MypageSideEffect
    data object NavigateToFollowing : MypageSideEffect
}
