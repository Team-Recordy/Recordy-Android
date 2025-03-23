package com.viskit.mypage

import com.viskit.model.VideoType
import com.viskit.ui.base.SideEffect
import com.viskit.ui.base.UiState
import com.viskit.video.model.VideoData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class MypageState(
    val profileImg: String = "",
    val nickname: String = "",
    val followerNum: Int = 0,
    val followingNum: Int = 0,
    val bookmarkCursor: Long = 0,
    val bookmarkIsEnd: Boolean = false,
    val recordCursor: Long = 0,
    val recordIsEnd: Boolean = false,
    val mypageTab: MypageTab = MypageTab.RECORD,
    val myRecordList: ImmutableList<VideoData> = emptyList<VideoData>().toImmutableList(),
    val recordVideoCount: Int = 0,
    val myBookmarkList: ImmutableList<VideoData> = emptyList<VideoData>().toImmutableList(),
    val bookmarkVideoCount: Int = 0,
) : UiState

sealed interface MypageSideEffect : SideEffect {
    data class NavigateToVideoDetail(val type: VideoType, val videoId: Long) : MypageSideEffect
    data object NavigateToSettings : MypageSideEffect
    data object NavigateToFollower : MypageSideEffect
    data object NavigateToFollowing : MypageSideEffect
}
