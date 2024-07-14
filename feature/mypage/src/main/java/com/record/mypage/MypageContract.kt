package com.record.mypage

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

data class MypageState(
    val profileImg: String = "",
    val nickname: String = "공간수집가열글자아아",
    val followerNum: Int = 26938,
    val followingNum: Int = 10,
    val mypageTab: MypageTab = MypageTab.TASTE,
) : UiState

sealed interface MypageSideEffect : SideEffect
