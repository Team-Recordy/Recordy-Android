package com.record.mypage

import android.net.Uri
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList

data class MypageState(
    val profileImg: String = "",
    val nickname: String = "공간수집가열글자아아",
    val followerNum: Int = 100,
    val followingNum: Int = 1,
    val mypageTab: MypageTab = MypageTab.TASTE,
) : UiState

sealed interface MypageSideEffect : SideEffect

